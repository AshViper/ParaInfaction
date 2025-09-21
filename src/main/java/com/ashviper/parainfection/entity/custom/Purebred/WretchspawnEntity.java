package com.ashviper.parainfection.entity.custom.Purebred;

import com.ashviper.parainfection.entity.custom.Class.ParainfectionFlyingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class WretchspawnEntity extends ParainfectionFlyingEntity {

    public WretchspawnEntity(EntityType<? extends FlyingMob> type, Level level) {
        super(type, level);
        this.moveControl = new GhastMoveControl(this); // ガスト風のMoveControlに変更
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new GhastRandomFlyGoal(this)); // ガスト風のランダム飛行
    }

    // ====================
    // ガスト風のMoveControl
    // ====================
    static class GhastMoveControl extends net.minecraft.world.entity.ai.control.MoveControl {
        private final FlyingMob mob;
        private int courseChangeCooldown;

        public GhastMoveControl(FlyingMob mob) {
            super(mob);
            this.mob = mob;
        }

        @Override
        public void tick() {
            if (this.operation == Operation.MOVE_TO) {
                double dx = this.wantedX - mob.getX();
                double dy = this.wantedY - mob.getY();
                double dz = this.wantedZ - mob.getZ();
                double distance = Math.sqrt(dx*dx + dy*dy + dz*dz);

                if (distance < 1.0) {
                    this.operation = Operation.WAIT;
                    mob.setDeltaMovement(mob.getDeltaMovement().scale(0.5));
                    return;
                }

                mob.setDeltaMovement(
                        mob.getDeltaMovement().add(
                                dx / distance * 0.1,
                                dy / distance * 0.1,
                                dz / distance * 0.1
                        )
                );

                // 方向を向く
                float f = (float)(Math.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
                mob.setYRot(f);
            }
        }
    }

    // ====================
    // ランダム飛行Goal
    // ====================
    static class GhastRandomFlyGoal extends Goal {
        private final FlyingMob mob;

        public GhastRandomFlyGoal(FlyingMob mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return mob.getTarget() == null && mob.getRandom().nextInt(30) == 0;
        }

        @Override
        public void start() {
            double x = mob.getX() + (mob.getRandom().nextDouble() * 20 - 10);
            double y = mob.getY() + (mob.getRandom().nextDouble() * 10 - 5);
            double z = mob.getZ() + (mob.getRandom().nextDouble() * 20 - 10);
            mob.getMoveControl().setWantedPosition(x, y, z, 1.0);
        }
    }
}
