package com.github.badnotice.miningworld.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocationUtils {

    public static Location getLocationRandom(String worldName, int radius) {
        World world = Bukkit.getWorld(worldName);

        int x = ThreadLocalRandom.current().nextInt(0, radius);
        int z = ThreadLocalRandom.current().nextInt(0, radius);

        Random random = new Random();
        if (!random.nextBoolean()) {
            z = -z;
        }

        if (!random.nextBoolean()) {
            x = -x;
        }

        return new Location(world, x, world.getHighestBlockYAt(x, z) + 1, z);
    }

}
