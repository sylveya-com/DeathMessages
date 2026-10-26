package me.lokspel.deathmessages.commands.deathmessages;

import com.google.common.collect.ImmutableList;
import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.commands.SubCommand;
import me.lokspel.deathmessages.commands.deathmessages.subcommands.BlacklistSubCmd;
import me.lokspel.deathmessages.commands.deathmessages.subcommands.ReloadSubCmd;
import me.lokspel.deathmessages.commands.deathmessages.subcommands.ToggleSubCmd;
import me.lokspel.deathmessages.utils.Enableable;
import me.lokspel.deathmessages.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DeathMessagesCmd extends Command implements Enableable {

    private final @NotNull List<SubCommand> subCommands;
    private final @NotNull List<String> tabCompletes;

    public DeathMessagesCmd() {
        this(new ToggleSubCmd());
    }

    public DeathMessagesCmd(@NotNull ToggleSubCmd toggleSubCmd) {
        super(
                "deathmessages",
                "Main DeathMessages command",
                "/deathmessages [ reload, toggle, blacklist ]",
                ImmutableList.of("dm")
        );
        subCommands = ImmutableList.of(
                new ReloadSubCmd(),
                toggleSubCmd,
                new BlacklistSubCmd()
        );
        tabCompletes = subCommands.stream().map(SubCommand::label).collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
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
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws CommandException, IllegalArgumentException {
        if (args.length == 1) {
            String current = args[0].toLowerCase();
            return tabCompletes.stream()
                    .filter(s -> s.toLowerCase().startsWith(current))
                    .collect(Collectors.toList());
        }

        if (args.length >= 2) {
            for (final SubCommand subCommand : subCommands) {
                if (args[0].equalsIgnoreCase(subCommand.label())) {
                    return subCommand.tabComplete(sender, alias, args);
                }
            }
        }

        return Collections.emptyList();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length >= 1) {
            for (final SubCommand subCommand : subCommands) {
                if (args[0].equalsIgnoreCase(subCommand.label())) {
                    return subCommand.execute(sender, commandLabel, args);
                }
            }
        }

        return commandOverview(sender);
    }

    private boolean commandOverview(CommandSender sender) {
        for (String line : DeathMessages.getInstance().getConfig().getStringList("Commands.DeathMessages.Help")) {
            MessageUtil.send(sender, line);
        }
        return true;
    }
}
