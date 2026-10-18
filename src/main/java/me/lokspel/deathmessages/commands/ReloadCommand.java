package me.lokspel.deathmessages.commands;

import me.lokspel.deathmessages.DeathMessages;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements SubCommand {

    private final DeathMessages plugin;

    public ReloadCommand(DeathMessages plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("deathmessages.command.reload")) {
            sender.sendMessage(plugin.getLang().prefixed("No-Permission"));
            return true;
        }

        plugin.reloadConfig();
        plugin.getUserData().reload();
        sender.sendMessage(plugin.getLang().prefixed("Sub-Commands.Reload.Reloaded"));
        return true;
    }
}
