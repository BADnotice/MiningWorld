package io.github.badnotice.miningworld.parsers;

import io.github.badnotice.miningworld.builder.ItemBuilder;
import io.github.badnotice.miningworld.model.Reward;
import io.github.badnotice.miningworld.model.RewardDropHeight;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

@Data(staticConstructor = "of")
public final class RewardParser {

    public Reward reward(ConfigurationSection section) {
        return Reward.builder()
                .percentage(section.getDouble("percentage"))
                .dropHeight(toDropHeight(section.getConfigurationSection("height")))
                .itemStack(toItemStack(section.getConfigurationSection("material")))
                .build();
    }

    private ItemStack toItemStack(ConfigurationSection section) {
        return ItemBuilder.of(Material.getMaterial(section.getInt("id")))
                .durability(section.getInt("data"))
                .getItemStack();
    }

    private RewardDropHeight toDropHeight(ConfigurationSection section) {
        return RewardDropHeight.builder()
                .max(section.getInt("max"))
                .min(section.getInt("min"))
                .build();
    }

}