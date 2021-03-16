package badnotice.miningworld.listener.custom;

import badnotice.miningworld.api.event.MiningDropSpawnEvent;
import badnotice.miningworld.api.model.Drop;
import badnotice.miningworld.controller.WorldController;
import com.google.inject.Inject;
import badnotice.miningworld.configuration.ConfigurationValue;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public final class MiningDropSpawnListener implements Listener {

    @Inject private WorldController worldController;

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
