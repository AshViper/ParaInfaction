package com.ashviper.parainfection.event;

import com.ashviper.parainfection.regi.costom.item.MiniSuiren;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "parainfection")
public class MiniSuirenEventHandler {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof MiniSuiren) {
                CompoundTag nbt = stack.getOrCreateTag();
                int usesLeft = nbt.contains(MiniSuiren.NBT_USES_LEFT_KEY)
                        ? nbt.getInt(MiniSuiren.NBT_USES_LEFT_KEY)
                        : MiniSuiren.MAX_USES;

                if (usesLeft > 0) {
                    event.setCanceled(true);
                    player.setHealth(player.getMaxHealth() / 2.0F);

                    usesLeft--;
                    nbt.putInt(MiniSuiren.NBT_USES_LEFT_KEY, usesLeft);

                    if (usesLeft <= 0) {
                        stack.shrink(1);
                    }
                    break;
                }
            }
        }
    }
}

