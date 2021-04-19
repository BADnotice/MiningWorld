package com.github.badnotice.miningworld.model;

import lombok.Builder;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Builder
@Data
public final class Drop {

    private final double percentage;

    private final DropHeight position;
    private final ItemStack itemStack;

}