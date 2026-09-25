package me.lokspel.deathmessages.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {

    public static String weaponName(ItemStack weapon) {
        ItemMeta itemMeta = weapon.getItemMeta();
        if (itemMeta != null && itemMeta.hasDisplayName()) {
            Component displayName = itemMeta.displayName();
            if (displayName instanceof TextComponent textComponent) {
                return textComponent.content();
            }
        }
        return MessageUtils.toReadableName(weapon.getType().name());
    }
}