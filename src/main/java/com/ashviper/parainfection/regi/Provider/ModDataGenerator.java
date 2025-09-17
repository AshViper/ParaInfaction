package com.ashviper.parainfection.regi.Provider;

import com.ashviper.parainfection.ParaInfectionMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ParaInfectionMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        PackOutput output = event.getGenerator().getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();

        event.getGenerator().addProvider(event.includeClient(), new ModItemModelProvider(output, helper));
        event.getGenerator().addProvider(event.includeClient(), new ModBlockStateProvider(output, helper));
    }
}
