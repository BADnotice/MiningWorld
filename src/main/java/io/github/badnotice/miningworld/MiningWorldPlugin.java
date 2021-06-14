package io.github.badnotice.miningworld;

import io.github.badnotice.miningworld.registry.*;
import io.github.badnotice.miningworld.util.FileUtils;
import lombok.Getter;
import me.bristermitten.pdm.PluginDependencyManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MiningWorldPlugin extends JavaPlugin {

    private final WorldRegistry worldRegistry = new WorldRegistry();
    private final RewardRegistry rewardRegistry = new RewardRegistry();

    public static MiningWorldPlugin getInstance() {
        return getPlugin(MiningWorldPlugin.class);
    }

    @Override
    public void onEnable() {
        PluginDependencyManager.of(this)
                .loadAllDependencies()
                .exceptionally(throwable -> {
                    throwable.printStackTrace();

                    getLogger().severe("Ocorreu um erro durante a inicialização do plugin!");
                    Bukkit.getPluginManager().disablePlugin(this);

                    return null;
                })
                .join();

        InjectConfigurationRegistry.of(this).init();
        BukkitFrameRegistry.of(this).init();
        ListenerRegistry.of(this).init();

        worldRegistry.init();
        rewardRegistry.init();

        int pluginId = 11682;
        new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {
        FileUtils.deleteDir(Bukkit.getWorld(worldRegistry.getName()).getWorldFolder());
    }

}
