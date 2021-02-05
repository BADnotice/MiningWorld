package etiocook.miningworld.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import etiocook.miningworld.configuration.ConfigurationValue;
import etiocook.miningworld.model.Drop;
import etiocook.miningworld.parser.DropParser;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Singleton
public final class DropManager {

    private final List<Drop> drops = new LinkedList<>();

    @Inject private DropParser dropParser;

    public void init() {
        ConfigurationSection configurationSection = ConfigurationValue.get(ConfigurationValue::drops);
        configurationSection.getKeys(false).forEach($ -> register(dropParser.parse(configurationSection.getConfigurationSection($))));
    }

    public void register(Drop drop) {
        this.drops.add(drop);
    }

    public Drop getDropRandom() {
        return this.drops.get(new Random().nextInt(this.drops.size()));
    }

}