package me.lokspel.deathmessages.commands.toggleconnectionmsg;

import com.google.common.collect.ImmutableList;
import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.utils.Enableable;
import me.lokspel.deathmessages.utils.MessageUtil;
import me.lokspel.deathmessages.utils.PluginPermission;
import me.lokspel.deathmessages.utils.UserData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ToggleConnectionMsgCmd extends Command implements Enableable {

    public ToggleConnectionMsgCmd() {
        super(
                "toggleconnectionmsg",
                "Toggle join/quit messages on or off",
                "/toggleconnectionmsg",
                ImmutableList.of("togglejoins")
        );
    }

    @Override
    public void enable() {
        PluginCommand pluginCommand = DeathMessages.getInstance().getCommand(getName());
        if (pluginCommand != null) {
            pluginCommand.setExecutor((sender, command, label, args) -> execute(sender, label, args));
            pluginCommand.setTabCompleter((sender, command, alias, args) -> tabComplete(sender, alias, args));
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!sender.hasPermission(PluginPermission.TOGGLE_CONNECTION_MSG_CMD.get())) {
            MessageUtil.send(sender, "No-Permission");
            return true;
        }

        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "Player-Only-Command");
            return true;
        }

        UserData userData = DeathMessages.getInstance().getUserData();
        boolean current = userData.isConnectionMessagesEnabled(player.getUniqueId());
        userData.setConnectionMessagesEnabled(player.getUniqueId(), !current);

        if (current) {
            MessageUtil.send(player, "<gold>Connection messages hidden.");
        } else {
            MessageUtil.send(player, "<gold>Connection messages unhidden.");
        }
        return true;
    }
}
