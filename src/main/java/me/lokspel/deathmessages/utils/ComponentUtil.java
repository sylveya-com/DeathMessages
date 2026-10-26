package me.lokspel.deathmessages.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class ComponentUtil {

    private ComponentUtil() {}

    public static TextColor parseColor(@Nullable String input, @NotNull TextColor fallback) {
        if (input == null || input.isEmpty()) {
            return fallback;
        }
        String clean = input.replace("<", "").replace(">", "").trim();
        if (clean.startsWith("#")) {
            TextColor hex = TextColor.fromHexString(clean);
            return hex != null ? hex : fallback;
        }
        TextColor named = NamedTextColor.NAMES.value(clean.toLowerCase());
        return named != null ? named : fallback;
    }

    public static @NotNull Component colorName(@NotNull String name, @NotNull TextColor color) {
        return Component.text(name).color(color).hoverEvent(null);
    }

    public static @NotNull Component colorItemName(@NotNull String name, @NotNull TextColor color, @NotNull ItemStack item) {
        return Component.text(name).color(color).hoverEvent(item.asHoverEvent());
    }

    public static @NotNull Component stripHoverEvents(@NotNull Component component) {
        if (component instanceof TranslatableComponent tc) {
            return tc.arguments(tc.arguments().stream()
                    .map(a -> a.asComponent().hoverEvent(null))
                    .toList());
        }
        return component;
    }

    public static @NotNull Component applyMainColor(@NotNull Component component, @Nullable TextColor mainColor) {
        Component colored = stripHoverEvents(component);
        if (mainColor != null) {
            colored = colored.color(mainColor);
        }
        return colored;
    }

    public static @NotNull Component replaceColoredName(@NotNull Component component, @Nullable String name, @NotNull TextColor color) {
        if (name == null || name.isEmpty()) {
            return component;
        }
        return component.replaceText(builder -> builder
                .matchLiteral(name)
                .replacement(colorName(name, color)));
    }

    public static @NotNull Component replaceColoredItemName(@NotNull Component component, @Nullable String name, @NotNull TextColor color, @NotNull ItemStack item) {
        if (name == null || name.isEmpty()) {
            return component;
        }
        return component.replaceText(builder -> builder
                .matchLiteral(name)
                .replacement(colorItemName(name, color, item)));
    }

    public static @NotNull String toReadableName(@NotNull String value) {
        return Arrays.stream(value.toLowerCase().split("_"))
                .filter(word -> !word.isEmpty())
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }
}
