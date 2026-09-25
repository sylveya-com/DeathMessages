package me.lokspel.deathmessages.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MessagesConfig {

    private static final String PATH = "Commands.DeathMessages.";

    private final FileConfiguration config;

    public MessagesConfig(FileConfiguration config) {
        this.config = config;
    }

    public String prefix() {
        return config.getString(PATH + "Prefix", "&7[DeathMessages]&r ");
    }

    public String playerOnly() {
        return config.getString(PATH + "Player-Only-Command", "&cOnly players can run this command.");
    }

    public String noPermission() {
        return config.getString(PATH + "No-Permission", "%prefix%&cYou do not have permission for this command.");
    }

    public List<String> help() {
        return config.getStringList(PATH + "Help");
    }

    public String reloaded() {
        return config.getString(PATH + "Sub-Commands.Reload.Reloaded", "%prefix%&aReloaded all plugin configs.");
    }

    public String toggleOn() {
        return config.getString(PATH + "Sub-Commands.Toggle.Toggle-On", "&6Death messages unhidden.");
    }

    public String toggleOff() {
        return config.getString(PATH + "Sub-Commands.Toggle.Toggle-Off", "&6Death messages hidden.");
    }

    public String blacklistHelp() {
        return config.getString(PATH + "Sub-Commands.Blacklist.Help", "&cUsage: /dm blacklist <username>");
    }

    public String blacklistUsernameNonexistent() {
        return config.getString(PATH + "Sub-Commands.Blacklist.Username-None-Existent", "%prefix%&cCould not find the player with the username &e%player%");
    }

    public String blacklistAdd() {
        return config.getString(PATH + "Sub-Commands.Blacklist.Blacklist-Add", "&6Now blacklisting &3%player%");
    }

    public String blacklistRemove() {
        return config.getString(PATH + "Sub-Commands.Blacklist.Blacklist-Remove", "&6No longer blacklisting &3%player%");
    }
}
