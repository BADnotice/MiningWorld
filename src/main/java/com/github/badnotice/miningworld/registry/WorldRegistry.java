package com.github.badnotice.miningworld.registry;

import com.github.badnotice.miningworld.configuration.ConfigValue;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.github.badnotice.miningworld.parsers.Parsers;
import com.github.badnotice.miningworld.util.FileUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        loadPotionEffects(worldSection);
        loadMaterialsBlocked(worldSection);
    }

    public void generateWorld(long seed) {
        if (Bukkit.getWorld(name) != null) {
            Bukkit.unloadWorld(name, false);
            FileUtils.deleteDir(Bukkit.getWorld(name).getWorldFolder());
        }

        createWorld(seed);
    }

    public void createWorld(long seed) {
        WorldCreator worldCreator = WorldCreator.name(name);
        worldCreator.generatorSettings("{\"coordinateScale\":684.412,\"heightScale\":684.412,\"lowerLimitScale\":512.0,\"upperLimitScale\":512.0,\"depthNoiseScaleX\":200.0,\"depthNoiseScaleZ\":200.0,\"depthNoiseScaleExponent\":0.5,\"mainNoiseScaleX\":80.0,\"mainNoiseScaleY\":160.0,\"mainNoiseScaleZ\":80.0,\"baseSize\":8.5,\"stretchY\":12.0,\"biomeDepthWeight\":0.2,\"biomeDepthOffset\":0.0,\"biomeScaleWeight\":1.0,\"biomeScaleOffset\":0.0,\"seaLevel\":63,\"useCaves\":false,\"useDungeons\":false,\"dungeonChance\":8,\"useStrongholds\":false,\"useVillages\":false,\"useMineShafts\":false,\"useTemples\":false,\"useMonuments\":false,\"useRavines\":false,\"useWaterLakes\":false,\"waterLakeChance\":4,\"useLavaLakes\":false,\"lavaLakeChance\":80,\"useLavaOceans\":false,\"fixedBiome\":-1,\"biomeSize\":4,\"riverSize\":4,\"dirtSize\":33,\"dirtCount\":10,\"dirtMinHeight\":0,\"dirtMaxHeight\":256,\"gravelSize\":0,\"gravelCount\":0,\"gravelMinHeight\":0,\"gravelMaxHeight\":0,\"graniteSize\":33,\"graniteCount\":10,\"graniteMinHeight\":0,\"graniteMaxHeight\":80,\"dioriteSize\":33,\"dioriteCount\":10,\"dioriteMinHeight\":0,\"dioriteMaxHeight\":80,\"andesiteSize\":33,\"andesiteCount\":10,\"andesiteMinHeight\":0,\"andesiteMaxHeight\":80,\"coalSize\":1,\"coalCount\":0,\"coalMinHeight\":0,\"coalMaxHeight\":0,\"ironSize\":1,\"ironCount\":0,\"ironMinHeight\":0,\"ironMaxHeight\":0,\"goldSize\":1,\"goldCount\":0,\"goldMinHeight\":0,\"goldMaxHeight\":0,\"redstoneSize\":1,\"redstoneCount\":0,\"redstoneMinHeight\":0,\"redstoneMaxHeight\":0,\"diamondSize\":1,\"diamondCount\":0,\"diamondMinHeight\":0,\"diamondMaxHeight\":0,\"lapisSize\":1,\"lapisCount\":0,\"lapisCenterHeight\":0,\"lapisSpread\":0,\"emeraldSize\":1,\"emeraldCount\":0,\"emeraldCenterHeight\":0,\"emeraldSpread\":0}");
        worldCreator.seed(seed);
        Bukkit.createWorld(worldCreator);

        enabled = true;
    }

    public void loadPotionEffects(ConfigurationSection section) {
        section.getStringList("potion-effects").forEach($ -> {
            String[] split = $.split(";");
            this.effects.add(PotionEffectType.getByName(split[0]).createEffect(99999999, Integer.parseInt(split[1])));
        });
    }

    public void loadMaterialsBlocked(ConfigurationSection section) {
        section.getStringList("materials-blocked").forEach($ -> materials.add(Material.getMaterial($)));
    }

}
