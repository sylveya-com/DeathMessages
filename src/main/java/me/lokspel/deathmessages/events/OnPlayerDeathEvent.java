package me.lokspel.deathmessages.events;

import me.lokspel.deathmessages.DeathMessages;
import me.lokspel.deathmessages.utils.ItemUtils;
import me.lokspel.deathmessages.utils.MessageUtils;
import me.lokspel.deathmessages.utils.PlayerUtils;
import me.lokspel.deathmessages.utils.UserData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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

    private final DeathMessages plugin;
    private final UserData userData;

    public OnPlayerDeathEvent(DeathMessages plugin) {
        this.plugin = plugin;
        this.userData = plugin.getUserData();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handle(PlayerDeathEvent event) {
        if (!plugin.getConfig().getBoolean("Settings.Death-Messages", true)) {
            return;
        }

        Player player = event.getEntity();

        if (PlayerUtils.hasNotPlayedLongEnough(player, plugin.getConfig().getInt("Settings.Min-Playtime-Minutes", 0))) {
            return;
        }

        Player killer = player.getKiller();
        int cooldownSeconds = plugin.getConfig().getInt("Settings.Death-Message-Cooldown-Seconds", 10);
        Component deathMessage = event.deathMessage();

        if (deathMessage == null || PlayerUtils.isCooldownActive(player, cooldownSeconds)) {
            event.deathMessage(null);
            return;
        }

        String playerName = player.getName();
        String killerName = (killer != null) ? killer.getName() : null;

        Component colored = colorDeathMessage(
                deathMessage,
                MessageUtils.parseColor(plugin.getConfig().getString("Colors.Death.Main"), NamedTextColor.DARK_RED),
                playerName,
                MessageUtils.parseColor(plugin.getConfig().getString("Colors.Death.Player"), NamedTextColor.DARK_AQUA),
                killerName,
                MessageUtils.parseColor(plugin.getConfig().getString("Colors.Death.Killer"), NamedTextColor.DARK_AQUA),
                MessageUtils.parseColor(plugin.getConfig().getString("Colors.Death.Weapon"), NamedTextColor.GOLD),
                killer,
                plugin.getConfig().getBoolean("Settings.Weapon-Hover", true)
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
