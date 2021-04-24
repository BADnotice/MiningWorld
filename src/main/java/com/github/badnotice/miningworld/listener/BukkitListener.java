package com.github.badnotice.miningworld.listener;

import com.github.badnotice.miningworld.MiningWorldPlugin;
import com.github.badnotice.miningworld.event.impl.MiningDropSpawnEvent;
import com.github.badnotice.miningworld.event.impl.MiningLeaveWorldEvent;
import com.github.badnotice.miningworld.model.Drop;
import com.github.badnotice.miningworld.registry.DropRegistry;
import com.github.badnotice.miningworld.registry.WorldRegistry;
import com.github.badnotice.miningworld.util.MiningWorldUtils;
import com.github.badnotice.miningworld.util.PercentageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class BukkitListener implements Listener {

    private final DropRegistry dropRegistry = MiningWorldPlugin.getInstance().getDropRegistry();
    private final WorldRegistry worldRegistry = MiningWorldPlugin.getInstance().getWorldRegistry();

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (!player.getWorld().getName().equals(worldRegistry.getName()) || block.getType() != Material.STONE) return;
        Drop drop = dropRegistry.getDropRandom();

        if (!MiningWorldUtils.isMiningHeight(block, drop) || !PercentageUtils.inPercent(drop.getPercentage())) return;

        MiningDropSpawnEvent miningDropSpawnEvent = new MiningDropSpawnEvent(
                player,
                drop,
                worldRegistry.getExperience(),
                MiningWorldUtils.getRandomFortune(player.getItemInHand())
        );
        Bukkit.getPluginManager().callEvent(miningDropSpawnEvent);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onItemSpawn(ItemSpawnEvent event) {
        if (!event.getEntity().getWorld().getName().equals(worldRegistry.getName())) return;

        Material block = event.getEntity().getItemStack().getType();
        if (worldRegistry.getMaterials().contains(block)) event.getEntity().remove();
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerChanged(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (!event.getFrom().getName().equals(worldRegistry.getName())) return;

        MiningLeaveWorldEvent miningLeaveWorldEvent = new MiningLeaveWorldEvent(player, worldRegistry);
        Bukkit.getPluginManager().callEvent(miningLeaveWorldEvent);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        MiningLeaveWorldEvent miningLeaveWorldEvent = new MiningLeaveWorldEvent(player, worldRegistry);
        Bukkit.getPluginManager().callEvent(miningLeaveWorldEvent);
    }

}
