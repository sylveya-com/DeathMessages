package me.lokspel.deathmessages.utils;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerUtils {

    private static final HashMap<UUID, Long> deathTimes = new HashMap<>();

    public static long getPlayTime(Player player) {
        return player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 1200;
    }

    public static boolean hasNotPlayedLongEnough(Player player, int minPlayTimeMinutes) {
        return getPlayTime(player) < minPlayTimeMinutes;
    }

    public static long getCurrentTimeSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    public static boolean isCooldownActive(Player player, int cooldownSeconds) {
        UUID playerUUID = player.getUniqueId();
        long lastDeathTime = deathTimes.getOrDefault(playerUUID, 0L);

        if (lastDeathTime == 0) {
            return false;
        }

        long currentTimeSeconds = getCurrentTimeSeconds();
        boolean isActive = (currentTimeSeconds - lastDeathTime) <= cooldownSeconds;

        if (!isActive) {
            deathTimes.remove(playerUUID);
        }

        return isActive;
    }

    public static void addDeathTime(Player player, long time) {
        deathTimes.put(player.getUniqueId(), time);
    }

    public static void removeDeathTime(Player player) {
        deathTimes.remove(player.getUniqueId());
    }
}
