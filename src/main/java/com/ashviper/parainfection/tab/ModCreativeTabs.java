package com.ashviper.parainfection.tab;

import com.ashviper.parainfection.ParaInfectionMod;
import com.ashviper.parainfection.regi.ModBlocks;
import com.ashviper.parainfection.regi.ModItems;
import com.ashviper.parainfection.regi.ParaInfectionMobs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, ParaInfectionMod.MODID);

    public static final RegistryObject<CreativeModeTab> PARA_INFECTION_TAB = TABS.register("parainfection",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.parainfection"))
                    .icon(() -> new ItemStack(ModItems.MINI_SUIREN.get())) // タブのアイコンはスポーン卵
                    .displayItems((parameters, output) -> {
                        // ModItems のスポーン卵のみ追加
                        ModItems.ITEMS.getEntries().forEach(item -> {
                            if (item.get() instanceof ForgeSpawnEggItem) {
                                output.accept(item.get());
                            }
                        });

                        // ParaInfectionMobs の spawn egg を自動追加
                        ParaInfectionMobs.ITEMS.getEntries().forEach(item -> {
                            if (item.get() instanceof net.minecraftforge.common.ForgeSpawnEggItem) {
                                output.accept(item.get());
                            }
                        });

                        // ModBlocks の自動追加（BlockItem に変換できるもののみ）
                        ModBlocks.BLOCKS.getEntries().forEach(block -> {
                            Block b = block.get();
                            Item item = b.asItem();
                            if (item != null) {
                                output.accept(item);
                            }
                        });
                    })
                    .build());

    public static void register(net.minecraftforge.eventbus.api.IEventBus eventBus) {
        TABS.register(eventBus);
    }
}
