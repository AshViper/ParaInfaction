package com.ashviper.parainfection.regi;

import com.ashviper.parainfection.ParaInfectionMod;
import com.ashviper.parainfection.effect.InfectionEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEffect {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, ParaInfectionMod.MODID);

    public static final RegistryObject<MobEffect> INFECTION =
            EFFECTS.register("infection", InfectionEffect::new);
}
