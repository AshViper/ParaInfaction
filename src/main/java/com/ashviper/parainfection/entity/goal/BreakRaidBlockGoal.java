package com.ashviper.parainfection.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.EnumSet;

public class BreakRaidBlockGoal extends Goal {
    private final Mob mob;
    private final BlockPos raidBlockPos;
    private final Block raidBlock;
    private int hitCount = 0;
    private final int hitsToBreak = 10;

    public BreakRaidBlockGoal(Mob mob, BlockPos raidBlockPos, Block raidBlock) {
        this.mob = mob;
        this.raidBlockPos = raidBlockPos.immutable();
        this.raidBlock = raidBlock;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return mob.level().getBlockState(raidBlockPos).getBlock() == raidBlock;
    }

    @Override
    public boolean canContinueToUse() {
        return mob.level().getBlockState(raidBlockPos).getBlock() == raidBlock &&
                mob.distanceToSqr(raidBlockPos.getCenter()) < 100.0D;
    }

    @Override
    public void start() {
        moveToBlock();
    }

    private void moveToBlock() {
        mob.getNavigation().moveTo(
                raidBlockPos.getX() + 0.5,
                raidBlockPos.getY() + 0.5,
                raidBlockPos.getZ() + 0.5,
                1.0D
        );
    }

    @Override
    public void tick() {
        Level level = mob.level();

        if (level.getBlockState(raidBlockPos).getBlock() != raidBlock) return;

        // 距離チェック・攻撃判定を10tickごとに間引く
        if (mob.tickCount % 20 != mob.getId() % 20) return;

        double dist = mob.distanceToSqr(raidBlockPos.getCenter());
        if (dist < 2.0D) {
            hitCount++;

            // サウンドも間引く
            if (hitCount % 2 == 0) {
                mob.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 1.0F, 1.0F);
            }

            if (hitCount >= hitsToBreak) {
                if (level.getBlockState(raidBlockPos).getBlock() == raidBlock) {
                    level.destroyBlock(raidBlockPos, false);
                    mob.playSound(SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, 1.0F, 1.0F);
                }
            }
        } else {
            // 近くに来るまでの移動は長距離経路計算を間引く
            if (!mob.getNavigation().isDone()) return;
            moveToBlock();
        }
    }
}
