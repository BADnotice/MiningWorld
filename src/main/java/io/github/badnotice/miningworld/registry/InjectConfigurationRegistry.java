package io.github.badnotice.miningworld.registry;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import io.github.badnotice.miningworld.configuration.ConfigValue;
import io.github.badnotice.miningworld.configuration.RewardValue;
import lombok.Data;
import org.bukkit.plugin.java.JavaPlugin;

@Data(staticConstructor = "of")
public final class InjectConfigurationRegistry {

    private final JavaPlugin plugin;

    public void init() {
        BukkitConfigurationInjector configurationInjector = new BukkitConfigurationInjector(plugin);

        configurationInjector.saveDefaultConfiguration(
                plugin,
                "config.yml",
                "rewards.yml"
        );

        configurationInjector.injectConfiguration(
                ConfigValue.instance,
                RewardValue.instance
        );

    }

}