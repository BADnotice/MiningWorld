package etiocook.miningworld.parser;

import com.google.inject.Singleton;
import etiocook.miningworld.builder.ItemBuilder;
import etiocook.miningworld.model.Drop;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

@Singleton
public final class DropParser {

    public Drop parse(ConfigurationSection section) {
        return Drop.builder()
                .percentage(section.getDouble("percentage"))
                .heightMax(section.getConfigurationSection("height").getInt("max"))
                .heightMin(section.getConfigurationSection("height").getInt("min"))
                .itemStack(parseItemStack(section.getConfigurationSection("material")))
                .build();
    }

    public ItemStack parseItemStack(ConfigurationSection section) {
        return ItemBuilder.of(Material.getMaterial(section.getInt("id")))
                .getItemStack();
    }

}