package com.ourfallenfriend.materiallist.messenger;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public enum MessageType {
    SUCCESS(Component.text("Success", TextColor.color(156, 204, 101))),
    FAILURE(Component.text("Failure", TextColor.color(239, 83, 80))),
    INFO(Component.text("Info", TextColor.color(0, 150, 150))),
    WARN(Component.text("Warn", TextColor.color(255, 167, 38))),
    TIP(Component.text("Tip", TextColor.color(0, 200, 150)));

    private final Component MATERIAL_LIST_PREFIX = Component.text("ML", TextColor.color(120, 144, 156));
    private final Component MATERIAL_LIST_PREFIX_ENDING_BRACKET = Component.text("]", TextColor.color(189, 189, 189));
    private final Component MATERIAL_LIST_PREFIX_STARTING_BRACKET = Component.text("[", TextColor.color(189, 189, 189));

    private final TextColor messageColor;
    private final Component prefixAddition;

    MessageType(Component prefixAddition) {
        this.messageColor = prefixAddition.color();
        this.prefixAddition = prefixAddition;
    }

    public Component getPrefixComplete() {
        return MATERIAL_LIST_PREFIX_STARTING_BRACKET
                .append(MATERIAL_LIST_PREFIX)
                .append(Component.text(" "))
                .append(prefixAddition)
                .append(MATERIAL_LIST_PREFIX_ENDING_BRACKET)
                .decorate(TextDecoration.BOLD);
    }

    public TextColor getMessageColor() {
        return this.messageColor;
    }
}
