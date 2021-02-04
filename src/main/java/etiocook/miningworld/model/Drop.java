package etiocook.miningworld.model;

import lombok.Builder;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Builder
@Data
public final class Drop {

    private final double percentage;

    private final int heightMax;
    private final int heightMin;

    private final ItemStack itemStack;

}