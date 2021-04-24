package com.github.badnotice.miningworld.commands;

import com.github.badnotice.miningworld.MiningWorldPlugin;
import com.github.badnotice.miningworld.configuration.ConfigValue;
import com.github.badnotice.miningworld.event.impl.MiningJoinWorldEvent;
import com.github.badnotice.miningworld.model.cooldown.Cooldown;
import com.github.badnotice.miningworld.registry.WorldRegistry;
import com.github.badnotice.miningworld.util.LocationUtils;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public final class MineCommand {

    public final Cooldown cooldown = Cooldown.of(
            ConfigValue.get(ConfigValue::delayCommand),
            TimeUnit.SECONDS,
            ConfigValue.get(ConfigValue::messageInCooldown)
    );

    private final WorldRegistry worldRegistry = MiningWorldPlugin.getInstance().getWorldRegistry();

    @Command(
            name = "mine",
            aliases = {"mina", "mineracao", "mining"}
    )
    public void handleCommand(Context<Player> context) {
        Player player = context.getSender();

        if (Bukkit.getWorld(worldRegistry.getName()) == null) {
            player.sendMessage("§cAguarde, mundo de mineração está sendo gerado!");
            return;
        }

        if (cooldown.inCooldown(player, false)) return;

        if (player.getWorld().getName().equals(worldRegistry.getName())) {
            player.sendMessage(ConfigValue.get(ConfigValue::messageInWorld));
            return;
        }

        cooldown.insertCooldown(player);

        ConfigValue.get(ConfigValue::messageJoinWorld).forEach(player::sendMessage);

        player.teleport(LocationUtils.getLocationRandom(
                this.worldRegistry.getName(),
                this.worldRegistry.getRadiusTeleport()
        ));

        MiningJoinWorldEvent miningJoinWorldEvent = new MiningJoinWorldEvent(player, worldRegistry);
        Bukkit.getPluginManager().callEvent(miningJoinWorldEvent);
    }

    @Command(
            name = "mine.createworld",
            aliases = {"criarmundo", "cm"},
            permission = "miningworld.admin"
    )
    public void handleCreateWorldCommand(Context<Player> context) {
        if (worldRegistry.isEnabled()) {
            context.sendMessage("§cO Mundo de mineração já se encontra criado!");
            return;
        }

        worldRegistry.init();
    }

}
