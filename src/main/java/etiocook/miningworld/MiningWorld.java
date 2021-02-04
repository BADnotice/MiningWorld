package etiocook.miningworld;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import etiocook.miningworld.command.MiningCommand;
import etiocook.miningworld.configuration.ConfigurationValue;
import etiocook.miningworld.guice.PluginModule;
import etiocook.miningworld.listener.BreakBlockListener;
import etiocook.miningworld.listener.ItemSpawnListener;
import etiocook.miningworld.manager.DropManager;
import etiocook.miningworld.manager.WorldManager;
import etiocook.miningworld.scheduler.MiningWorldRunnable;
import lombok.Getter;
import me.bristermitten.pdm.PluginDependencyManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiningWorld extends JavaPlugin {

    @Getter private Injector injector;

    @Inject private WorldManager worldManager;
    @Inject private DropManager dropManager;

    @Inject private MiningCommand miningCommand;
    @Inject private MiningWorldRunnable miningWorldRunnable;

    @Override
    public void onEnable() {
        PluginDependencyManager.of(this).loadAllDependencies().thenRun(() -> {
            try {
                enableInjectConfiguration();

                this.injector = PluginModule.of(this).createInjector();
                this.injector.injectMembers(this);

                this.dropManager.init();
                Bukkit.getScheduler().runTaskLater(this, () -> this.worldManager.init(),10);

                registerListener(ItemSpawnListener.class);
                registerListener(BreakBlockListener.class);

                this.getCommand("mining").setExecutor(this.miningCommand);

                enableRunnable();
            } catch (Throwable t) {
                t.printStackTrace();
                Bukkit.getLogger().severe("Um erro ocorreu na inicializado plugin!");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        });
    }

    @Override
    public void onDisable() {
        worldManager.deleteDir(Bukkit.getWorld(worldManager.getName()).getWorldFolder());
    }

    private void enableInjectConfiguration() {
        BukkitConfigurationInjector customConfiguration = new BukkitConfigurationInjector(this);
        customConfiguration.saveDefaultConfiguration(this, "configuration.yml");
        customConfiguration.injectConfiguration(ConfigurationValue.instance);
    }

    private void enableRunnable() {
        this.miningWorldRunnable.runTaskTimer(this,
                ConfigurationValue.get(ConfigurationValue::delayEffects),
                ConfigurationValue.get(ConfigurationValue::delayEffects));
    }

    private void registerListener(Class<? extends Listener> clazz) {
        Bukkit.getPluginManager().registerEvents(injector.getInstance(clazz), this);
    }

}
