package io.github.badnotice.miningworld.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Function;

@Getter
@Accessors(fluent = true)
@ConfigFile("rewards.yml")
@TranslateColors
public final class RewardValue implements ConfigurationInjectable {

    public static RewardValue instance = new RewardValue();

    @ConfigField("rewards")
    private ConfigurationSection rewards;

    public static <T> T get(Function<RewardValue, T> function) {
        return function.apply(instance);
    }

}