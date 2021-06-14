package io.github.badnotice.miningworld.builder;

import lombok.Data;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Henry FÃ¡bio
 */
@Data(staticConstructor = "of")
public final class CooldownBuilder {

    private final TimeUnit timeUnit;
    private final String message;

    private final Map<String, Instant> instantMap = new LinkedHashMap<>();

    public static CooldownBuilder of(TimeUnit timeUnit) {
        return of(timeUnit, null);
    }

    public void insertCooldown(Player player, long delay) {
        instantMap.put(player.getName(), Instant.now().plusMillis(this.timeUnit.toMillis(delay)));
    }

    public boolean inCooldown(Player player, boolean silent) {
        Instant instant = instantMap.get(player.getName());
        if (instant == null) return false;

        boolean inCooldown = instant.isAfter(Instant.now());
        if (!silent && inCooldown && this.message != null) {
            player.sendMessage(this.message
                    .replace("{0}", getTimeLeft(player))
            );
        }

        return inCooldown;
    }

    public boolean inCooldown(Player player) {
        return this.inCooldown(player, false);
    }

    public String getTimeLeft(Player player) {
        Instant instant = instantMap.get(player.getName());
        if (instant == null) return "0";

        long timer = instant.toEpochMilli() - System.currentTimeMillis();

        int days = (int) TimeUnit.MILLISECONDS.toDays(timer);
        int hour = (int) TimeUnit.MILLISECONDS.toHours(timer) % 24;
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(timer) % 60;
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(timer) % 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0L) sb.append(days).append((days == 1L) ? "d" : " d");
        if (hour > 0L)
            sb.append((days > 0L) ? ((minutes > 0L) ? ", " : " e ") : "").append(hour).append((hour == 1L) ? "h" : "h");
        if (minutes > 0L)
            sb.append((days > 0L || hour > 0L) ? ((seconds > 0L) ? ", " : " e ") : "").append(minutes).append((minutes == 1L) ? "m" : "m");
        if (seconds > 0L)
            sb.append((days > 0L || hour > 0L || minutes > 0L) ? " e " : ((sb.length() > 0) ? ", " : "")).append(seconds).append((seconds == 1L) ? "s" : "s");

        String s = sb.toString();
        return s.isEmpty() ? "0" : s;
    }

}
