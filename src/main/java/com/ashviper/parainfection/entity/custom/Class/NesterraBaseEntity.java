package com.ashviper.parainfection.entity.custom.Class;

import com.ashviper.parainfection.regi.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;

@SuppressWarnings("removal")
public abstract class NesterraBaseEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int corruptionRadius = 0;
    private int corruptionDelay = 0;
    private final int BLOCKS_PER_TICK = 5;
    private final Set<BlockPos> visited = new HashSet<>();
    private final Queue<BlockPos> pendingBlocks = new LinkedList<>();

    private int summonDelay = 0;
    private int mobsSummoned = 0;
    private boolean playerInRange = false;

    public static final TagKey<Block> FORGE_ORES = TagKey.create(
            net.minecraft.core.registries.Registries.BLOCK, new ResourceLocation("forge", "ores"));

    public NesterraBaseEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder baseAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 20.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            // 腐食処理
            handleCorruption();

            // プレイヤーが範囲内にいれば召喚処理
            Player nearestPlayer = this.level().getNearestPlayer(this, getSummonRange());

            if (nearestPlayer != null) {
                playerInRange = true;
                summonDelay++;

                if (summonDelay >= getSummonInterval()) {
                    summonDelay = 0;

                    List<EntityType<? extends Monster>> summonTypes = getSummonMobTypes();

                    if (!summonTypes.isEmpty()) {
                        for (int i = 0; i < getMobsPerSummon(); i++) {
                            // ランダムなMobタイプを取得
                            EntityType<? extends Monster> mobType = summonTypes.get(
                                    this.random.nextInt(summonTypes.size()));

                            // ランダムなスポーン位置（半径3以内）
                            BlockPos spawnPos = this.blockPosition().offset(
                                    this.random.nextInt(7) - 3, 0, this.random.nextInt(7) - 3);

                            // エンティティを生成してスポーン
                            var newEntity = mobType.create(level());
                            if (newEntity != null) {
                                newEntity.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5,
                                        this.getYRot(), 0);
                                level().addFreshEntity(newEntity);

                                // 爆発パーティクル（見た目のみ）
                                if (level() instanceof ServerLevel serverLevel) {
                                    serverLevel.sendParticles(
                                            ParticleTypes.EXPLOSION,
                                            spawnPos.getX() + 0.5, spawnPos.getY() + 0.5, spawnPos.getZ() + 0.5,
                                            1, 0, 0, 0, 0.01
                                    );
                                }
                            }
                        }

                        // 呼び出し数を記録（任意）
                        mobsSummoned += getMobsPerSummon();
                    }
                }
            } else {
                if (playerInRange) {
                    playerInRange = false;
                    summonDelay = 0;
                    mobsSummoned = 0;
                }
            }
        }
    }

    private void handleCorruption() {
        corruptionDelay++;

        if (corruptionDelay >= getMaxCorruptionTime()) {
            corruptionDelay = 0;
            if (corruptionRadius < getMaxCorruptionRadius()) {
                corruptionRadius++;
            }

            BlockPos origin = this.blockPosition();

            for (int dx = -corruptionRadius; dx <= corruptionRadius; dx++) {
                for (int dy = -corruptionRadius; dy <= corruptionRadius; dy++) {
                    for (int dz = -corruptionRadius; dz <= corruptionRadius; dz++) {
                        double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
                        if (dist > corruptionRadius) continue;

                        BlockPos pos = origin.offset(dx, dy, dz);
                        if (visited.contains(pos)) continue;

                        BlockState state = level().getBlockState(pos);
                        if (!state.isAir()
                                && state.getFluidState().isEmpty()
                                && !state.is(BlockTags.SLABS)
                                && !state.is(BlockTags.DOORS)
                                && !state.is(BlockTags.TRAPDOORS)
                                && !state.is(BlockTags.STAIRS)
                                && !state.is(Blocks.BEDROCK)
                                && !state.is(FORGE_ORES)
                                && level().getBlockEntity(pos) == null) {
                            pendingBlocks.add(pos);
                            visited.add(pos);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < BLOCKS_PER_TICK && !pendingBlocks.isEmpty(); i++) {
            BlockPos pos = pendingBlocks.poll();
            BlockState current = level().getBlockState(pos);

            if (current.is(ModBlocks.CORRODED_GROUND.get())
                    || current.is(ModBlocks.CORRODED_LEAVES.get())
                    || current.is(ModBlocks.CORRODED_IVY.get()))
                continue;

            if (current.is(BlockTags.LEAVES)) {
                level().setBlockAndUpdate(pos, ModBlocks.CORRODED_LEAVES.get().defaultBlockState());
            } else {
                level().setBlockAndUpdate(pos, ModBlocks.CORRODED_GROUND.get().defaultBlockState());

                int ivyHeight = 1 + level().getRandom().nextInt(4);
                for (int j = 1; j <= ivyHeight; j++) {
                    BlockPos ivyPos = pos.above(j);
                    if (level().isEmptyBlock(ivyPos)) {
                        level().setBlockAndUpdate(ivyPos, ModBlocks.CORRODED_IVY.get().defaultBlockState());
                    } else break;
                }
            }
        }
    }

    // サブクラスが指定：召喚するMobタイプ（複数対応）
    protected abstract List<EntityType<? extends Monster>> getSummonMobTypes();

    protected int getMobsPerSummon() {
        return 3;
    }

    protected int getSummonInterval() {
        return 100;
    }

    protected int getSummonRange() {
        return 10;
    }

    protected int getMaxCorruptionRadius() {
        return 1;
    }

    protected int getMaxCorruptionTime() {
        return 1;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(new AnimationController<>(this, "mainController", 0, state -> {
            state.setAnimation(RawAnimation.begin().then("stay", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }
}
