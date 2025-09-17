package com.ashviper.parainfection.entity.goal;

import com.ashviper.parainfection.entity.custom.Purebred.IncompletefieldEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class MoveTowardsSameEntityGoal extends Goal {
    private final IncompletefieldEntity mob;
    private final double speed;
    private final double searchRadius;

    public MoveTowardsSameEntityGoal(IncompletefieldEntity mob, double speed, double searchRadius) {
        this.mob = mob;
        this.speed = speed;
        this.searchRadius = searchRadius;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        AABB box = this.mob.getBoundingBox().inflate(searchRadius);
        List<IncompletefieldEntity> list = this.mob.level().getEntitiesOfClass(IncompletefieldEntity.class, box,
                e -> e != mob && e.isAlive());

        if (!list.isEmpty()) {
            // 一番近い個体をターゲットに設定
            IncompletefieldEntity closest = list.stream()
                    .min((a, b) -> Double.compare(mob.distanceToSqr(a), mob.distanceToSqr(b)))
                    .orElse(null);

            if (closest != null) {
                mob.setTargetEntity(closest);
                return true;
            }
        }

        return false;
    }

    @Override
    public void start() {
        LivingEntity target = mob.getTargetEntity();
        if (target != null) {
            mob.getNavigation().moveTo(target, speed);
        }
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = mob.getTargetEntity();
        return target != null && target.isAlive()
                && mob.distanceToSqr(target) > 2.0
                && mob.getNavigation().isInProgress();
    }

    @Override
    public void stop() {
        mob.setTargetEntity(null);
    }
}
