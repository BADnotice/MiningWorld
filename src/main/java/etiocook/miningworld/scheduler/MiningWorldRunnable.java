package etiocook.miningworld.scheduler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import etiocook.miningworld.manager.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

@Singleton
public final class MiningWorldRunnable extends BukkitRunnable {

    @Inject private WorldManager worldManager;

    @Override
    public void run() {

        Bukkit.getOnlinePlayers().forEach(player -> {

            if (player.getWorld().getName().equals(worldManager.getName())) {
                worldManager.getEffects().forEach($ -> {
                    String[] split = $.split(";");
                    player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(split[0]), 20 * Integer.parseInt(split[1]), Integer.parseInt(split[2])));
                });
            }

        });

    }

}
