package me.lokspel.deathmessages.commands.deathmessagestoggle;

import com.google.common.collect.ImmutableList;
import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.commands.deathmessages.subcommands.ToggleSubCmd;
import me.lokspel.deathmessages.utils.Enableable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class DeathMessagesToggleCmd extends Command implements Enableable {

    private final @NotNull ToggleSubCmd toggleSubCmd;

    public DeathMessagesToggleCmd() {
        this(new ToggleSubCmd());
    }

    public DeathMessagesToggleCmd(@NotNull ToggleSubCmd toggleSubCmd) {
        super(
                "deathmessagestoggle",
                "Shortcut to toggle death messages",
                "/deathmessagestoggle",
                ImmutableList.of("dmtoggle", "dmt")
        );
        this.toggleSubCmd = toggleSubCmd;
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
        return toggleSubCmd.execute(sender, commandLabel, args);
    }
}
