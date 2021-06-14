package io.github.badnotice.miningworld.api;

import io.github.badnotice.miningworld.MiningWorldPlugin;
import io.github.badnotice.miningworld.registry.WorldRegistry;
import lombok.Data;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Data(staticConstructor = "of")
public final class MiningWorldAPI {

    private final Player player;

    /**
     * Check if the player is in the mining world.
     *
     * @return Sucess or Failure
     */
    public boolean inMiningWorld() {
        World playerWorld = this.player.getWorld();
        WorldRegistry worldRegistry = MiningWorldPlugin.getInstance().getWorldRegistry();

        return playerWorld.getName().equals(worldRegistry.getName());
    }

}
