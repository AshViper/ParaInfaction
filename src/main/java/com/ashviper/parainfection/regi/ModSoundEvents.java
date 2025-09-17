package com.ashviper.parainfection.regi;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.ashviper.parainfection.ParaInfectionMod;

@SuppressWarnings("removal")
public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ParaInfectionMod.MODID);

    public static final RegistryObject<SoundEvent> PHASE1 = SOUND_EVENTS.register("phase1",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParaInfectionMod.MODID, "phase1")));
    public static final RegistryObject<SoundEvent> PHASE2 = SOUND_EVENTS.register("phase2",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParaInfectionMod.MODID, "phase2")));
    public static final RegistryObject<SoundEvent> PHASE3 = SOUND_EVENTS.register("phase3",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParaInfectionMod.MODID, "phase3")));
    public static final RegistryObject<SoundEvent> PHASE4 = SOUND_EVENTS.register("phase4",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParaInfectionMod.MODID, "phase4")));
    public static final RegistryObject<SoundEvent> PHASE5 = SOUND_EVENTS.register("phase5",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParaInfectionMod.MODID, "phase5")));
    public static final RegistryObject<SoundEvent> PHASE6 = SOUND_EVENTS.register("phase6",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParaInfectionMod.MODID, "phase6")));
    public static final RegistryObject<SoundEvent> PHASE7 = SOUND_EVENTS.register("phase7",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParaInfectionMod.MODID, "phase7")));
    public static final RegistryObject<SoundEvent> PHASE8 = SOUND_EVENTS.register("phase8",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParaInfectionMod.MODID, "phase8")));
    public static final RegistryObject<SoundEvent> PHASE9 = SOUND_EVENTS.register("phase9",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParaInfectionMod.MODID, "phase9")));
    public static final RegistryObject<SoundEvent> PHASE10 = SOUND_EVENTS.register("phase10",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParaInfectionMod.MODID, "phase10")));
    public static final RegistryObject<SoundEvent> GNAWLINGMEOW = SOUND_EVENTS.register("gnawlingmeow",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParaInfectionMod.MODID, "gnawlingmeow")));
    public static final RegistryObject<SoundEvent> NESTERRA1SPAWN = SOUND_EVENTS.register("nesterra1spawn",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ParaInfectionMod.MODID, "nesterra1spawn")));
}
