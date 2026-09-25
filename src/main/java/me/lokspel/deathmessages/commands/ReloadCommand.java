package me.lokspel.deathmessages.commands;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.MainConfig;
import me.lokspel.deathmessages.config.UserDataManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements SubCommand {

    private final MainConfig config;
    private final UserDataManager userData;
    private final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();

    public ReloadCommand(DeathMessages plugin) {
        this.config = plugin.getMainConfig();
        this.userData = plugin.getUserDataManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("deathmessages.command.reload")) {
            sender.sendMessage(legacySerializer.deserialize(
                    config.messages().noPermission().replace("%prefix%", config.messages().prefix())));
            return true;
        }

        config.load();
        userData.reloadUserData();
        sender.sendMessage(legacySerializer.deserialize(
                config.messages().reloaded().replace("%prefix%", config.messages().prefix())));
        return true;
    }
}
