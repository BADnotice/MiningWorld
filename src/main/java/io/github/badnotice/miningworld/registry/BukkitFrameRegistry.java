package io.github.badnotice.miningworld.registry;

import io.github.badnotice.miningworld.MiningWorldPlugin;
import io.github.badnotice.miningworld.commands.MineCommand;
import lombok.Data;
import me.saiintbrisson.bukkit.command.BukkitFrame;

@Data(staticConstructor = "of")
public final class BukkitFrameRegistry {

    private final MiningWorldPlugin plugin;

    public void init() {
        BukkitFrame bukkitFrame = new BukkitFrame(this.plugin);
        bukkitFrame.registerCommands(
                new MineCommand()
        );
    }

}
