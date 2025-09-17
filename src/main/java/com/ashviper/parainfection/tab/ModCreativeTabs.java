package com.ashviper.parainfection.tab;

import com.ashviper.parainfection.ParaInfectionMod;
import com.ashviper.parainfection.regi.ModBlocks;
import com.ashviper.parainfection.regi.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ParaInfectionMod.MODID);

    public static final RegistryObject<CreativeModeTab> PARA_INFECTION_TAB = TABS.register("parainfection",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.parainfection"))
                    .icon(() -> ModItems.GNAWLING_EGG.map(ItemStack::new)
                            .orElse(new ItemStack(Items.SLIME_BALL)))
                    .displayItems((parameters, output) -> {
                        // ModItems の自動追加
                        ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));

                        // ModBlocks の自動追加（BlockItem に変換できるもののみ）
                        ModBlocks.BLOCKS.getEntries().forEach(block -> {
                            Block b = block.get();
                            Item item = ForgeRegistries.ITEMS.getValue(ForgeRegistries.BLOCKS.getKey(b));
                            if (item != null) {
                                output.accept(item);
                            }
                        });
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}