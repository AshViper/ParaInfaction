package com.ashviper.parainfection.regi.Provider;

import com.ashviper.parainfection.ParaInfectionMod;
import com.ashviper.parainfection.regi.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ParaInfectionMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (RegistryObject<Block> blockRO : ModBlocks.BLOCKS.getEntries()) {
            Block block = blockRO.get();
            ResourceLocation name = ForgeRegistries.BLOCKS.getKey(block);

            if (name != null) {
                simpleBlock(block, cubeAll(block));
            }
        }
    }
}
