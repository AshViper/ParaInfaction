package com.ashviper.parainfection;

import com.ashviper.parainfection.phase.PhaseTickHandler;
import com.ashviper.parainfection.regi.ModBlocks;
import com.ashviper.parainfection.entity.ParainfectionEntities;
import com.ashviper.parainfection.entity.ParainfectionEntitySpawnPlacements;
import com.ashviper.parainfection.regi.ModEffect;
import com.ashviper.parainfection.regi.ModItems;
import com.ashviper.parainfection.phase.PhaseCommand;
import com.ashviper.parainfection.regi.ModSoundEvents;
import com.ashviper.parainfection.tab.ModCreativeTabs;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib.GeckoLib;

@SuppressWarnings("removal")
@Mod(ParaInfectionMod.MODID)
public class ParaInfectionMod {
    public static final String MODID = "parainfection";

    public ParaInfectionMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        GeckoLib.initialize();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup); // ← クライアント側初期化イベントを追加

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ParainfectionEntities.register(modEventBus);
        ModSoundEvents.SOUND_EVENTS.register(modEventBus);
        ModEffect.EFFECTS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(PhaseTickHandler.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ParainfectionEntitySpawnPlacements::register);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CORRODED_IVY.get(), RenderType.translucent());
        });
    }
}