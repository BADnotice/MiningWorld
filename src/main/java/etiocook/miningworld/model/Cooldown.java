package etiocook.miningworld.model;

import lombok.Data;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Henry Fábio
 */
@Data(staticConstructor = "of")
public final class Cooldown {

    private final long delay;
    private final TimeUnit timeUnit;
    private final String message;

    private final Map<String, Instant> instantMap = new LinkedHashMap<>();

    public static Cooldown of(long delay, TimeUnit timeUnit) {
        return of(delay, timeUnit, null);
    }

    public void insertCooldown(Player player) {
        instantMap.put(player.getName(), Instant.now().plusMillis(this.timeUnit.toMillis(this.delay)));
    }

    public boolean inCooldown(Player player, boolean silent) {
        Instant instant = instantMap.get(player.getName());
        if (instant == null) return false;

        boolean inCooldown = instant.isAfter(Instant.now());
        if (!silent && inCooldown && this.message != null) {
            player.sendMessage(this.message);
        }
        return inCooldown;
    }

    public boolean inCooldown(Player player) {
        return this.inCooldown(player, false);
    }

}
