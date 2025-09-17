package com.ashviper.parainfection.regi.costom.baseClass;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;


public abstract class BaseBlock extends Block {
    public BaseBlock() {
        super(BlockBehaviour.Properties
                .of()
                .mapColor(MapColor.DIRT)
                .instrument(NoteBlockInstrument.BASS)
                .strength(0.5f)
                .sound(SoundType.GRAVEL)
                .pushReaction(PushReaction.NORMAL)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)
                .requiresCorrectToolForDrops()
        );
    }
    // プロパティ指定コンストラクタ（新しい拡張用）
    public BaseBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        ItemStack tool = player.getMainHandItem();
        return tool.getItem() instanceof HoeItem;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 20; // 数値が高いほど燃えやすい（木材は20）
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 50; // 数値が高いほど早く燃え広がる（木材は5〜60）
    }
}
