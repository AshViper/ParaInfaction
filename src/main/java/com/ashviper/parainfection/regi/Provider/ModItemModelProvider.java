package com.ashviper.parainfection.regi.Provider;

import com.ashviper.parainfection.ParaInfectionMod;
import com.ashviper.parainfection.regi.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ParaInfectionMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> itemRO : ModItems.ITEMS.getEntries()) {
            Item item = itemRO.get();
            ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);

            if (key == null || !key.getNamespace().equals(ParaInfectionMod.MODID)) {
                // 他のModのアイテムが混ざっていたら無視
                continue;
            }

            String name = key.getPath();

            if (item instanceof BlockItem) {
                withExistingParent(name, modLoc("block/" + name));
            } else {
                withExistingParent(name, mcLoc("item/generated"))
                        .texture("layer0", modLoc("item/" + name));
            }
        }
    }
}
