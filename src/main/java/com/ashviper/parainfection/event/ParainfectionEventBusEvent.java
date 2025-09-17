package com.ashviper.parainfection.event;

import com.ashviper.parainfection.ParaInfectionMod;
import com.ashviper.parainfection.entity.ParainfectionEntities;
import com.ashviper.parainfection.entity.custom.*;
import com.ashviper.parainfection.entity.custom.Corroded.*;
import com.ashviper.parainfection.entity.custom.Purebred.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ParaInfectionMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParainfectionEventBusEvent {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ParainfectionEntities.GNAWLING.get(), GnawlingEntity.createAttributes().build());
        event.put(ParainfectionEntities.LARVAX.get(), LarvaxEntity.createAttributes().build());
        event.put(ParainfectionEntities.DEVORA.get(), DevoraEntity.createAttributes().build());
        event.put(ParainfectionEntities.INCOMPLETEFIELD.get(), IncompletefieldEntity.createAttributes().build());
        event.put(ParainfectionEntities.CORRODEDCOW.get(), CorrodedCowEntity.createAttributes().build());
        event.put(ParainfectionEntities.CORRODEDCHIKEN.get(), CorrodedChikenEntity.createAttributes().build());
        event.put(ParainfectionEntities.CORRODEDSHEEP.get(), CorrodedSheepEntity.createAttributes().build());
        event.put(ParainfectionEntities.CORRODEDPIG.get(), CorrodedPigEntity.createAttributes().build());
        event.put(ParainfectionEntities.CORRODEDZOMBIE.get(), CorrodedZombieEntity.createAttributes().build());
        event.put(ParainfectionEntities.CORRODEDCREEPER.get(), CorrodedCreeperEntity.createAttributes().build());
        event.put(ParainfectionEntities.NESTERRA1.get(), Nesterra1Entity.createAttributes().build());
        event.put(ParainfectionEntities.NESTERRA2.get(), Nesterra2Entity.createAttributes().build());
        event.put(ParainfectionEntities.NESTERRA3.get(), Nesterra3Entity.createAttributes().build());
        event.put(ParainfectionEntities.NESTERRA4.get(), Nesterra4Entity.createAttributes().build());
    }
}
