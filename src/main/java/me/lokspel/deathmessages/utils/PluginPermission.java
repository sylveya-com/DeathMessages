package me.lokspel.deathmessages.utils;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public enum PluginPermission {

    RELOAD_CMD(new Permission("deathmessages.command.reload", PermissionDefault.OP)),
    TOGGLE_CMD(new Permission("deathmessages.command.toggle", PermissionDefault.OP)),
    BLACKLIST_CMD(new Permission("deathmessages.command.blacklist", PermissionDefault.OP)),
    TOGGLE_CONNECTION_MSG_CMD(new Permission("deathmessages.command.toggleconnectionmsg", PermissionDefault.OP));

    private final Permission permission;

    PluginPermission(Permission permission) {
        this.permission = permission;
    }

    public Permission get() {
        return permission;
    }

    public static void registerAll() {
        for (PluginPermission pluginPermission : PluginPermission.values()) {
            try {
                Bukkit.getPluginManager().addPermission(pluginPermission.get());
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    public static void unregisterAll() {
        for (PluginPermission pluginPermission : PluginPermission.values()) {
            try {
                Bukkit.getPluginManager().removePermission(pluginPermission.get());
            } catch (IllegalArgumentException ignored) {
            }
        }
    }
}
