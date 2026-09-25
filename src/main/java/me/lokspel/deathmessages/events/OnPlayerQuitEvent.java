package me.lokspel.deathmessages.events;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.MainConfig;
import me.lokspel.deathmessages.config.UserDataManager;
import me.lokspel.deathmessages.utils.MessageUtils;
import me.lokspel.deathmessages.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuitEvent implements Listener {

    private final MainConfig config;
    private final UserDataManager userData;

    public OnPlayerQuitEvent() {
        this.config = DeathMessages.getInstance().getMainConfig();
        this.userData = DeathMessages.getInstance().getUserDataManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handle(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerUtils.removeDeathTime(player);

        if (!config.settings().quitMessagesEnabled()) {
            return;
        }

        if (PlayerUtils.hasNotPlayedLongEnough(player, config.settings().minPlayTimeMinutes())) {
            event.quitMessage(null);
            return;
        }

        Component message = event.quitMessage();
        if (message != null) {
            Component colored = MessageUtils.applyMainColor(message, config.colors().quitMain());
            colored = MessageUtils.replaceColoredName(colored, player.getName(), config.colors().quitPlayer());

            for (Player online : player.getServer().getOnlinePlayers()) {
                if (userData.isConnectionMessagesEnabled(online.getUniqueId())) {
                    online.sendMessage(colored);
                }
            }

            Bukkit.getConsoleSender().sendMessage(colored);
        }

        event.quitMessage(null);
    }
}
