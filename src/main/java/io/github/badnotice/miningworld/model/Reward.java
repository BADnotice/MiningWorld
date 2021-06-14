package io.github.badnotice.miningworld.model;

import lombok.Builder;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Builder
@Data
public final class Reward {

    private final double percentage;

    private final RewardDropHeight dropHeight;
    private final ItemStack itemStack;

}