package badnotice.miningworld.listener;

import badnotice.miningworld.api.event.impl.MiningDropSpawnEvent;
import badnotice.miningworld.api.event.impl.MiningJoinWorldEvent;
import badnotice.miningworld.api.event.impl.MiningLeaveWorldEvent;
import badnotice.miningworld.api.model.Drop;
import badnotice.miningworld.configuration.ConfigurationValue;
import badnotice.miningworld.controller.DropController;
import badnotice.miningworld.controller.WorldController;
import badnotice.miningworld.util.PercentageUtils;
import com.google.inject.Inject;
import badnotice.miningworld.util.MiningWorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

public final class MiningWorldListener implements Listener {

    @Inject private WorldController worldController;

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onMiningJoinWorld(MiningJoinWorldEvent event) {
        WorldController controller = event.getController();
        controller.getEffects().forEach(event.getPlayer()::addPotionEffect);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onMiningLeaveWorld(MiningLeaveWorldEvent event) {
        WorldController controller = event.getController();
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
            player.sendMessage(ConfigurationValue.get(ConfigurationValue::messageInventoryFull));
            player.getWorld().dropItem(player.getLocation(), itemStack);

        } else {
            player.getInventory().addItem(itemStack);
        }
    }

}
