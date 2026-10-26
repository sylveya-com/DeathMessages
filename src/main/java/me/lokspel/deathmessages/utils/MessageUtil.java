package me.lokspel.deathmessages.utils;

import me.lokspel.deathmessages.DeathMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class MessageUtil {

    private static final String PATH = "Commands.DeathMessages.";
    private static final String PREFIX_KEY = PATH + "Prefix";
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private MessageUtil() {}

    public static void send(@NotNull CommandSender sender, @NotNull String message) {
        sender.sendMessage(parse(message));
    }

    public static void send(@NotNull CommandSender sender, @NotNull String message, @NotNull String key, @NotNull Object value) {
        String resolved = resolve(message);
        send(sender, replace(resolved, key, String.valueOf(value)));
    }

    public static @NotNull Component parse(@NotNull String message) {
        String resolved = resolve(message);
        String prefix = getPrefix();
        String text = resolved
                .replace("<prefix>", prefix)
                .replace("%prefix%", prefix)
                .replace("{prefix}", prefix);
        return MINI_MESSAGE.deserialize(text);
    }

    private static @NotNull String resolve(@NotNull String message) {
        if (DeathMessages.getInstance().getConfig().contains(PATH + message)) {
            return DeathMessages.getInstance().getConfig().getString(PATH + message, message);
        }
        return message;
    }

    public static @NotNull String getPrefix() {
        return DeathMessages.getInstance().getConfig().getString(PREFIX_KEY, "<gray>[DeathMessages]<reset> ");
    }

    private static @NotNull String replace(@NotNull String message, @NotNull String key, @NotNull String value) {
        return message
                .replace("{" + key + "}", value)
                .replace("%" + key + "%", value)
                .replace("<" + key + ">", value);
    }
}
