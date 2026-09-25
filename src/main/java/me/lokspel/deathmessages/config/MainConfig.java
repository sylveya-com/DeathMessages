package me.lokspel.deathmessages.config;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.section.ColorsSection;
import me.lokspel.deathmessages.config.section.SettingsSection;

public class MainConfig {

    private final DeathMessages plugin;
    private final ColorsSection colors;
    private final SettingsSection settings;
    private MessagesConfig messages;

    public MainConfig(DeathMessages plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        this.colors = new ColorsSection(plugin);
        this.settings = new SettingsSection(plugin);
        this.messages = new MessagesConfig(plugin.getConfig());
    }

    public void load() {
        plugin.reloadConfig();
        messages = new MessagesConfig(plugin.getConfig());
    }

    public ColorsSection colors() {
        return colors;
    }

    public SettingsSection settings() {
        return settings;
    }

    public MessagesConfig messages() {
        return messages;
    }
}
