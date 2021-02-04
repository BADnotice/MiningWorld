package etiocook.miningworld.listener;

import com.google.inject.Inject;
import etiocook.miningworld.manager.WorldManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public final class ItemSpawnListener implements Listener {

    @Inject private WorldManager worldManager;

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        if (!event.getEntity().getWorld().getName().equals(worldManager.getName())) return;

        Material block = event.getEntity().getItemStack().getType();
        if (worldManager.getMaterials().contains(block)) event.getEntity().remove();
    }

}
