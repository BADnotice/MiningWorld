package badnotice.miningworld.util;

import badnotice.miningworld.api.model.Drop;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MiningWorldUtils {

    public static Location generateLocation(String worldName, int radiusTeleport) {
        World world = Bukkit.getWorld(worldName);
        int x = ThreadLocalRandom.current().nextInt(0, radiusTeleport);
        int z = ThreadLocalRandom.current().nextInt(0, radiusTeleport);
        int y = world.getHighestBlockYAt(x, z) + 1;

        return new Location(world, x, y, z);
    }

    public static boolean isMiningHeight(Block block, Drop drop) {
        return block.getLocation().getY() >= drop.getPosition().getMin()
                && block.getLocation().getY() <= drop.getPosition().getMax();
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
