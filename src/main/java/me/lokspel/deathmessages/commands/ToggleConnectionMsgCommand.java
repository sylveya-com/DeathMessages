package me.lokspel.deathmessages.commands;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.MainConfig;
import me.lokspel.deathmessages.config.UserDataManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleConnectionMsgCommand implements CommandExecutor {

    private final MainConfig config;
    private final UserDataManager userData;
    private final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();

    public ToggleConnectionMsgCommand(DeathMessages plugin) {
        this.config = plugin.getMainConfig();
        this.userData = plugin.getUserDataManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!sender.hasPermission("deathmessages.command.toggleconnectionmsg")) {
            sender.sendMessage(legacySerializer.deserialize(
                    config.messages().noPermission().replace("%prefix%", config.messages().prefix())));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(legacySerializer.deserialize("&cOnly players can run this command."));
            return true;
        }

        boolean current = userData.isConnectionMessagesEnabled(player.getUniqueId());
        userData.setConnectionMessagesEnabled(player.getUniqueId(), !current);

        if (current) {
            player.sendMessage(legacySerializer.deserialize("&6Connection messages hidden."));
        } else {
            player.sendMessage(legacySerializer.deserialize("&6Connection messages unhidden."));
        }
        return true;
    }
}
