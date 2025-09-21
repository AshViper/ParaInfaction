package com.ashviper.parainfection.regi;

import com.ashviper.parainfection.phase.PhasePointData;
import com.ashviper.parainfection.phase.PhasePointStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class ParainfectionEntitySpawnPlacements {

    public static void register() {
        SpawnPlacements.register(
                ParaInfectionMobs.GNAWLING.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ParainfectionEntitySpawnPlacements::canGnawlingSpawn
        );

        SpawnPlacements.register(
                ParaInfectionMobs.DEVORA.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ParainfectionEntitySpawnPlacements::canDevoraSpawn
        );

        SpawnPlacements.register(
                ParaInfectionMobs.CORRODEDCHIKEN.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ParainfectionEntitySpawnPlacements::canCorrodedSpawn
        );

        SpawnPlacements.register(
                ParaInfectionMobs.CORRODEDSHEEP.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ParainfectionEntitySpawnPlacements::canCorrodedSpawn
        );

        SpawnPlacements.register(
                ParaInfectionMobs.CORRODEDCOW.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ParainfectionEntitySpawnPlacements::canCorrodedSpawn
        );

        SpawnPlacements.register(
                ParaInfectionMobs.CORRODEDPIG.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ParainfectionEntitySpawnPlacements::canCorrodedSpawn
        );

        SpawnPlacements.register(
                ParaInfectionMobs.CORRODEDZOMBIE.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ParainfectionEntitySpawnPlacements::canCorrodedSpawn
        );

        SpawnPlacements.register(
                ParaInfectionMobs.LARVAX.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ParainfectionEntitySpawnPlacements::canLarvaxSpawn
        );
    }

    private static boolean isValidSpawn(ServerLevelAccessor level, BlockPos pos) {
        BlockPos below = pos.below();
        BlockState blockState = level.getBlockState(below);
        if (blockState.is(ModBlocks.CORRODED_GROUND.get())) {
            return true;
        }

        // それ以外の場所では通常の明るさチェック
        return Monster.isDarkEnoughToSpawn(level, pos, level.getRandom());
    }

    private static PhasePointData getPhaseData(ServerLevelAccessor level) {
        if (level instanceof ServerLevel serverLevel) {
            return PhasePointStorage.get(serverLevel);
        }
        return null;
    }

    private static boolean canLarvaxSpawn(EntityType<?> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        PhasePointData data = getPhaseData(level);
        if (data == null || data.getPhase() > 1) return false;
        //System.out.println("[DEBUG] Phase data: " + data + ", phase=" + (data != null ? data.getPhase() : "null"));
        return isValidSpawn(level, pos);
    }

    private static boolean canGnawlingSpawn(EntityType<?> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        PhasePointData data = getPhaseData(level);
        if (data == null || data.getPhase() < 2) return false;
        //System.out.println("[DEBUG] Phase data: " + data + ", phase=" + (data != null ? data.getPhase() : "null"));
        return isValidSpawn(level, pos);
    }

    private static boolean canDevoraSpawn(EntityType<?> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        PhasePointData data = getPhaseData(level);
        //System.out.println("[DEBUG] Phase data: " + data + ", phase=" + (data != null ? data.getPhase() : "null"));
        if (data == null || data.getPhase() < 5) return false;
        return isValidSpawn(level, pos);
    }

    private static boolean canCorrodedSpawn(EntityType<?> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        PhasePointData data = getPhaseData(level);
        //System.out.println("[DEBUG] Phase data: " + data + ", phase=" + (data != null ? data.getPhase() : "null"));
        if (data == null || data.getPhase() < 3) return false;
        return isValidSpawn(level, pos);
    }
}
