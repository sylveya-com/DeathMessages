package me.lokspel.deathmessages.events;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.utils.ComponentUtil;
import me.lokspel.deathmessages.utils.PlayerUtils;
import me.lokspel.deathmessages.utils.UserData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoinEvent implements Listener {

    private final DeathMessages plugin;
    private final UserData userData;

    public OnPlayerJoinEvent(DeathMessages plugin) {
        this.plugin = plugin;
        this.userData = plugin.getUserData();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handle(PlayerJoinEvent event) {
        if (!plugin.getConfig().getBoolean("Settings.Join-Messages", true)) {
            return;
        }

        Player player = event.getPlayer();

        if (PlayerUtils.hasNotPlayedLongEnough(player, plugin.getConfig().getInt("Settings.Min-Playtime-Minutes", 0))) {
            event.joinMessage(null);
            return;
        }

        Component message = event.joinMessage();
        if (message != null) {
            Component colored = ComponentUtil.applyMainColor(message, ComponentUtil.parseColor(plugin.getConfig().getString("Colors.Join.Main"), NamedTextColor.GRAY));
            colored = ComponentUtil.replaceColoredName(colored, player.getName(), ComponentUtil.parseColor(plugin.getConfig().getString("Colors.Join.Player"), NamedTextColor.GRAY));

            for (Player online : player.getServer().getOnlinePlayers()) {
                if (userData.isConnectionMessagesEnabled(online.getUniqueId())) {
                    online.sendMessage(colored);
                }
            }

            Bukkit.getConsoleSender().sendMessage(colored);
        }

        event.joinMessage(null);
    }
}
