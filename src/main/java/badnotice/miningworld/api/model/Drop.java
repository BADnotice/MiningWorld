package badnotice.miningworld.api.model;

import badnotice.miningworld.api.model.position.HeightPosition;
import lombok.Builder;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Builder
@Data
public final class Drop {

    private final double percentage;

    private final HeightPosition position;
    private final ItemStack itemStack;

}