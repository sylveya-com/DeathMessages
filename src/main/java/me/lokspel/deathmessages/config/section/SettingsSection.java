package me.lokspel.deathmessages.config.section;

import me.lokspel.deathmessages.DeathMessages;
import org.bukkit.configuration.file.FileConfiguration;

public class SettingsSection {

    private static final String PATH = "Settings.";

    private final DeathMessages plugin;

    public SettingsSection(DeathMessages plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration config() {
        return plugin.getConfig();
    }

    public int minPlayTimeMinutes() {
        return config().getInt(PATH + "Min-Playtime-Minutes", 0);
    }

    public int deathMessageCooldownSeconds() {
        return config().getInt(PATH + "Death-Message-Cooldown-Seconds", 3);
    }

    public boolean deathMessagesEnabled() {
        return config().getBoolean(PATH + "Death-Messages", true);
    }

    public boolean joinMessagesEnabled() {
        return config().getBoolean(PATH + "Join-Messages", true);
    }

    public boolean quitMessagesEnabled() {
        return config().getBoolean(PATH + "Quit-Messages", true);
    }

    public boolean weaponHoverEnabled() {
        return config().getBoolean(PATH + "Weapon-Hover", true);
    }
}
