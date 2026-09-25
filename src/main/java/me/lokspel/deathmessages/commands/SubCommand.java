package me.lokspel.deathmessages.commands;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    boolean execute(CommandSender sender, String[] args);
}
