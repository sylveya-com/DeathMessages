package me.lokspel.deathmessages.commands;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.utils.UserData;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleConnectionMsgCommand implements CommandExecutor {

    private final DeathMessages plugin;
    private final UserData userData;

    public ToggleConnectionMsgCommand(DeathMessages plugin) {
        this.plugin = plugin;
        this.userData = plugin.getUserData();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!sender.hasPermission("deathmessages.command.toggleconnectionmsg")) {
            sender.sendMessage(plugin.getLang().prefixed("No-Permission"));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getLang().get("Player-Only-Command"));
            return true;
        }

        boolean current = userData.isConnectionMessagesEnabled(player.getUniqueId());
        userData.setConnectionMessagesEnabled(player.getUniqueId(), !current);

        if (current) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>Connection messages hidden."));
        } else {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<gold>Connection messages unhidden."));
        }
        return true;
    }
}
