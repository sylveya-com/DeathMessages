package me.lokspel.deathmessages.commands.deathmessages.subcommands;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.commands.SubCommand;
import me.lokspel.deathmessages.utils.MessageUtil;
import me.lokspel.deathmessages.utils.PluginPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ReloadSubCmd implements SubCommand {

    @Override
    public @NotNull String label() {
        return "reload";
    }

    @Override
    public @NotNull TextComponent syntax() {
        return Component.text("/deathmessages reload").color(NamedTextColor.GOLD);
    }

    @Override
    public @NotNull TextComponent description() {
        return Component.text("Reload the plugin configuration.").color(NamedTextColor.GRAY);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws CommandException, IllegalArgumentException {
        return Collections.emptyList();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!sender.hasPermission(PluginPermission.RELOAD_CMD.get())) {
            MessageUtil.send(sender, "No-Permission");
            return true;
        }

        DeathMessages plugin = DeathMessages.getInstance();
        plugin.reloadConfig();
        plugin.getUserData().reload();
        MessageUtil.send(sender, "Sub-Commands.Reload.Reloaded");
        return true;
    }
}
