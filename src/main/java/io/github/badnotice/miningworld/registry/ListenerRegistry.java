package io.github.badnotice.miningworld.registry;

import io.github.badnotice.miningworld.MiningWorldPlugin;
import io.github.badnotice.miningworld.listener.BukkitListener;
import io.github.badnotice.miningworld.listener.MiningWorldListener;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

@Data(staticConstructor = "of")
public final class ListenerRegistry {

    private final MiningWorldPlugin plugin;

    public void init() {
        registerListeners(
                new MiningWorldListener(),
                new BukkitListener(this.plugin)
        );
    }

    private void registerListeners(Listener... listeners) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, plugin);
        }
    }

}
