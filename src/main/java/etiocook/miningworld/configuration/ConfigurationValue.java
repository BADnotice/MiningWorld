package etiocook.miningworld.configuration;

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
public final class ConfigurationValue implements ConfigurationInjectable {

    public static ConfigurationValue instance = new ConfigurationValue();

    @ConfigField("drops") private ConfigurationSection drops;
    @ConfigField("world") private ConfigurationSection world;

    @ConfigField("delay-command") private Integer delayCommand;
    @ConfigField("delay-effects") private Integer delayEffects;

    @ConfigField("messages.in-cooldown") @TranslateColors private String inCooldown;
    @ConfigField("messages.in-world") @TranslateColors private String inWorld;
    @ConfigField("messages.join-world") @TranslateColors private List<String> joinWorld;
    @ConfigField("messages.inventory-full") @TranslateColors private String inventoryFull;

    public static <T> T get(Function<ConfigurationValue, T> function) {
        return function.apply(instance);
    }

}