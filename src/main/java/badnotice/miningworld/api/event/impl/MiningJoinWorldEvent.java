package badnotice.miningworld.api.event.impl;

import badnotice.miningworld.api.event.CustomEvent;
import badnotice.miningworld.api.model.Drop;
import badnotice.miningworld.controller.WorldController;
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
    private final WorldController controller;

    private boolean cancelled;

}
