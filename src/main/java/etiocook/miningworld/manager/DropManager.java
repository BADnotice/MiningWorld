package etiocook.miningworld.manager;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import etiocook.miningworld.configuration.ConfigurationValue;
import etiocook.miningworld.model.Drop;
import etiocook.miningworld.parser.DropParser;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedHashSet;
import java.util.Set;

@Singleton
public final class DropManager {

    private final Set<Drop> DROPS = new LinkedHashSet<>();

    @Inject private DropParser dropParser;

    public void init() {
        ConfigurationSection configurationSection = ConfigurationValue.get(ConfigurationValue::drops);
        configurationSection.getKeys(false).forEach($ -> register(dropParser.parse(configurationSection.getConfigurationSection($))));
    }

    public void register(Drop drop) {
        this.DROPS.add(drop);
    }

    public Drop findDropByHeight(Integer height) {
        return this.DROPS.stream()
                .filter($ -> height >= $.getHeightMin() && height <= $.getHeightMax())
                .findFirst()
                .orElse(null);
    }

    public Set<Drop> getDropsSet() {
        return ImmutableSet.copyOf(this.DROPS);
    }

}