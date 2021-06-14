package io.github.badnotice.miningworld.commands;

import io.github.badnotice.miningworld.MiningWorldPlugin;
import io.github.badnotice.miningworld.api.event.impl.MiningJoinWorldEvent;
import io.github.badnotice.miningworld.builder.CooldownBuilder;
import io.github.badnotice.miningworld.configuration.ConfigValue;
import io.github.badnotice.miningworld.registry.WorldRegistry;
import io.github.badnotice.miningworld.util.LocationUtils;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public final class MineCommand {

    public final CooldownBuilder cooldownBuilder = CooldownBuilder.of(
            TimeUnit.SECONDS,
            ConfigValue.get(ConfigValue::messageInCooldown)
    );
    private final MiningWorldPlugin plugin;

    public MineCommand(MiningWorldPlugin plugin) {
        this.plugin = plugin;
    }

    @Command(
            name = "mine",
            aliases = {"mina", "mineracao", "mining"}
    )
    public void handleMineCommand(Context<Player> context) {
        Player player = context.getSender();
        WorldRegistry worldRegistry = this.plugin.getWorldRegistry();

        if (Bukkit.getWorld(worldRegistry.getName()) == null) {
            player.sendMessage("§cAguarde, mundo de mineração está sendo gerado!");
            return;
        }

        if (cooldownBuilder.inCooldown(player)) return;

        if (player.getWorld().getName().equals(worldRegistry.getName())) {
            player.sendMessage(ConfigValue.get(ConfigValue::messageInWorld));
            return;
        }

        cooldownBuilder.insertCooldown(player, ConfigValue.get(ConfigValue::delayCommand));
        ConfigValue.get(ConfigValue::messageJoinWorld).forEach(player::sendMessage);

        player.teleport(LocationUtils.getRandom(
                worldRegistry.getName(),
                worldRegistry.getRadiusTeleport()
        ));

        MiningJoinWorldEvent joinWorldEvent = new MiningJoinWorldEvent(
                player,
                worldRegistry
        );


        joinWorldEvent.call();
    }

    @Command(
            name = "mine.createworld",
            aliases = {"criarmundo"},
            permission = "miningworld.command.createworld"
    )
    public void handleCreateWorldCommand(Context<?> context) {
        if (this.plugin.getWorldRegistry().isEnabled()) {
            context.sendMessage("§cO Mundo de mineração já se encontra criado!");
            return;
        }

        this.plugin.getWorldRegistry().init();
    }

}
