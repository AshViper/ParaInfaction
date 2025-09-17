package com.ashviper.parainfection.phase;

import com.ashviper.parainfection.regi.ModSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PhaseTickHandler {

    public static void resetPhaseState(ServerLevel world) {
        PhasePointData data = world.getDataStorage().computeIfAbsent(
                PhasePointData::load,
                PhasePointData::new,
                "parainfection_phase"
        );
        data.setLastPhase(data.getPhase()); // ← これで初期化時に通知を抑制
        data.setDirty();
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.level.isClientSide()) {
            ServerLevel world = (ServerLevel) event.level;

            PhasePointData data = world.getDataStorage().computeIfAbsent(
                    PhasePointData::load,
                    PhasePointData::new,
                    "parainfection_phase"
            );

            data.tick(); // ← 正しく10秒ごとに1ポイント加算

            int currentPhase = data.getPhase();

            if (currentPhase != data.getLastPhase()) {
                data.setLastPhase(currentPhase);

                Component message = Component.literal("Phase " + currentPhase);
                for (ServerPlayer player : world.players()) {
                    player.sendSystemMessage(message);
                    Vec3 pos = player.position();
                    switch (currentPhase) {
                        case 0: break;
                        case 1: world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.PHASE1.get(), SoundSource.PLAYERS, 1.0F, 1.0F); break;
                        case 2: world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.PHASE2.get(), SoundSource.PLAYERS, 1.0F, 1.0F); break;
                        case 3: world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.PHASE3.get(), SoundSource.PLAYERS, 1.0F, 1.0F); break;
                        case 4: world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.PHASE4.get(), SoundSource.PLAYERS, 1.0F, 1.0F); break;
                        case 5: world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.PHASE5.get(), SoundSource.PLAYERS, 1.0F, 1.0F); break;
                        case 6: world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.PHASE6.get(), SoundSource.PLAYERS, 1.0F, 1.0F); break;
                        case 7: world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.PHASE7.get(), SoundSource.PLAYERS, 1.0F, 1.0F); break;
                        case 8: world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.PHASE8.get(), SoundSource.PLAYERS, 1.0F, 1.0F); break;
                        case 9: world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.PHASE9.get(), SoundSource.PLAYERS, 1.0F, 1.0F); break;
                        case 10: world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.PHASE10.get(), SoundSource.PLAYERS, 1.0F, 1.0F); break;
                    }
                }

                data.setDirty(); // フェーズが変わったら保存
            }

            // 各フェーズのロジックを書く場所
        }
    }
}