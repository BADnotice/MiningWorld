package io.github.badnotice.miningworld.util;

import io.github.badnotice.miningworld.model.Reward;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MiningWorldUtils {

    public static String WORLD_SETTINGS = "{\"coordinateScale\":684.412,\"heightScale\":684.412,\"lowerLimitScale\":512.0,\"upperLimitScale\":512.0,\"depthNoiseScaleX\":200.0,\"depthNoiseScaleZ\":200.0,\"depthNoiseScaleExponent\":0.5,\"mainNoiseScaleX\":80.0,\"mainNoiseScaleY\":160.0,\"mainNoiseScaleZ\":80.0,\"baseSize\":8.5,\"stretchY\":12.0,\"biomeDepthWeight\":0.2,\"biomeDepthOffset\":0.0,\"biomeScaleWeight\":1.0,\"biomeScaleOffset\":0.0,\"seaLevel\":63,\"useCaves\":false,\"useDungeons\":false,\"dungeonChance\":8,\"useStrongholds\":false,\"useVillages\":false,\"useMineShafts\":false,\"useTemples\":false,\"useMonuments\":false,\"useRavines\":false,\"useWaterLakes\":false,\"waterLakeChance\":4,\"useLavaLakes\":false,\"lavaLakeChance\":80,\"useLavaOceans\":false,\"fixedBiome\":-1,\"biomeSize\":4,\"riverSize\":4,\"dirtSize\":33,\"dirtCount\":10,\"dirtMinHeight\":30,\"dirtMaxHeight\":256,\"gravelSize\":0,\"gravelCount\":0,\"gravelMinHeight\": 0,\"gravelMaxHeight\":0,\"graniteSize\":33,\"graniteCount\":10,\"graniteMinHeight\":0,\"graniteMaxHeight\":80,\"dioriteSize\":33,\"dioriteCount\":10,\"dioriteMinHeight\":0,\"dioriteMaxHeight\":80,\"andesiteSize\":33,\"andesiteCount\":10,\"andesiteMinHeight\":0,\"andesiteMaxHeight\":80,\"coalSize\":1,\"coalCount\":0,\"coalMinHeight\":0,\"coalMaxHeight\":0,\"ironSize\":1,\"ironCount\":0,\"ironMinHeight\":0,\"ironMaxHeight\":0,\"goldSize\":1,\"goldCount\":0,\"goldMinHeight\":0,\"goldMaxHeight\":0,\"redstoneSize\":1,\"redstoneCount\":0,\"redstoneMinHeight\":0,\"redstoneMaxHeight\":0,\"diamondSize\":1,\"diamondCount\":0,\"diamondMinHeight\":0,\"diamondMaxHeight\":0,\"lapisSize\":1,\"lapisCount\":0,\"lapisCenterHeight\":0,\"lapisSpread\":0,\"emeraldSize\":1,\"emeraldCount\":0,\"emeraldCenterHeight\":0,\"emeraldSpread\":0}";

    public static boolean isMiningHeight(Block block, Reward drop) {
        return block.getLocation().getY() >= drop.getDropHeight().getMin()
                && block.getLocation().getY() <= drop.getDropHeight().getMax();
    }

    public static Integer getRandomFortune(ItemStack itemStack) {
        int i = 1;

        if (itemStack.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
            int levelEnchant = itemStack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            i = new Random().nextInt(levelEnchant + 1);
        }

        return i;
    }

}
