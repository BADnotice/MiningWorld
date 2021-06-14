package io.github.badnotice.miningworld.listener;

import io.github.badnotice.miningworld.MiningWorldPlugin;
import io.github.badnotice.miningworld.api.event.impl.MiningLeaveWorldEvent;
import io.github.badnotice.miningworld.api.event.impl.MiningRewardSpawnEvent;
import io.github.badnotice.miningworld.model.Reward;
import io.github.badnotice.miningworld.registry.WorldRegistry;
import io.github.badnotice.miningworld.util.MiningWorldUtils;
import io.github.badnotice.miningworld.util.PercentageUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class BukkitListener implements Listener {

    private final MiningWorldPlugin plugin;

    public BukkitListener(MiningWorldPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        World playerWorld = player.getWorld();
        WorldRegistry worldRegistry = this.plugin.getWorldRegistry();

        if (!playerWorld.getName().equals(worldRegistry.getName())) return;

        if (block.getType() != Material.STONE) return;
        Reward rewardRandom = this.plugin.getRewardRegistry().getRewardRandom();

        if (!MiningWorldUtils.isMiningHeight(block, rewardRandom) || !PercentageUtils.inPercent(rewardRandom.getPercentage()))
            return;

        MiningRewardSpawnEvent dropSpawnEvent = new MiningRewardSpawnEvent(
                player,
                rewardRandom,
                worldRegistry.getExperience(),
                MiningWorldUtils.getRandomFortune(player.getItemInHand())
        );

        dropSpawnEvent.call();
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onItemSpawn(ItemSpawnEvent event) {
        Item entity = event.getEntity();
        WorldRegistry worldRegistry = this.plugin.getWorldRegistry();

        if (!entity.getWorld().getName().equals(worldRegistry.getName())) return;

        Material block = entity.getItemStack().getType();
        event.setCancelled(worldRegistry.getMaterials().contains(block));
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerChanged(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        WorldRegistry worldRegistry = this.plugin.getWorldRegistry();

        if (!event.getFrom().getName().equals(worldRegistry.getName())) return;

        MiningLeaveWorldEvent leaveWorldEvent = new MiningLeaveWorldEvent(
                player,
                worldRegistry
        );

        leaveWorldEvent.call();
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        WorldRegistry worldRegistry = this.plugin.getWorldRegistry();

        if (!player.getWorld().getName().equals(worldRegistry.getName())) return;

        MiningLeaveWorldEvent leaveWorldEvent = new MiningLeaveWorldEvent(
                player,
                worldRegistry
        );

        leaveWorldEvent.call();
    }

}
