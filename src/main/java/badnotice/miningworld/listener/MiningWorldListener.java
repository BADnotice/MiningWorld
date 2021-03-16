package badnotice.miningworld.listener;

import badnotice.miningworld.api.event.MiningDropSpawnEvent;
import badnotice.miningworld.api.model.Drop;
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

public final class MiningWorldListener implements Listener {

    @Inject private DropController dropController;
    @Inject private WorldController worldController;

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (!player.getWorld().getName().equals(worldController.getName()) || block.getType() != Material.STONE) return;
        Drop drop = dropController.getDropRandom();

        if (!MiningWorldUtils.isMiningHeight(block, drop) || !PercentageUtils.inPercent(drop.getPercentage())) return;

        MiningDropSpawnEvent miningDropSpawnEvent = new MiningDropSpawnEvent(
                player,
                drop,
                worldController.getExperience(),
                MiningWorldUtils.getRandomFortune(player.getItemInHand())
        );
        Bukkit.getPluginManager().callEvent(miningDropSpawnEvent);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onItemSpawn(ItemSpawnEvent event) {
        if (!event.getEntity().getWorld().getName().equals(worldController.getName())) return;

        Material block = event.getEntity().getItemStack().getType();
        if (worldController.getMaterials().contains(block)) event.getEntity().remove();
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerChanged(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (world.getName().equalsIgnoreCase(worldController.getName())) {
            worldController.getEffects().forEach(player::addPotionEffect);
            return;
        }

        if (!event.getFrom().getName().equals(worldController.getName())) return;

        player.getActivePotionEffects().forEach($ -> worldController.getEffects().forEach(potionEffect -> {
            if ($.getType().equals(potionEffect.getType())) player.removePotionEffect($.getType());
        }));
    }

}
