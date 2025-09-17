package com.ashviper.parainfection.event;

import com.ashviper.parainfection.ParaInfectionMod;
import com.ashviper.parainfection.entity.ParainfectionEntities;
import com.ashviper.parainfection.entity.renderer.*;
import com.ashviper.parainfection.entity.renderer.Corroded.*;
import com.ashviper.parainfection.entity.renderer.Purebred.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ParaInfectionMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParainfectionEventBusClientEvent {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event){

    }
    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ParainfectionEntities.GNAWLING.get(), GnawlingEntityRenderer::new);
        event.registerEntityRenderer(ParainfectionEntities.LARVAX.get(), LarvaxEntityRenderer::new);
        event.registerEntityRenderer(ParainfectionEntities.DEVORA.get(), DevoraEntityRenderer::new);
        event.registerEntityRenderer(ParainfectionEntities.INCOMPLETEFIELD.get(), IncompletefieldRenderer::new);
        event.registerEntityRenderer(ParainfectionEntities.CORRODEDCOW.get(), CorrodedCowEntityRenderer::new);
        event.registerEntityRenderer(ParainfectionEntities.CORRODEDCHIKEN.get(), CorrodedChikenEntityRenderer::new);
        event.registerEntityRenderer(ParainfectionEntities.CORRODEDSHEEP.get(), CorrodedSheepEntityRenderer::new);
        event.registerEntityRenderer(ParainfectionEntities.CORRODEDPIG.get(), CorrodedPigEntityRenderer::new);
        event.registerEntityRenderer(ParainfectionEntities.CORRODEDZOMBIE.get(), CorrodedZombieEntityRenderer::new);
        event.registerEntityRenderer(ParainfectionEntities.CORRODEDCREEPER.get(), CorrodedCreeperEntityRenderer::new);
        event.registerEntityRenderer(ParainfectionEntities.NESTERRA1.get(), Nesterra1EntityRenderer::new);
        event.registerEntityRenderer(ParainfectionEntities.NESTERRA2.get(), Nesterra2EntityRenderer::new);
        event.registerEntityRenderer(ParainfectionEntities.NESTERRA3.get(), Nesterra3EntityRenderer::new);
        event.registerEntityRenderer(ParainfectionEntities.NESTERRA4.get(), Nesterra4EntityRenderer::new);
    }
}
