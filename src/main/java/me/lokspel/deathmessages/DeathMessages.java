package me.lokspel.deathmessages;

import me.lokspel.deathmessages.commands.deathmessages.DeathMessagesCmd;
import me.lokspel.deathmessages.commands.deathmessages.subcommands.ToggleSubCmd;
import me.lokspel.deathmessages.commands.deathmessagestoggle.DeathMessagesToggleCmd;
import me.lokspel.deathmessages.commands.toggleconnectionmsg.ToggleConnectionMsgCmd;
import me.lokspel.deathmessages.events.OnPlayerDeathEvent;
import me.lokspel.deathmessages.events.OnPlayerJoinEvent;
import me.lokspel.deathmessages.events.OnPlayerQuitEvent;
import me.lokspel.deathmessages.utils.PluginPermission;
import me.lokspel.deathmessages.utils.UserData;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathMessages extends JavaPlugin {

    private static DeathMessages instance;
    private UserData userData;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        new Metrics(this, 33462);

        userData = new UserData(this);

        PluginPermission.registerAll();

        var toggleSubCmd = new ToggleSubCmd();
        new DeathMessagesCmd(toggleSubCmd).enable();
        new DeathMessagesToggleCmd(toggleSubCmd).enable();
        new ToggleConnectionMsgCmd().enable();

        getServer().getPluginManager().registerEvents(new OnPlayerDeathEvent(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(this), this);
    }

    @Override
    public void onDisable() {
        PluginPermission.unregisterAll();
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
}
