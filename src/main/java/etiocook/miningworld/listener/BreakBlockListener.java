package etiocook.miningworld.listener;

import com.google.inject.Inject;
import etiocook.miningworld.configuration.ConfigurationValue;
import etiocook.miningworld.manager.DropManager;
import etiocook.miningworld.manager.WorldManager;
import etiocook.miningworld.model.Drop;
import etiocook.miningworld.util.PercentageUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public final class BreakBlockListener implements Listener {

    @Inject private DropManager dropManager;
    @Inject private WorldManager worldManager;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (!playerIsValid(player, block)) return;

        Drop drop = dropManager.findDropByHeight(player.getLocation().getBlockY());
        if (drop == null || !PercentageUtils.inPercent(drop.getPercentage())) return;

        for (int i = 0; i < getLevelFortune(player.getItemInHand()); i++) sendItemStack(player, drop.getItemStack());

    }

    private void sendItemStack(Player player, ItemStack itemStack) {
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ConfigurationValue.get(ConfigurationValue::inventoryFull));
            player.getWorld().dropItem(player.getLocation(), itemStack);
            return;
        }
        player.getInventory().addItem(itemStack);
    }

    private boolean playerIsValid(Player player, Block block) {
        if (!player.getWorld().getName().equals(worldManager.getName())) return false;
        return block.getType() == Material.STONE;
    }

    private Integer getLevelFortune(ItemStack itemStack) {
        int i = 1;

        if (itemStack.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
            int levelEnchant = itemStack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            i = new Random().nextInt(levelEnchant + 1);
        }

        return i;
    }

}