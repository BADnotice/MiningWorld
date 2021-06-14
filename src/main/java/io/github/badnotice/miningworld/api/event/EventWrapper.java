package io.github.badnotice.miningworld.api.event;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public abstract class EventWrapper extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final Player player;

    protected EventWrapper(Player player) {
        this.player = player;
    }

    public boolean call() {
        Bukkit.getPluginManager().callEvent(this);

        if (this instanceof Cancellable) {
            return !((Cancellable) this).isCancelled();
        }

        return true;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}