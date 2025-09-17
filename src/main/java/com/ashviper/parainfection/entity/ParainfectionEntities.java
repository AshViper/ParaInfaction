package com.ashviper.parainfection.entity;


import com.ashviper.parainfection.ParaInfectionMod;
import com.ashviper.parainfection.entity.custom.*;
import com.ashviper.parainfection.entity.custom.Corroded.*;
import com.ashviper.parainfection.entity.custom.Purebred.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParainfectionEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ParaInfectionMod.MODID);

    public static final RegistryObject<EntityType<GnawlingEntity>> GNAWLING = ENTITY_TYPES.register(
            "gnawling",
            ()-> EntityType.Builder.of(GnawlingEntity::new, MobCategory.MONSTER)
                    .sized(1f, 0.5f)
                    .build("gnawling"));

    public static final RegistryObject<EntityType<LarvaxEntity>> LARVAX = ENTITY_TYPES.register(
            "larvax",
            ()-> EntityType.Builder.of(LarvaxEntity::new, MobCategory.MONSTER)
                    .sized(1f, 0.5f)
                    .build("larvax"));

    public static final RegistryObject<EntityType<DevoraEntity>> DEVORA = ENTITY_TYPES.register(
            "devora",
            ()-> EntityType.Builder.of(DevoraEntity::new, MobCategory.MONSTER)
                    .sized(1f, 0.5f)
                    .build("devora"));

    public static final RegistryObject<EntityType<IncompletefieldEntity>> INCOMPLETEFIELD = ENTITY_TYPES.register(
            "incompletefield",
            ()-> EntityType.Builder.of(IncompletefieldEntity::new, MobCategory.MONSTER)
                    .sized(1f, 0.5f)
                    .build("incompletefield"));

    public static final RegistryObject<EntityType<CorrodedCowEntity>> CORRODEDCOW = ENTITY_TYPES.register(
            "corroded_cow",
            ()-> EntityType.Builder.of(CorrodedCowEntity::new, MobCategory.MONSTER)
                    .sized(1.0f, 1.5f)
                    .build("corroded_cow"));

    public static final RegistryObject<EntityType<CorrodedChikenEntity>> CORRODEDCHIKEN = ENTITY_TYPES.register(
            "corroded_chiken",
            ()-> EntityType.Builder.of(CorrodedChikenEntity::new, MobCategory.MONSTER)
                    .sized(0.8f, 0.8f)
                    .build("corroded_chiken"));

    public static final RegistryObject<EntityType<CorrodedSheepEntity>> CORRODEDSHEEP = ENTITY_TYPES.register(
            "corroded_sheep",
            ()-> EntityType.Builder.of(CorrodedSheepEntity::new, MobCategory.MONSTER)
                    .sized(1.0f, 1.5f)
                    .build("corroded_sheep"));

    public static final RegistryObject<EntityType<CorrodedPigEntity>> CORRODEDPIG = ENTITY_TYPES.register(
            "corroded_pig",
            ()-> EntityType.Builder.of(CorrodedPigEntity::new, MobCategory.MONSTER)
                    .sized(1.0f, 1.5f)
                    .build("corroded_pig"));

    public static final RegistryObject<EntityType<CorrodedZombieEntity>> CORRODEDZOMBIE = ENTITY_TYPES.register(
            "corroded_zombie",
            ()-> EntityType.Builder.of(CorrodedZombieEntity::new, MobCategory.MONSTER)
                    .sized(0.8f, 2.0f)
                    .build("corroded_zombie"));

    public static final RegistryObject<EntityType<CorrodedCreeperEntity>> CORRODEDCREEPER = ENTITY_TYPES.register(
            "corroded_creeper",
            ()-> EntityType.Builder.of(CorrodedCreeperEntity::new, MobCategory.MONSTER)
                    .sized(0.8f, 2.0f)
                    .build("corroded_creeper"));

    public static final RegistryObject<EntityType<Nesterra1Entity>> NESTERRA1 = ENTITY_TYPES.register(
            "nesterra_phase1",
            ()-> EntityType.Builder.of(Nesterra1Entity::new, MobCategory.MONSTER)
                    .sized(0.8f, 2.0f)
                    .build("nesterra_phase1"));

    public static final RegistryObject<EntityType<Nesterra2Entity>> NESTERRA2 = ENTITY_TYPES.register(
            "nesterra_phase2",
            ()-> EntityType.Builder.of(Nesterra2Entity::new, MobCategory.MONSTER)
                    .sized(2.0f, 3.0f)
                    .build("nesterra_phase2"));

    public static final RegistryObject<EntityType<Nesterra3Entity>> NESTERRA3 = ENTITY_TYPES.register(
            "nesterra_phase3",
            ()-> EntityType.Builder.of(Nesterra3Entity::new, MobCategory.MONSTER)
                    .sized(3.0f, 5.0f)
                    .build("nesterra_phase3"));

    public static final RegistryObject<EntityType<Nesterra4Entity>> NESTERRA4 = ENTITY_TYPES.register(
            "nesterra_phase4",
            ()-> EntityType.Builder.of(Nesterra4Entity::new, MobCategory.MONSTER)
                    .sized(4.0f, 8.0f)
                    .build("nesterra_phase4"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
