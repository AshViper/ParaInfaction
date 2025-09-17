package com.ashviper.parainfection.regi.costom.block;

import com.ashviper.parainfection.regi.costom.baseClass.BaseBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class CorrodedIvyBlock extends BaseBlock {
    public CorrodedIvyBlock() {
        super(Properties
                .of()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .noCollission()
                .randomTicks() // tick() を有効にするために必要
        );
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 0; // 光を透過
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos below = pos.below();
        if (level.isEmptyBlock(below)) {
            level.removeBlock(pos, false); // 下が空気なら自壊
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        level.scheduleTick(pos, this, 1); // 設置直後に tick をスケジュール
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, net.minecraft.world.level.block.Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
        level.scheduleTick(pos, this, 1); // 隣接ブロックが変化したときにも tick をスケジュール
    }
}
