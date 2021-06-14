package io.github.badnotice.miningworld.api.event.impl;

import io.github.badnotice.miningworld.api.event.EventWrapper;
import io.github.badnotice.miningworld.model.Reward;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * @author BADnotice
 * @description Chamado quando o jogador recebe uma reward
 */

@Getter
@Setter
public final class MiningRewardSpawnEvent extends EventWrapper implements Cancellable {

    private final Reward reward;

    private final Integer experience;
    private final Integer amount;

    private boolean cancelled;

    public MiningRewardSpawnEvent(Player player, Reward reward, Integer experience, Integer amount) {
        super(player);
        this.reward = reward;
        this.experience = experience;
        this.amount = amount;
    }
}
