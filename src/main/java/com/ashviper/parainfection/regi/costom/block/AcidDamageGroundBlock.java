package com.ashviper.parainfection.regi.costom.block;

import com.ashviper.parainfection.entity.custom.Class.ParainfectionBaseEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AcidDamageGroundBlock extends Block {
    public AcidDamageGroundBlock() {
        super(BlockBehaviour.Properties
                .of()
                .mapColor(MapColor.DIRT)
                .instrument(NoteBlockInstrument.BASS)
                .strength(0.5f)
                .sound(SoundType.SAND)
                .pushReaction(PushReaction.NORMAL)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)
                .requiresCorrectToolForDrops()
        );
    }
    // プロパティ指定コンストラクタ（新しい拡張用）
    public AcidDamageGroundBlock(BlockBehaviour.Properties properties) {
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
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, 20); // 20ティック後に最初のtickを呼ぶ
        }
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // ブロック上のEntityを取得
        List<LivingEntity> entities = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(pos).inflate(0.5, 1.0, 0.5),
                e -> {
                    // ModIDが "parainfection" のエンティティは除外
                    ResourceLocation typeId = EntityType.getKey(e.getType());
                    return typeId == null || !typeId.getNamespace().equals("parainfection");
                }
        );

        for (LivingEntity entity : entities) {
            // 毒を付与（3秒、強さ1）
            entity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1));
        }

        // 次のtickをスケジュール（20ティック後）
        level.scheduleTick(pos, this, 20);
    }
}
