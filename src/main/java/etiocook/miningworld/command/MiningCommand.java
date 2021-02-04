package etiocook.miningworld.command;

import com.google.inject.Inject;
import etiocook.miningworld.configuration.ConfigurationValue;
import etiocook.miningworld.manager.WorldManager;
import etiocook.miningworld.model.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public final class MiningCommand implements CommandExecutor {

    @Inject private WorldManager worldManager;

    private final Cooldown cooldown = Cooldown.of(
            ConfigurationValue.get(ConfigurationValue::delayCommand),
            TimeUnit.SECONDS,
            ConfigurationValue.get(ConfigurationValue::inCooldown)
    );

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (Bukkit.getWorld(worldManager.getName()) == null) {
             player.sendMessage("§cAguarde, mundo de mineração está sendo gerado!");
            return false;
        }

        if (cooldown.inCooldown(player, false)) return false;

        if (player.getWorld().getName().equals(worldManager.getName())) {
            player.sendMessage(ConfigurationValue.get(ConfigurationValue::inWorld));
            return false;
        }

        player.teleport(worldManager.generateLocation());
        ConfigurationValue.get(ConfigurationValue::joinWorld).forEach(player::sendMessage);

        cooldown.insertCooldown(player);
        return false;
    }

}
