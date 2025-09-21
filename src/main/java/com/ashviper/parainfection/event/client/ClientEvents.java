package com.ashviper.parainfection.event.client;

import com.ashviper.parainfection.regi.ParaInfectionMobs;
import com.ashviper.parainfection.entity.renderer.*;
import com.ashviper.parainfection.entity.renderer.Corroded.*;
import com.ashviper.parainfection.entity.renderer.Purebred.*;
import com.ashviper.parainfection.regi.ModItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("removal")
@Mod.EventBusSubscriber(modid = "parainfection", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemProperties.register(ModItems.CORRODED_BOW.get(), new ResourceLocation("pulling"),
                (stack, world, entity, seed) -> {
                    return (entity != null && entity.isUsingItem() && entity.getUseItem() == stack) ? 1.0F : 0.0F;
                });
        ItemProperties.register(ModItems.CORRODED_BOW.get(), new ResourceLocation("pull"),
                (stack, world, entity, seed) -> {
                    if (entity == null) {
                        return 0.0F;
                    } else {
                        return entity.getUseItem() != stack ? 0.0F :
                                (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
                    }
                });
        EntityRenderers.register(ParaInfectionMobs.GNAWLING.get(), GnawlingEntityRenderer::new);
        EntityRenderers.register(ParaInfectionMobs.LARVAX.get(), LarvaxEntityRenderer::new);
        EntityRenderers.register(ParaInfectionMobs.DEVORA.get(), DevoraEntityRenderer::new);
        EntityRenderers.register(ParaInfectionMobs.INCOMPLETEFIELD.get(), IncompletefieldRenderer::new);
        EntityRenderers.register(ParaInfectionMobs.CORRODEDCOW.get(), CorrodedCowEntityRenderer::new);
        EntityRenderers.register(ParaInfectionMobs.CORRODEDCHIKEN.get(), CorrodedChikenEntityRenderer::new);
        EntityRenderers.register(ParaInfectionMobs.CORRODEDSHEEP.get(), CorrodedSheepEntityRenderer::new);
        EntityRenderers.register(ParaInfectionMobs.CORRODEDPIG.get(), CorrodedPigEntityRenderer::new);
        EntityRenderers.register(ParaInfectionMobs.CORRODEDZOMBIE.get(), CorrodedZombieEntityRenderer::new);
        EntityRenderers.register(ParaInfectionMobs.CORRODEDCREEPER.get(), CorrodedCreeperEntityRenderer::new);
        EntityRenderers.register(ParaInfectionMobs.NESTERRA1.get(), Nesterra1EntityRenderer::new);
        EntityRenderers.register(ParaInfectionMobs.NESTERRA2.get(), Nesterra2EntityRenderer::new);
        EntityRenderers.register(ParaInfectionMobs.NESTERRA3.get(), Nesterra3EntityRenderer::new);
        EntityRenderers.register(ParaInfectionMobs.NESTERRA4.get(), Nesterra4EntityRenderer::new);
    }
}
