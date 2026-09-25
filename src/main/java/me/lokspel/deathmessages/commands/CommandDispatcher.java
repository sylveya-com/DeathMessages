package me.lokspel.deathmessages.commands;

import me.lokspel.deathmessages.config.MainConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandDispatcher implements CommandExecutor, TabCompleter {

    private final Map<String, SubCommand> subCommands = new LinkedHashMap<>();
    private final MainConfig config;
    private final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.legacyAmpersand();

    public CommandDispatcher(MainConfig config, List<RegisteredCommand> commands) {
        this.config = config;
        for (RegisteredCommand cmd : commands) {
            subCommands.put(cmd.name(), cmd.executor());
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        SubCommand sub = subCommands.get(args[0].toLowerCase());
        if (sub != null) {
            return sub.execute(sender, args);
        }

        sendHelp(sender);
        return true;
    }

    private void sendHelp(CommandSender sender) {
        for (String line : config.messages().help()) {
            sender.sendMessage(legacySerializer.deserialize(
                    line.replace("%prefix%", config.messages().prefix())));
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            return subCommands.keySet().stream()
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        SubCommand sub = subCommands.get(args[0].toLowerCase());
        if (sub instanceof TabCompleter completer) {
            return completer.onTabComplete(sender, command, alias, args);
        }

        return List.of();
    }

    public record RegisteredCommand(String name, SubCommand executor) {}
}
