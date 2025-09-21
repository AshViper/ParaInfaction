package com.ashviper.parainfection.entity.custom.Purebred;

import com.ashviper.parainfection.entity.goal.MoveTowardsSameEntityGoal;
import com.ashviper.parainfection.regi.ParaInfectionMobs;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class IncompletefieldEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int checkTimer = 0;
    private static final int CHECK_INTERVAL = 20 * 5; // 5秒ごと
    private IncompletefieldEntity targetEntity;

    public IncompletefieldEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 10.0D);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MoveTowardsSameEntityGoal(this, 0.9D, 50.0D));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.3D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            checkTimer++;
            if (checkTimer >= CHECK_INTERVAL) {
                checkTimer = 0;
                tryMerge();
            }
        }
    }

    private void tryMerge() {
        int range = 5;
        List<IncompletefieldEntity> nearby = level().getEntitiesOfClass(
                IncompletefieldEntity.class,
                this.getBoundingBox().inflate(range),
                e -> e.isAlive() && e != this
        );

        if (nearby.size() >= 3) {
            double centerX = (this.getX() + nearby.get(0).getX() + nearby.get(1).getX() + nearby.get(2).getX()) / 4;
            double centerY = (this.getY() + nearby.get(0).getY() + nearby.get(1).getY() + nearby.get(2).getY()) / 4;
            double centerZ = (this.getZ() + nearby.get(0).getZ() + nearby.get(1).getZ() + nearby.get(2).getZ()) / 4;

            if (!level().isClientSide) {
                var newEntity = ParaInfectionMobs.DEVELOPED_PLAGUELATCH.get().create(level());
                if (newEntity != null) {
                    newEntity.moveTo(centerX, centerY, centerZ, this.getYRot(), this.getXRot());
                    level().addFreshEntity(newEntity);
                }

                this.discard();
                nearby.get(0).discard();
                nearby.get(1).discard();
                nearby.get(2).discard();

                // サーバーからクライアントにパーティクルを送信
                ((ServerLevel) level()).sendParticles(
                        net.minecraft.core.particles.ParticleTypes.EXPLOSION,
                        centerX, centerY + 0.5, centerZ,
                        20, // 数
                        0.3, 0.3, 0.3, // spread
                        0.1 // speed
                );
            }
        }
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

    public IncompletefieldEntity getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(IncompletefieldEntity targetEntity) {
        this.targetEntity = targetEntity;
    }
}
