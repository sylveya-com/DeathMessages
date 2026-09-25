package me.lokspel.deathmessages.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MessageUtils {

    public static Component colorName(String name, TextColor color) {
        return Component.text(name).color(color).hoverEvent(null);
    }

    public static Component colorItemName(String name, TextColor color, ItemStack item) {
        return Component.text(name).color(color).hoverEvent(item.asHoverEvent());
    }

    public static Component stripHoverEvents(Component component) {
        if (component instanceof TranslatableComponent tc) {
            return tc.arguments(tc.arguments().stream()
                .map(a -> a.asComponent().hoverEvent(null))
                .toList());
        }
        return component;
    }

    public static Component applyMainColor(Component component, TextColor mainColor) {
        Component colored = stripHoverEvents(component);
        if (mainColor != null) {
            colored = colored.color(mainColor);
        }
        return colored;
    }

    public static Component replaceColoredName(Component component, String name, TextColor color) {
        if (name == null || name.isEmpty()) {
            return component;
        }
        return component.replaceText(builder -> builder
                .matchLiteral(name)
                .replacement(colorName(name, color)));
    }

    public static Component replaceColoredItemName(Component component, String name, TextColor color, ItemStack item) {
        if (name == null || name.isEmpty()) {
            return component;
        }
        return component.replaceText(builder -> builder
                .matchLiteral(name)
                .replacement(colorItemName(name, color, item)));
    }

    public static String toReadableName(String value) {
        return Arrays.stream(value.toLowerCase().split("_"))
                .filter(word -> !word.isEmpty())
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }

}
