package com.github.badnotice.miningworld.event.impl;

import com.github.badnotice.miningworld.event.CustomEvent;
import com.github.badnotice.miningworld.model.Drop;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * @author BADnotice
 * @description Chamado quando o jogador recebe o drop
 */

@EqualsAndHashCode(callSuper = true)
@Data
public final class MiningDropSpawnEvent extends CustomEvent implements Cancellable {

    private final Player player;
    private final Drop drop;

    private final Integer experience;
    private final Integer amount;

    private boolean cancelled;

}
