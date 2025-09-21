package com.ashviper.parainfection.entity.custom.Developed;

import com.ashviper.parainfection.entity.custom.Class.AdaptedBaseEntity;
import com.ashviper.parainfection.entity.custom.Class.DevelopedBaseEntity;
import com.ashviper.parainfection.entity.custom.Purebred.LarvaxEntity;
import com.ashviper.parainfection.phase.PhasePointData;
import com.ashviper.parainfection.regi.ModBlocks;
import com.ashviper.parainfection.regi.ModSoundEvents;
import com.ashviper.parainfection.regi.ParaInfectionMobs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

public class Developed_PlaguelatchEntity extends DevelopedBaseEntity {
    int killCount = 0;
    public Developed_PlaguelatchEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return baseAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(new AnimationController<>(this, "mainController", 0, state -> {
            Developed_PlaguelatchEntity entity = state.getAnimatable();
                // 動いていない → stay アニメーション
            state.setAnimation(RawAnimation.begin().then("stay", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public void awardKillScore(Entity killed, int score, DamageSource source) {
        super.awardKillScore(killed, score, source);

        if (!this.level().isClientSide && killed instanceof LivingEntity) {
            killCount++;
            if(killCount >= 20){
                if (this.level() instanceof ServerLevel serverLevel) {
                    // 縮小＆爆発演出
                    this.setDeltaMovement(0, 0, 0); // 動きを止める
                    this.setNoGravity(true); // 重力無効
                    this.setPos(this.getX(), this.getY(), this.getZ()); // 固定位置

                    // 縮小用タイマー（サーバーでも少し演出）
                    serverLevel.sendParticles(
                            ParticleTypes.EXPLOSION,
                            this.getX(), this.getY() + 0.5, this.getZ(),
                            20, 0.5, 0.5, 0.5, 0.1
                    );

                    // 音も再生
                    serverLevel.playSound(
                            null,
                            this.getX(), this.getY(), this.getZ(),
                            ModSoundEvents.GNAWLINGMEOW.get(),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F
                    );

                    // 新しいエンティティをスポーン
                    LivingEntity newEntity = ParaInfectionMobs.ADAPTED_PLAGUELATCH.get().create(this.level());
                    if (newEntity != null) {
                        newEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                        this.level().addFreshEntity(newEntity);
                    }

                    // 元のエンティティを削除
                    this.remove(RemovalReason.DISCARDED);
                }
                return; // 変身したら以下の召喚処理は行わない
            }


            // === PhasePointData にポイントを加算 ===
            if (this.level() instanceof ServerLevel serverLevel) {
                PhasePointData data = serverLevel.getDataStorage().computeIfAbsent(
                        PhasePointData::load,
                        PhasePointData::new,
                        "parainfection_phase"
                );

                data.addPoints(10.0f); // ★ ここで加算するポイント量はお好みで
            }
        }
    }
}
