package com.github.badnotice.miningworld.event.impl;

import com.github.badnotice.miningworld.event.CustomEvent;
import com.github.badnotice.miningworld.registry.WorldRegistry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * @author BADnotice
 * @description Chamado quando o jogador entra no mundo de mineração
 */

@EqualsAndHashCode(callSuper = true)
@Data
public final class MiningJoinWorldEvent extends CustomEvent implements Cancellable {

    private final Player player;
    private final WorldRegistry controller;

    private boolean cancelled;

}
