package com.github.badnotice.miningworld;

import com.github.badnotice.miningworld.commands.MineCommand;
import com.github.badnotice.miningworld.registry.DropRegistry;
import com.github.badnotice.miningworld.registry.WorldRegistry;
import com.github.badnotice.miningworld.listener.BukkitListener;
import com.github.badnotice.miningworld.listener.MiningWorldListener;
import com.github.badnotice.miningworld.util.FileUtils;
import com.github.badnotice.miningworld.configuration.ConfigValue;
import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import lombok.Getter;
import me.bristermitten.pdm.PluginDependencyManager;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MiningWorldPlugin extends JavaPlugin {

    private final WorldRegistry worldRegistry = new WorldRegistry();
    private final DropRegistry dropRegistry = new DropRegistry();

    public static MiningWorldPlugin getInstance() {
        return getPlugin(MiningWorldPlugin.class);
    }

    @Override
    public void onEnable() {
        initInjectConfiguration();
        Bukkit.getScheduler().runTask(this, this.worldRegistry::init);

        initListener();
        PluginDependencyManager.of(this).loadAllDependencies().thenRun(this::initBukkitFrame);
    }

    @Override
    public void onDisable() {
        FileUtils.deleteDir(Bukkit.getWorld(worldRegistry.getName()).getWorldFolder());
    }

    private void initInjectConfiguration() {
        BukkitConfigurationInjector customConfiguration = new BukkitConfigurationInjector(this);
        customConfiguration.saveDefaultConfiguration(this, "config.yml");
        customConfiguration.injectConfiguration(ConfigValue.instance);
    }

    private void initListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new MiningWorldListener(), this);
        pluginManager.registerEvents(new BukkitListener(), this);
    }

    private void initBukkitFrame() {
        BukkitFrame bukkitFrame = new BukkitFrame(this);
        bukkitFrame.registerCommands(new MineCommand());
    }

}
