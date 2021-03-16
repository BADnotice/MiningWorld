package badnotice.miningworld.parsers;

import badnotice.miningworld.api.model.builder.ItemBuilder;
import com.google.inject.Singleton;
import badnotice.miningworld.api.model.position.HeightPosition;
import badnotice.miningworld.api.model.Drop;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

@Singleton
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

    private HeightPosition heightPosition(ConfigurationSection section) {
        return HeightPosition.builder()
                .max(section.getInt("max"))
                .min(section.getInt("min"))
                .build();
    }

}