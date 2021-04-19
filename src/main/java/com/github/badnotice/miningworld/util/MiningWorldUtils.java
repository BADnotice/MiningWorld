package com.github.badnotice.miningworld.util;

import com.github.badnotice.miningworld.model.Drop;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MiningWorldUtils {

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
