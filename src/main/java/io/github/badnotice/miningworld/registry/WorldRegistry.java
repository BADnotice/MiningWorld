package io.github.badnotice.miningworld.registry;

import com.google.common.collect.Sets;
import io.github.badnotice.miningworld.configuration.ConfigValue;
import io.github.badnotice.miningworld.loader.Loaders;
import io.github.badnotice.miningworld.util.FileUtils;
import io.github.badnotice.miningworld.util.MiningWorldUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;

import java.util.Set;

@Getter
public final class WorldRegistry {

    private final Set<PotionEffect> effects = Sets.newLinkedHashSet();
    private final Set<Material> materials = Sets.newLinkedHashSet();

    private String name;
    private int radiusTeleport;
    private int experience;
    private boolean enabled = false;

    public void init() {
        ConfigurationSection worldSection = ConfigValue.get(ConfigValue::world);

        this.name = worldSection.getString("name");
        this.radiusTeleport = worldSection.getInt("radius-teleport");
        this.experience = worldSection.getInt("experience");

        generateWorld(worldSection.getLong("seed"));

        materials.addAll(Loaders.of().loadMaterialsBlocked(worldSection));
        effects.addAll(Loaders.of().loadPotionEffects(worldSection));
    }

    public void generateWorld(long seed) {
        if (Bukkit.getWorld(name) != null) {
            Bukkit.unloadWorld(name, false);
            FileUtils.deleteDir(Bukkit.getWorld(name).getWorldFolder());
        }

        createWorld(seed);
    }

    public void createWorld(long seed) {
        WorldCreator creator = new WorldCreator(name);
        creator.generatorSettings(MiningWorldUtils.WORLD_SETTINGS);
        creator.seed(seed);
        creator.createWorld();

        enabled = true;
    }

}
