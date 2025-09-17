package com.ashviper.parainfection.event;

import com.ashviper.parainfection.entity.custom.Class.ParainfectionBaseEntity;
import com.ashviper.parainfection.regi.costom.block.RaidBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber
public class ModEvents {
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        // 攻撃者が ParainfectionBaseEntity でなければ無視
        if (!(event.getSource().getEntity() instanceof ParainfectionBaseEntity)) return;

        // 倒されたエンティティが Player ならドロップを無効化しない
        if (event.getEntity() instanceof Player) return;

        // プレイヤー以外がParainfectionのMobに倒された場合はドロップをキャンセル
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) return;
        for (ServerLevel level : event.getServer().getAllLevels()) {
            RaidBlock.tick(level);
        }
    }

    @SubscribeEvent
    public static void onMobDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Monster mob) || event.getEntity().level().isClientSide()) return;
        ServerLevel level = (ServerLevel) event.getEntity().level();
        UUID uuid = mob.getUUID();

        RaidBlock.raidMobs.forEach((pos, uuidSet) -> {
            if (uuidSet.remove(uuid) && uuidSet.isEmpty()) {
                // 全滅：5秒後にフェーズ移行をスケジュール
                RaidBlock.pendingPhaseTransitions.put(pos, level.getGameTime() + 100);
            }
        });
    }
}