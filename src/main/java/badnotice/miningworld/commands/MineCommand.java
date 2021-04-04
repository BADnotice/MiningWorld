package badnotice.miningworld.commands;

import badnotice.miningworld.api.event.impl.MiningJoinWorldEvent;
import badnotice.miningworld.controller.WorldController;
import badnotice.miningworld.util.MiningWorldUtils;
import com.google.inject.Inject;
import badnotice.miningworld.api.model.cooldown.Cooldown;
import badnotice.miningworld.configuration.ConfigurationValue;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public final class MineCommand {

    public final Cooldown cooldown = Cooldown.of(
            ConfigurationValue.get(ConfigurationValue::delayCommand),
            TimeUnit.SECONDS,
            ConfigurationValue.get(ConfigurationValue::messageInCooldown)
    );

    @Inject
    private WorldController worldController;

    @Command(
            name = "mine",
            aliases = {"mina", "mineracao", "mining"}
    )
    public void handleCommand(Context<Player> context) {
        Player player = context.getSender();

        if (Bukkit.getWorld(worldController.getName()) == null) {
            player.sendMessage("§cAguarde, mundo de mineração está sendo gerado!");
            return;
        }

        if (cooldown.inCooldown(player, false)) return;

        if (player.getWorld().getName().equals(worldController.getName())) {
            player.sendMessage(ConfigurationValue.get(ConfigurationValue::messageInWorld));
            return;
        }

        cooldown.insertCooldown(player);

        ConfigurationValue.get(ConfigurationValue::messageJoinWorld).forEach(player::sendMessage);
        player.teleport(MiningWorldUtils.generateLocation(
                this.worldController.getName(),
                this.worldController.getRadiusTeleport()
        ));

        MiningJoinWorldEvent miningJoinWorldEvent = new MiningJoinWorldEvent(player, worldController);
        Bukkit.getPluginManager().callEvent(miningJoinWorldEvent);
    }

    @Command(
            name = "mine.createworld",
            aliases = {"criarmundo", "cm"},
            permission = "miningworld.admin"
    )
    public void handleCreateWorldCommand(Context<Player> context) {
        if (worldController.isEnabled()) {
            context.sendMessage("§cO Mundo de mineração já se encontra criado!");
            return;
        }

        worldController.init();
    }

}
