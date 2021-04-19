package com.github.badnotice.miningworld.parsers;

import com.github.badnotice.miningworld.model.builder.ItemBuilder;
import com.github.badnotice.miningworld.model.DropHeight;
import com.github.badnotice.miningworld.model.Drop;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public final class Parsers {

    public Drop drop(ConfigurationSection section) {
        return Drop.builder()
                .percentage(section.getDouble("percentage"))
                .position(heightPosition(section.getConfigurationSection("height")))
                .itemStack(dropItem(section.getConfigurationSection("material")))
                .build();
    }

    private ItemStack dropItem(ConfigurationSection section) {
        return ItemBuilder.of(Material.getMaterial(section.getInt("id")))
                .durability(section.getInt("data"))
                .getItemStack();
    }

    private DropHeight heightPosition(ConfigurationSection section) {
        return DropHeight.builder()
                .max(section.getInt("max"))
                .min(section.getInt("min"))
                .build();
    }

}