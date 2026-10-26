package me.lokspel.deathmessages.commands.deathmessages.subcommands;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.commands.SubCommand;
import me.lokspel.deathmessages.utils.MessageUtil;
import me.lokspel.deathmessages.utils.PluginPermission;
import me.lokspel.deathmessages.utils.UserData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BlacklistSubCmd implements SubCommand {

    @Override
    public @NotNull String label() {
        return "blacklist";
    }

    @Override
    public @NotNull TextComponent syntax() {
        return Component.text("/deathmessages blacklist <username>").color(NamedTextColor.GOLD);
    }

    @Override
    public @NotNull TextComponent description() {
        return Component.text("Blacklists a player so you no longer see their death messages.").color(NamedTextColor.GRAY);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws CommandException, IllegalArgumentException {
        if (args.length == 2) {
            String current = args[1].toLowerCase();
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(s -> s.toLowerCase().startsWith(current))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player viewer)) {
            MessageUtil.send(sender, "Player-Only-Command");
            return true;
        }

        if (!sender.hasPermission(PluginPermission.BLACKLIST_CMD.get())) {
            MessageUtil.send(sender, "No-Permission");
            return true;
        }

        if (args.length < 2) {
            MessageUtil.send(sender, "Sub-Commands.Blacklist.Help");
            return true;
        }

        UserData userData = DeathMessages.getInstance().getUserData();
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
            MessageUtil.send(sender, "Sub-Commands.Blacklist.Username-None-Existent", "player", username);
            return true;
        }

        boolean nowBlacklisted = userData.toggleBlacklist(viewer.getUniqueId(), targetUUID, username);

        if (nowBlacklisted) {
            MessageUtil.send(sender, "Sub-Commands.Blacklist.Blacklist-Add", "player", username);
        } else {
            MessageUtil.send(sender, "Sub-Commands.Blacklist.Blacklist-Remove", "player", username);
        }
        return true;
    }
}
