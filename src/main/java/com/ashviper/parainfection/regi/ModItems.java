package com.ashviper.parainfection.regi;

import com.ashviper.parainfection.ParaInfectionMod;
import com.ashviper.parainfection.regi.costom.item.Corroded;
import com.ashviper.parainfection.regi.costom.item.CorrodedBow;
import com.ashviper.parainfection.regi.costom.item.MiniSuiren;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ParaInfectionMod.MODID);

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