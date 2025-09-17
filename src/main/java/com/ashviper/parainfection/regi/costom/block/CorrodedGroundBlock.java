package com.ashviper.parainfection.regi.costom.block;

import com.ashviper.parainfection.regi.costom.baseClass.BaseBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CorrodedGroundBlock extends BaseBlock {
    public CorrodedGroundBlock() {
        super();
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        if (random.nextFloat() < 0.2f) { // 出現頻度をやや控えめに
            double x = pos.getX() + 0.5 + (random.nextFloat() - 0.5);
            double y = pos.getY() + 1.1;
            double z = pos.getZ() + 0.5 + (random.nextFloat() - 0.5);

            level.addParticle(ParticleTypes.SPORE_BLOSSOM_AIR, x, y, z, 0, 0.01, 0);
        }
    }
}