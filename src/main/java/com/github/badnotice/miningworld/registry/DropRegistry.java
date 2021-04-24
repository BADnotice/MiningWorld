package com.github.badnotice.miningworld.registry;

import com.github.badnotice.miningworld.configuration.ConfigValue;
import com.github.badnotice.miningworld.model.Drop;
import com.github.badnotice.miningworld.parsers.Parsers;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class DropRegistry {

    private final List<Drop> drops = new LinkedList<>();

     private final Parsers parsers = new Parsers();

    public void init() {
        ConfigurationSection dropSection = ConfigValue.get(ConfigValue::drops);
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