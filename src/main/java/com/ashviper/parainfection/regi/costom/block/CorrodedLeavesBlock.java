package com.ashviper.parainfection.regi.costom.block;

import com.ashviper.parainfection.regi.costom.baseClass.BaseBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CorrodedLeavesBlock extends BaseBlock {
    public CorrodedLeavesBlock() {
        super(BlockBehaviour.Properties
                .of()
                .sound(SoundType.CHERRY_LEAVES)
                .noOcclusion()
        );
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 0; // 光を透過
    }
}
