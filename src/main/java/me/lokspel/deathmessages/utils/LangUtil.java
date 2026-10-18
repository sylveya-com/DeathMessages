package me.lokspel.deathmessages.utils;

import me.lokspel.deathmessages.DeathMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class LangUtil {

    private static final String PATH = "Commands.DeathMessages.";
    private static final String PREFIX_KEY = PATH + "Prefix";

    private final DeathMessages plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public LangUtil(DeathMessages plugin) {
        this.plugin = plugin;
    }

    public Component get(String key) {
        String raw = plugin.getConfig().getString(PATH + key, "");
        return miniMessage.deserialize(raw);
    }

    public Component get(String key, String placeholder, String value) {
        String raw = plugin.getConfig().getString(PATH + key, "");
        String formatted = raw
                .replace("<" + placeholder + ">", value)
                .replace("%" + placeholder + "%", value);
        return miniMessage.deserialize(formatted);
    }

    public Component prefixed(String key) {
        String prefix = plugin.getConfig().getString(PREFIX_KEY, "");
        String raw = plugin.getConfig().getString(PATH + key, "");
        String formatted = raw
                .replace("<prefix>", prefix)
                .replace("%prefix%", prefix);
        return miniMessage.deserialize(formatted);
    }

    public Component prefixed(String key, String placeholder, String value) {
        String prefix = plugin.getConfig().getString(PREFIX_KEY, "");
        String raw = plugin.getConfig().getString(PATH + key, "");
        String formatted = raw
                .replace("<prefix>", prefix)
                .replace("%prefix%", prefix)
                .replace("<" + placeholder + ">", value)
                .replace("%" + placeholder + "%", value);
        return miniMessage.deserialize(formatted);
    }
}
