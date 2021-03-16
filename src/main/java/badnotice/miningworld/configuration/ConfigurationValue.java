package badnotice.miningworld.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.function.Function;

@Getter
@Accessors(fluent = true)
@ConfigFile("configuration.yml")
@TranslateColors
public final class ConfigurationValue implements ConfigurationInjectable {

    public static ConfigurationValue instance = new ConfigurationValue();

    @ConfigField("drops") private ConfigurationSection drops;
    @ConfigField("world") private ConfigurationSection world;

    @ConfigField("delay-command") private Integer delayCommand;

    @ConfigField("messages.in-cooldown") private String messageInCooldown;
    @ConfigField("messages.in-world") private String messageInWorld;
    @ConfigField("messages.join-world") private List<String> messageJoinWorld;
    @ConfigField("messages.inventory-full")  private String messageInventoryFull;

    public static <T> T get(Function<ConfigurationValue, T> function) {
        return function.apply(instance);
    }

}