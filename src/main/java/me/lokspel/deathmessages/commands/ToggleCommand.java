package me.lokspel.deathmessages.commands;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.MainConfig;
import me.lokspel.deathmessages.config.UserDataManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCommand implements SubCommand {

    private final MainConfig config;
    private final UserDataManager userData;
    private final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();

    public ToggleCommand(DeathMessages plugin) {
        this.config = plugin.getMainConfig();
        this.userData = plugin.getUserDataManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("deathmessages.command.toggle")) {
            sender.sendMessage(legacySerializer.deserialize(
                    config.messages().noPermission().replace("%prefix%", config.messages().prefix())));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(legacySerializer.deserialize(config.messages().playerOnly()));
            return true;
        }

        boolean current = userData.isMessagesEnabled(player.getUniqueId());
        userData.setMessagesEnabled(player.getUniqueId(), !current);

        if (current) {
            player.sendMessage(legacySerializer.deserialize(config.messages().toggleOff()));
        } else {
            player.sendMessage(legacySerializer.deserialize(config.messages().toggleOn()));
        }
        return true;
    }
}
