package io.github.badnotice.miningworld.listener;

import io.github.badnotice.miningworld.api.event.impl.MiningJoinWorldEvent;
import io.github.badnotice.miningworld.api.event.impl.MiningLeaveWorldEvent;
import io.github.badnotice.miningworld.api.event.impl.MiningRewardSpawnEvent;
import io.github.badnotice.miningworld.configuration.ConfigValue;
import io.github.badnotice.miningworld.model.Reward;
import io.github.badnotice.miningworld.registry.WorldRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public final class MiningWorldListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onMiningJoinWorld(MiningJoinWorldEvent event) {
        WorldRegistry worldRegistry = event.getController();
        worldRegistry.getEffects().forEach(event.getPlayer()::addPotionEffect);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onMiningLeaveWorld(MiningLeaveWorldEvent event) {
        WorldRegistry worldRegistry = event.getController();
        Player player = event.getPlayer();

        for (PotionEffect activePotionEffect : player.getActivePotionEffects()) {
            for (PotionEffect effect : worldRegistry.getEffects()) {
                if (activePotionEffect.getType().equals(effect.getType()))
                    player.removePotionEffect(activePotionEffect.getType());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onMiningWorld(MiningRewardSpawnEvent event) {
        Reward reward = event.getReward();

        Player player = event.getPlayer();
        player.giveExp(event.getExperience());

        for (int i = 0; i < event.getAmount(); i++) sendItem(player, reward.getItemStack());
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
