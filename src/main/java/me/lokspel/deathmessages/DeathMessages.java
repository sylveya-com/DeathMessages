package me.lokspel.deathmessages;

import me.lokspel.deathmessages.commands.BlacklistCommand;
import me.lokspel.deathmessages.commands.CommandDispatcher;
import me.lokspel.deathmessages.commands.CommandDispatcher.RegisteredCommand;
import me.lokspel.deathmessages.commands.ReloadCommand;
import me.lokspel.deathmessages.commands.ToggleCommand;
import me.lokspel.deathmessages.commands.ToggleConnectionMsgCommand;
import me.lokspel.deathmessages.events.OnPlayerDeathEvent;
import me.lokspel.deathmessages.events.OnPlayerJoinEvent;
import me.lokspel.deathmessages.events.OnPlayerQuitEvent;
import me.lokspel.deathmessages.utils.LangUtil;
import me.lokspel.deathmessages.utils.UserData;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public class DeathMessages extends JavaPlugin {

    private static DeathMessages instance;
    private UserData userData;
    private LangUtil lang;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        new Metrics(this, 33462);

        lang = new LangUtil(this);
        userData = new UserData(this);

        var toggleCommand = new ToggleCommand(this);

        CommandDispatcher dispatcher = new CommandDispatcher(this, List.of(
                new RegisteredCommand("reload", new ReloadCommand(this)),
                new RegisteredCommand("toggle", toggleCommand),
                new RegisteredCommand("blacklist", new BlacklistCommand(this))
        ));
        var deathMessagesCmd = Objects.requireNonNull(getCommand("deathmessages"));
        deathMessagesCmd.setExecutor(dispatcher);
        deathMessagesCmd.setTabCompleter(dispatcher);

        Objects.requireNonNull(getCommand("toggleconnectionmsg")).setExecutor(new ToggleConnectionMsgCommand(this));

        Objects.requireNonNull(getCommand("deathmessagestoggle")).setExecutor((sender, cmd, label, args) -> toggleCommand.execute(sender, args));

        getServer().getPluginManager().registerEvents(new OnPlayerDeathEvent(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(this), this);
    }

    @Override
    public void onDisable() {
        if (userData != null) {
            userData.save();
        }
    }

    public static DeathMessages getInstance() {
        return instance;
    }

    public UserData getUserData() {
        return userData;
    }

    public LangUtil getLang() {
        return lang;
    }
}
