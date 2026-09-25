package me.lokspel.deathmessages.events;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.config.MainConfig;
import me.lokspel.deathmessages.config.UserDataManager;
import me.lokspel.deathmessages.utils.ItemUtils;
import me.lokspel.deathmessages.utils.MessageUtils;
import me.lokspel.deathmessages.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class OnPlayerDeathEvent implements Listener {

    private final MainConfig config;
    private final UserDataManager userData;

    public OnPlayerDeathEvent() {
        this.config = DeathMessages.getInstance().getMainConfig();
        this.userData = DeathMessages.getInstance().getUserDataManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handle(PlayerDeathEvent event) {
        if (!config.settings().deathMessagesEnabled()) {
            return;
        }

        Player player = event.getEntity();

        if (PlayerUtils.hasNotPlayedLongEnough(player, config.settings().minPlayTimeMinutes())) {
            return;
        }

        Player killer = player.getKiller();
        int cooldownSeconds = config.settings().deathMessageCooldownSeconds();
        Component deathMessage = event.deathMessage();

        if (deathMessage == null || PlayerUtils.isCooldownActive(player, cooldownSeconds)) {
            event.deathMessage(null);
            return;
        }

        String playerName = player.getName();
        String killerName = (killer != null) ? killer.getName() : null;

        Component colored = colorDeathMessage(
                deathMessage,
                config.colors().deathMain(),
                playerName,
                config.colors().deathPlayer(),
                killerName,
                config.colors().deathKiller(),
                config.colors().deathWeapon(),
                killer,
                config.settings().weaponHoverEnabled()
        );

        for (Player online : player.getServer().getOnlinePlayers()) {
            if (userData.isMessagesEnabled(online.getUniqueId())
                    && !userData.isBlacklistedFor(online.getUniqueId(), player.getUniqueId())) {
                online.sendMessage(colored);
            }
        }

        Bukkit.getConsoleSender().sendMessage(colored);
        event.deathMessage(null);
        PlayerUtils.addDeathTime(player, PlayerUtils.getCurrentTimeSeconds());
    }

    private Component colorDeathMessage(
            Component message,
            TextColor mainColor,
            String playerName,
            TextColor playerColor,
            String killerName,
            TextColor killerColor,
            TextColor weaponColor,
            Player killer,
            boolean weaponHoverEnabled
    ) {
        Component colored = MessageUtils.applyMainColor(message, mainColor);
        colored = MessageUtils.replaceColoredName(colored, playerName, playerColor);
        colored = MessageUtils.replaceColoredName(colored, killerName, killerColor);

        if (killer != null) {
            ItemStack weapon = killer.getInventory().getItemInMainHand();
            if (weapon.getType() != Material.AIR) {
                String weaponName = ItemUtils.weaponName(weapon);
                colored = weaponHoverEnabled
                        ? MessageUtils.replaceColoredItemName(colored, weaponName, weaponColor, weapon)
                        : MessageUtils.replaceColoredName(colored, weaponName, weaponColor);
            }
        }

        return colored;
    }
}
