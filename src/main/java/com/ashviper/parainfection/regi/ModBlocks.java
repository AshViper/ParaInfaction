package com.ashviper.parainfection.regi;

import com.ashviper.parainfection.ParaInfectionMod;
import com.ashviper.parainfection.regi.costom.block.CorrodedGroundBlock;
import com.ashviper.parainfection.regi.costom.block.CorrodedIvyBlock;
import com.ashviper.parainfection.regi.costom.block.CorrodedLeavesBlock;
import com.ashviper.parainfection.regi.costom.block.RaidBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ParaInfectionMod.MODID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ParaInfectionMod.MODID);

    // 例: カスタムブロック登録
    public static final RegistryObject<Block> CORRODED_GROUND = registerBlock("corroded_ground",
            CorrodedGroundBlock::new);
    public static final RegistryObject<Block> CORRODED_LEAVES = registerBlock("corroded_leaves",
            CorrodedLeavesBlock::new);
    public static final RegistryObject<Block> CORRODED_IVY = registerBlock("corroded_ivy",
            CorrodedIvyBlock::new);

    public static final RegistryObject<Block> RAID_BLOCK = registerBlock("raid_block",
            () -> new RaidBlock(BlockBehaviour.Properties.of()
                    .strength(2.0f, 6.0F)
                    .requiresCorrectToolForDrops()));

    private static RegistryObject<Block> registerBlock(String name, java.util.function.Supplier<Block> blockSupplier) {
        RegistryObject<Block> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
