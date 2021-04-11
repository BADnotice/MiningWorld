package etiocook.miningworld.manager;

import com.google.inject.Singleton;
import etiocook.miningworld.configuration.ConfigurationValue;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
@Getter
public final class WorldManager {

    private String name;
    private int radiusTeleport;

    private final Set<String> effects = new LinkedHashSet<>();
    private final Set<Material> materials = new LinkedHashSet<>();

    public void init() {
        ConfigurationSection section = ConfigurationValue.get(ConfigurationValue::world);
        name = section.getString("name");
        radiusTeleport = section.getInt("radius-teleport");

        this.effects.addAll(section.getStringList("effects"));
        section.getStringList("materials-blocked").forEach($ -> materials.add(Material.getMaterial($)));

        generateWorld(section.getLong("seed"));
    }

    public void generateWorld(long seed) {
        if (Bukkit.getWorld(name) != null) {
            Bukkit.unloadWorld(name, false);
            deleteDir(Bukkit.getWorld(name).getWorldFolder());
        }

        createWorld(seed);
    }

    public void createWorld(long seed) {
        WorldCreator creator = new WorldCreator(name);
        creator.seed(seed);
        creator.generatorSettings("{\"coordinateScale\":684.412,\"heightScale\":684.412,\"lowerLimitScale\":512.0,\"upperLimitScale\":512.0,\"depthNoiseScaleX\":200.0,\"depthNoiseScaleZ\":200.0,\"depthNoiseScaleExponent\":0.5,\"mainNoiseScaleX\":80.0,\"mainNoiseScaleY\":160.0,\"mainNoiseScaleZ\":80.0,\"baseSize\":8.5,\"stretchY\":12.0,\"biomeDepthWeight\":0.2,\"biomeDepthOffset\":0.0,\"biomeScaleWeight\":1.0,\"biomeScaleOffset\":0.0,\"seaLevel\":63,\"useCaves\":false,\"useDungeons\":false,\"dungeonChance\":8,\"useStrongholds\":false,\"useVillages\":false,\"useMineShafts\":false,\"useTemples\":false,\"useMonuments\":false,\"useRavines\":false,\"useWaterLakes\":false,\"waterLakeChance\":4,\"useLavaLakes\":false,\"lavaLakeChance\":80,\"useLavaOceans\":false,\"fixedBiome\":-1,\"biomeSize\":4,\"riverSize\":4,\"dirtSize\":33,\"dirtCount\":10,\"dirtMinHeight\":0,\"dirtMaxHeight\":256,\"gravelSize\":33,\"gravelCount\":8,\"gravelMinHeight\":0,\"gravelMaxHeight\":256,\"graniteSize\":33,\"graniteCount\":10,\"graniteMinHeight\":0,\"graniteMaxHeight\":80,\"dioriteSize\":33,\"dioriteCount\":10,\"dioriteMinHeight\":0,\"dioriteMaxHeight\":80,\"andesiteSize\":33,\"andesiteCount\":10,\"andesiteMinHeight\":0,\"andesiteMaxHeight\":80,\"coalSize\":1,\"coalCount\":0,\"coalMinHeight\":0,\"coalMaxHeight\":0,\"ironSize\":1,\"ironCount\":0,\"ironMinHeight\":0,\"ironMaxHeight\":0,\"goldSize\":1,\"goldCount\":0,\"goldMinHeight\":0,\"goldMaxHeight\":0,\"redstoneSize\":1,\"redstoneCount\":0,\"redstoneMinHeight\":0,\"redstoneMaxHeight\":0,\"diamondSize\":1,\"diamondCount\":0,\"diamondMinHeight\":0,\"diamondMaxHeight\":0,\"lapisSize\":1,\"lapisCount\":0,\"lapisCenterHeight\":0,\"lapisSpread\":0,\"emeraldSize\":1,\"emeraldCount\":0,\"emeraldCenterHeight\":0,\"emeraldSpread\":0}");
        creator.createWorld();
    }

    public void deleteDir(File dir) {
        if (!dir.exists())
            return;
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
                deleteDir(new File(dir, children[i]));
        }
        dir.delete();
    }

    public Location generateLocation() {
        World world = Bukkit.getWorld(name);
        int x = ThreadLocalRandom.current().nextInt(0, radiusTeleport);
        int z = ThreadLocalRandom.current().nextInt(0, radiusTeleport);
        int y = world.getHighestBlockYAt(x, z) + 1;

        return new Location(world, x, y, z);
    }

}
