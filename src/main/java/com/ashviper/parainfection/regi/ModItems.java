package com.ashviper.parainfection.regi;

import com.ashviper.parainfection.ParaInfectionMod;
import com.ashviper.parainfection.entity.ParainfectionEntities;
import com.ashviper.parainfection.regi.costom.item.Corroded;
import com.ashviper.parainfection.regi.costom.item.CorrodedBow;
import com.ashviper.parainfection.regi.costom.item.MiniSuiren;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ParaInfectionMod.MODID);

    // 卵登録ヘルパー関数
    private static RegistryObject<Item> registerEgg(String name, Supplier<? extends EntityType<? extends Mob>> entityType) {
        return ITEMS.register(name + "_egg",
                () -> new ForgeSpawnEggItem(
                        entityType,
                        0xAABBCC,
                        0x112233,
                        new Item.Properties().stacksTo(64))
        );
    }

    // egg
    public static final RegistryObject<Item> GNAWLING_EGG = registerEgg("gnawling", ParainfectionEntities.GNAWLING);
    public static final RegistryObject<Item> LARVAX_EGG = registerEgg("larvax", ParainfectionEntities.LARVAX);
    public static final RegistryObject<Item> DEVORA_EGG = registerEgg("devora", ParainfectionEntities.DEVORA);
    public static final RegistryObject<Item> INCOMPLETEFIELD_EGG = registerEgg("incompletefield", ParainfectionEntities.INCOMPLETEFIELD);
    public static final RegistryObject<Item> CORRODEDCOW_EGG = registerEgg("corroded_cow", ParainfectionEntities.CORRODEDCOW);
    public static final RegistryObject<Item> CORRODEDCHIKEN_EGG = registerEgg("corroded_chiken", ParainfectionEntities.CORRODEDCHIKEN);
    public static final RegistryObject<Item> CORRODEDSHEEP_EGG = registerEgg("corroded_sheep", ParainfectionEntities.CORRODEDSHEEP);
    public static final RegistryObject<Item> CORRODEDPIG_EGG = registerEgg("corroded_pig", ParainfectionEntities.CORRODEDPIG);
    public static final RegistryObject<Item> CORRODEDZOMBIE_EGG = registerEgg("corroded_zombie", ParainfectionEntities.CORRODEDZOMBIE);
    public static final RegistryObject<Item> CORRODEDCREEPER_EGG = registerEgg("corroded_creeper", ParainfectionEntities.CORRODEDCREEPER);
    public static final RegistryObject<Item> NESTERRA1_EGG = registerEgg("nesterra_phase1", ParainfectionEntities.NESTERRA1);
    public static final RegistryObject<Item> NESTERRA2_EGG = registerEgg("nesterra_phase2", ParainfectionEntities.NESTERRA2);
    public static final RegistryObject<Item> NESTERRA3_EGG = registerEgg("nesterra_phase3", ParainfectionEntities.NESTERRA3);
    public static final RegistryObject<Item> NESTERRA4_EGG = registerEgg("nesterra_phase4", ParainfectionEntities.NESTERRA4);

    // アイテム
    public static final RegistryObject<Item> CORRODED_FRAGMENT = ITEMS.register("corroded_fragments", Corroded::new);
    public static final RegistryObject<Item> CORRODED_EXOSKELETON = ITEMS.register("corroded_exoskeleton", Corroded::new);
    public static final RegistryObject<Item> CORRODED_INGOT = ITEMS.register("corroded_ingot", Corroded::new);
    public static final RegistryObject<Item> MINI_SUIREN = ITEMS.register("mini_suiren", MiniSuiren::new);
    public static final RegistryObject<Item> CORRODED_BOW = ITEMS.register("corroded_bow", CorrodedBow::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}