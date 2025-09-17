package com.ashviper.parainfection.phase;

import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.server.level.ServerLevel;

public class PhasePointStorage {
    private static final String DATA_NAME = "parainfection_phase";

    public static PhasePointData get(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(PhasePointData::load, PhasePointData::new, DATA_NAME);
    }
}
