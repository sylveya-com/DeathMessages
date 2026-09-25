package me.lokspel.deathmessages.commands;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.MainConfig;
import me.lokspel.deathmessages.config.UserDataManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BlacklistCommand implements SubCommand, TabCompleter {

    private final MainConfig config;
    private final UserDataManager userData;
    private final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();

    public BlacklistCommand(DeathMessages plugin) {
        this.config = plugin.getMainConfig();
        this.userData = plugin.getUserDataManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player viewer)) {
            sender.sendMessage(legacySerializer.deserialize(config.messages().playerOnly()));
            return true;
        }

        if (!sender.hasPermission("deathmessages.command.blacklist")) {
            sender.sendMessage(legacySerializer.deserialize(
                    config.messages().noPermission().replace("%prefix%", config.messages().prefix())));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(legacySerializer.deserialize(config.messages().blacklistHelp()));
            return true;
        }

        String username = args[1];
        UUID targetUUID = null;

        Player onlineTarget = Bukkit.getPlayerExact(username);
        if (onlineTarget != null) {
            targetUUID = onlineTarget.getUniqueId();
        }

        if (targetUUID == null) {
            targetUUID = userData.uuidByUsername(username);
        }

        if (targetUUID == null) {
            OfflinePlayer offlineTarget = Bukkit.getOfflinePlayerIfCached(username);
            if (offlineTarget != null && (offlineTarget.hasPlayedBefore() || offlineTarget.isOnline())) {
                targetUUID = offlineTarget.getUniqueId();
            }
        }

        if (targetUUID == null) {
            sender.sendMessage(legacySerializer.deserialize(
                    config.messages().blacklistUsernameNonexistent()
                            .replace("%prefix%", config.messages().prefix())
                            .replace("%player%", username)));
            return true;
        }

        boolean nowBlacklisted = userData.toggleBlacklist(viewer.getUniqueId(), targetUUID, username);

        if (nowBlacklisted) {
            sender.sendMessage(legacySerializer.deserialize(
                    config.messages().blacklistAdd().replace("%player%", username)));
        } else {
            sender.sendMessage(legacySerializer.deserialize(
                    config.messages().blacklistRemove().replace("%player%", username)));
        }
        return true;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 2) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
