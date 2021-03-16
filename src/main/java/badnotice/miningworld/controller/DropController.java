package badnotice.miningworld.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import badnotice.miningworld.api.model.Drop;
import badnotice.miningworld.configuration.ConfigurationValue;
import badnotice.miningworld.parsers.Parsers;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Singleton
public final class DropController {

    private final List<Drop> drops = new LinkedList<>();

    @Inject private Parsers parsers;

    public void init() {
        ConfigurationSection dropSection = ConfigurationValue.get(ConfigurationValue::drops);
        dropSection.getKeys(false).forEach(section -> register(parsers.drop(dropSection.getConfigurationSection(section))));
    }

    public void register(Drop drop) {
        this.drops.add(drop);
    }

    public Drop getDropRandom() {
        int index = new Random().nextInt(this.drops.size());
        return this.drops.get(index);
    }

}