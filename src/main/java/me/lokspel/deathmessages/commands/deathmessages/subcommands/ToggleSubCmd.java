package me.lokspel.deathmessages.commands.deathmessages.subcommands;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.commands.SubCommand;
import me.lokspel.deathmessages.utils.MessageUtil;
import me.lokspel.deathmessages.utils.PluginPermission;
import me.lokspel.deathmessages.utils.UserData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ToggleSubCmd implements SubCommand {

    @Override
    public @NotNull String label() {
        return "toggle";
    }

    @Override
    public @NotNull TextComponent syntax() {
        return Component.text("/deathmessages toggle").color(NamedTextColor.GOLD);
    }

    @Override
    public @NotNull TextComponent description() {
        return Component.text("Toggle whether you see others' death messages.").color(NamedTextColor.GRAY);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws CommandException, IllegalArgumentException {
        return Collections.emptyList();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!sender.hasPermission(PluginPermission.TOGGLE_CMD.get())) {
            MessageUtil.send(sender, "No-Permission");
            return true;
        }

        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, "Player-Only-Command");
            return true;
        }

        UserData userData = DeathMessages.getInstance().getUserData();
        boolean current = userData.isMessagesEnabled(player.getUniqueId());
        userData.setMessagesEnabled(player.getUniqueId(), !current);

        if (current) {
            MessageUtil.send(sender, "Sub-Commands.Toggle.Toggle-Off");
        } else {
            MessageUtil.send(sender, "Sub-Commands.Toggle.Toggle-On");
        }
        return true;
    }
}
