package me.lokspel.deathmessages.commands;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.utils.UserData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCommand implements SubCommand {

    private final DeathMessages plugin;
    private final UserData userData;

    public ToggleCommand(DeathMessages plugin) {
        this.plugin = plugin;
        this.userData = plugin.getUserData();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("deathmessages.command.toggle")) {
            sender.sendMessage(plugin.getLang().prefixed("No-Permission"));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getLang().get("Player-Only-Command"));
            return true;
        }

        boolean current = userData.isMessagesEnabled(player.getUniqueId());
        userData.setMessagesEnabled(player.getUniqueId(), !current);

        if (current) {
            sender.sendMessage(plugin.getLang().get("Sub-Commands.Toggle.Toggle-Off"));
        } else {
            sender.sendMessage(plugin.getLang().get("Sub-Commands.Toggle.Toggle-On"));
        }
        return true;
    }
}
