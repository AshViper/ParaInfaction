package com.ashviper.parainfection.entity.custom.Adapted;

import com.ashviper.parainfection.entity.custom.Class.AdaptedBaseEntity;
import com.ashviper.parainfection.regi.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class Adapted_PlaguelatchEntity extends AdaptedBaseEntity {
    public Adapted_PlaguelatchEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return baseAttributes()
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 20.0D);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(new AnimationController<>(this, "mainController", 0, state -> {
            Adapted_PlaguelatchEntity entity = state.getAnimatable();
                // 動いていない → stay アニメーション
            state.setAnimation(RawAnimation.begin().then("stay", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }));
    }

    private void replaceGroundWithAcid() {
        if (this.level().isClientSide) return; // サーバー側のみ処理

        int radius = 2;
        BlockPos center = this.blockPosition().below(); // 足元基準

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                double distance = Math.sqrt(dx * dx + dz * dz);
                if (distance > radius) continue; // 半径外はスキップ

                BlockPos pos = center.offset(dx, 0, dz);
                BlockState currentBlock = this.level().getBlockState(pos);

                // 置換条件: 空気、置換可能、土系ブロックなど
                if (!currentBlock.isAir() || currentBlock.is(Blocks.DIRT) || currentBlock.is(Blocks.STONE)) {
                    this.level().setBlockAndUpdate(pos, ModBlocks.ACID_DAMAGE_GROUND.get().defaultBlockState());

                    // 爆発パーティクル（見た目）
                    if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(
                                ParticleTypes.SMOKE,
                                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                                2, 0.2, 0.1, 0.2, 0.01
                        );
                    }
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            // 足元の座標を取得
            this.replaceGroundWithAcid();
        }
    }
}
