package me.lokspel.deathmessages.config.section;

import me.lokspel.deathmessages.DeathMessages;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;

public class ColorsSection {

    private static final String PATH = "Colors.";

    private final DeathMessages plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public ColorsSection(DeathMessages plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration config() {
        return plugin.getConfig();
    }

    private TextColor color(String key, String defaultRaw) {
        String raw = config().getString(PATH + key, defaultRaw);
        return miniMessage.deserialize(raw + "x").color();
    }

    public TextColor deathMain() {
        return color("Death.Main", "<red>");
    }

    public TextColor deathPlayer() {
        return color("Death.Player", "<green>");
    }

    public TextColor deathKiller() {
        return color("Death.Killer", "<red>");
    }

    public TextColor deathWeapon() {
        return color("Death.Weapon", "<yellow>");
    }

    public TextColor joinMain() {
        return color("Join.Main", "<dark_green>");
    }

    public TextColor joinPlayer() {
        return color("Join.Player", "<green>");
    }

    public TextColor quitMain() {
        return color("Quit.Main", "<dark_red>");
    }

    public TextColor quitPlayer() {
        return color("Quit.Player", "<light_purple>");
    }
}
