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

        if (!isMiningWorld(player, block)) return;

        Drop drop = dropManager.getDropRandom();
        if (!isMiningHeight(block, drop) || !PercentageUtils.inPercent(drop.getPercentage())) return;

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

    private boolean isMiningWorld(Player player, Block block) {
        return player.getWorld().getName().equals(worldManager.getName()) && block.getType() == Material.STONE;
    }

    private boolean isMiningHeight(Block block, Drop drop) {
        return block.getLocation().getY() >= drop.getHeightMin() && block.getLocation().getY() <= drop.getHeightMax();
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