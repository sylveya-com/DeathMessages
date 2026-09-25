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
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoinEvent implements Listener {

    private final MainConfig config;
    private final UserDataManager userData;

    public OnPlayerJoinEvent() {
        this.config = DeathMessages.getInstance().getMainConfig();
        this.userData = DeathMessages.getInstance().getUserDataManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handle(PlayerJoinEvent event) {
        if (!config.settings().joinMessagesEnabled()) {
            return;
        }

        Player player = event.getPlayer();

        if (PlayerUtils.hasNotPlayedLongEnough(player, config.settings().minPlayTimeMinutes())) {
            event.joinMessage(null);
            return;
        }

        Component message = event.joinMessage();
        if (message != null) {
            Component colored = MessageUtils.applyMainColor(message, config.colors().joinMain());
            colored = MessageUtils.replaceColoredName(colored, player.getName(), config.colors().joinPlayer());

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
