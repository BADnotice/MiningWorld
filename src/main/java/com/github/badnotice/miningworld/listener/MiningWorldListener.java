package com.github.badnotice.miningworld.listener;

import com.github.badnotice.miningworld.MiningWorldPlugin;
import com.github.badnotice.miningworld.event.impl.MiningDropSpawnEvent;
import com.github.badnotice.miningworld.event.impl.MiningJoinWorldEvent;
import com.github.badnotice.miningworld.event.impl.MiningLeaveWorldEvent;
import com.github.badnotice.miningworld.model.Drop;
import com.github.badnotice.miningworld.configuration.ConfigValue;
import com.github.badnotice.miningworld.registry.WorldRegistry;
import com.google.inject.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public final class MiningWorldListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onMiningJoinWorld(MiningJoinWorldEvent event) {
        WorldRegistry controller = event.getController();
        controller.getEffects().forEach(event.getPlayer()::addPotionEffect);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onMiningLeaveWorld(MiningLeaveWorldEvent event) {
        WorldRegistry controller = event.getController();
        Player player = event.getPlayer();

        player.getActivePotionEffects().forEach($ -> controller.getEffects().forEach(potionEffect -> {
            if ($.getType().equals(potionEffect.getType())) player.removePotionEffect($.getType());
        }));
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onMiningWorld(MiningDropSpawnEvent event) {
        Drop drop = event.getDrop();

        Player player = event.getPlayer();
        player.giveExp(event.getExperience());

        for (int i = 0; i < event.getAmount(); i++) sendItem(player, drop.getItemStack());
    }

    private void sendItem(Player player, ItemStack itemStack) {
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ConfigValue.get(ConfigValue::messageInventoryFull));
            player.getWorld().dropItem(player.getLocation(), itemStack);

        } else {
            player.getInventory().addItem(itemStack);
        }
    }

}
