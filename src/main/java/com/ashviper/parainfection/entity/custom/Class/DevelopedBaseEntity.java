package com.ashviper.parainfection.entity.custom.Class;

import com.ashviper.parainfection.entity.custom.Purebred.LarvaxEntity;
import com.ashviper.parainfection.phase.PhasePointData;
import com.ashviper.parainfection.regi.ModItems;
import com.ashviper.parainfection.regi.ModSoundEvents;
import com.ashviper.parainfection.regi.ParaInfectionMobs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class DevelopedBaseEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    int killCount = 0;

    protected DevelopedBaseEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder baseAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 200.0D);     // 視認距離
    }
    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.8D, true)); // 攻撃行動は上位に置く

        // 最後に他の LivingEntity を攻撃（異種Modエンティティを含めた敵対対象）
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, LivingEntity.class, /* targetChance= */ 1, true, false, target -> {
            if (target == null || !target.isAlive() || target == this) return false;
            if (target.getType() == this.getType()) return false;

            ResourceLocation thisId = EntityType.getKey(this.getType());
            ResourceLocation targetId = EntityType.getKey(target.getType());
            if (thisId != null && targetId != null && thisId.getNamespace().equals(targetId.getNamespace())) return false;

            return this.distanceToSqr(target) < 20000.0; // 20ブロック以内
        }));

        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.5D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(new AnimationController<>(this, "mainController", 0, state -> {
            DevelopedBaseEntity entity = state.getAnimatable();

            if (entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
                // 動いている → walk アニメーション
                state.setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            } else {
                // 動いていない → stay アニメーション
                state.setAnimation(RawAnimation.begin().then("stay", Animation.LoopType.LOOP));
            }

            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void awardKillScore(Entity killed, int score, DamageSource source) {
        super.awardKillScore(killed, score, source);

        if (!this.level().isClientSide && killed instanceof LivingEntity) {
            killCount++;

            if(killCount % 5 == 0){
                    EntityType<LarvaxEntity> type = ParaInfectionMobs.LARVAX.get();

                    for (int i = 0; i < 5; i++) {
                        LarvaxEntity newEntity = type.create(this.level());
                        if (newEntity != null) {
                            // 少しランダムで位置をずらす
                            double offsetX = (this.random.nextDouble() - 0.5) * 2.0;
                            double offsetZ = (this.random.nextDouble() - 0.5) * 2.0;
                            newEntity.moveTo(killed.getX() + offsetX, killed.getY(), killed.getZ() + offsetZ,
                                    this.random.nextFloat() * 360, 0);
                            this.level().addFreshEntity(newEntity);
                        }
                    }
                if (level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(
                            ParticleTypes.EXPLOSION,
                            killed.getX() + 0.5, killed.getY() + 0.5, killed.getZ() + 0.5,
                            1, 0, 0, 0, 0.01
                    );
                }
            }
            // 爆発パーティクル（見た目のみ）


            // === PhasePointData にポイントを加算 ===
            if (this.level() instanceof ServerLevel serverLevel) {
                PhasePointData data = serverLevel.getDataStorage().computeIfAbsent(
                        PhasePointData::load,
                        PhasePointData::new,
                        "parainfection_phase"
                );

                data.addPoints(15.0f); // ★ ここで加算するポイント量はお好みで
            }
        }
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);

        if (!this.level().isClientSide) {
            RandomSource random = this.level().getRandom();

            // 30% の確率で召喚
            if (random.nextFloat() < 0.1f) {
                EntityType<?> summonType = ParaInfectionMobs.NESTERRA1.get();
                LivingEntity newEntity = (LivingEntity) summonType.create(this.level());

                if (newEntity != null) {
                    final int minDistance = 10;
                    final int maxDistance = 30;

                    for (int attempts = 0; attempts < 10; attempts++) { // 最大10回試す
                        double angle = random.nextDouble() * 2 * Math.PI;
                        double distance = minDistance + (random.nextDouble() * (maxDistance - minDistance));

                        double dx = Math.cos(angle) * distance;
                        double dz = Math.sin(angle) * distance;

                        double x = this.getX() + dx;
                        double z = this.getZ() + dz;

                        BlockPos targetPos = BlockPos.containing(x, this.getY(), z);
                        double y = this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, targetPos).getY();
                        BlockPos spawnPos = BlockPos.containing(x, y, z);

                        // 液体ブロック（水・溶岩など）でなければスポーン
                        if (this.level().getBlockState(spawnPos).getFluidState().isEmpty()) {
                            newEntity.moveTo(x, y, z, this.getYRot(), 0);
                            this.level().addFreshEntity(newEntity);
                            this.level().playSound(
                                    null,
                                    x, y, z,
                                    ModSoundEvents.NESTERRA1SPAWN.get(),
                                    SoundSource.HOSTILE,
                                    1.0F,
                                    1.0F
                            );
                            break; // 成功したらループ終了
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int lootingLevel, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, lootingLevel, recentlyHit);

        if (!this.level().isClientSide) {
            RandomSource random = this.getRandom();

            // 例：腐った肉を 50% の確率でドロップ
            if (random.nextFloat() < 0.7f) {
                this.spawnAtLocation(new ItemStack(ModItems.CORRODED_FRAGMENT.get()));
            }

            // 例：レアドロップ（Mod アイテム）を lootingLevel に応じてドロップ
            if (random.nextFloat() < 0.1f) {
                this.spawnAtLocation(new ItemStack(ModItems.CORRODED_EXOSKELETON.get()));
            }
        }
    }
}