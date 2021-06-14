package io.github.badnotice.miningworld.api.event.impl;

import io.github.badnotice.miningworld.api.event.EventWrapper;
import io.github.badnotice.miningworld.registry.WorldRegistry;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * @author BADnotice
 * @description Chamado quando o jogador sai no mundo de mineração
 */

@Getter
@Setter
public final class MiningLeaveWorldEvent extends EventWrapper implements Cancellable {

    private final WorldRegistry controller;

    private boolean cancelled;

    public MiningLeaveWorldEvent(Player player, WorldRegistry controller) {
        super(player);
        this.controller = controller;
    }

}
