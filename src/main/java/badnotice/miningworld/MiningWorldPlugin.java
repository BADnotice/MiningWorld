package badnotice.miningworld;

import badnotice.miningworld.commands.MineCommand;
import badnotice.miningworld.controller.DropController;
import badnotice.miningworld.controller.WorldController;
import badnotice.miningworld.guice.PluginModule;
import badnotice.miningworld.listener.MiningWorldListener;
import badnotice.miningworld.util.FileUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import badnotice.miningworld.configuration.ConfigurationValue;
import badnotice.miningworld.listener.custom.MiningDropSpawnListener;
import lombok.Getter;
import me.bristermitten.pdm.PluginDependencyManager;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MiningWorldPlugin extends JavaPlugin {

    @Getter private Injector injector;

    @Inject private WorldController worldController;
    @Inject private DropController dropController;

    @Override
    public void onEnable() {
        PluginDependencyManager.of(this).loadAllDependencies().thenRun(() -> {
            try {
                initInjectConfiguration();

                this.injector = PluginModule.of(this).createInjector();
                this.injector.injectMembers(this);

                this.dropController.init();
                Bukkit.getScheduler().runTask(this, () -> this.worldController.init());

                initBukkitFrame();
                initListener();
            } catch (Throwable t) {
                t.printStackTrace();
                Bukkit.getLogger().severe("Um erro ocorreu na inicializado plugin!");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        });
    }

    @Override
    public void onDisable() {
        FileUtils.deleteDir(Bukkit.getWorld(worldController.getName()).getWorldFolder());
    }

    private void initInjectConfiguration() {
        BukkitConfigurationInjector customConfiguration = new BukkitConfigurationInjector(this);
        customConfiguration.saveDefaultConfiguration(this, "configuration.yml");
        customConfiguration.injectConfiguration(ConfigurationValue.instance);
    }

    private void initListener() {
        Bukkit.getPluginManager().registerEvents(injector.getInstance(MiningWorldListener.class), this);
        Bukkit.getPluginManager().registerEvents(injector.getInstance(MiningDropSpawnListener.class), this);
    }

    private void initBukkitFrame() {
        BukkitFrame bukkitFrame = new BukkitFrame(this);
        bukkitFrame.registerCommands(this.injector.getInstance(MineCommand.class));
    }

}
