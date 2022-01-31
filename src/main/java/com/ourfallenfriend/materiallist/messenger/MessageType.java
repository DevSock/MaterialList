package com.ourfallenfriend.materiallist.messenger;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public enum MessageType {
    SUCCESS(Component.text("Success"), TextColor.color(156, 204, 101)),
    FAILURE(Component.text("Failure"), TextColor.color(239, 83, 80)),
    INFO(Component.text("Info"), TextColor.color(0, 150, 150)),
    WARN(Component.text("Warn"), TextColor.color(255, 167, 38)),
    TIP(Component.text("Tip"), TextColor.color(0, 200, 150));

    private final TextColor MATERIAL_LIST_PREFIX_BRACKET_COLOR = TextColor.color(189, 189, 189);
    private final TextColor MATERIAL_LIST_PREFIX_TEXT_COLOR = TextColor.color(120, 144, 156);
    private final Component MATERIAL_LIST_PREFIX = Component.text("ML").color(MATERIAL_LIST_PREFIX_TEXT_COLOR);
    private final Component MATERIAL_LIST_PREFIX_ENDING_BRACKET = Component.text("]").color(MATERIAL_LIST_PREFIX_BRACKET_COLOR);
    private final Component MATERIAL_LIST_PREFIX_STARTING_BRACKET = Component.text("[").color(MATERIAL_LIST_PREFIX_BRACKET_COLOR);

    private final TextColor messageColor;
    private final Component prefixAddition;

    MessageType(Component prefixAddition, TextColor messageColor) {
        this.messageColor = messageColor;
        this.prefixAddition = prefixAddition;
    }

    public Component getPrefixComplete() {
        Component coloredAddition = prefixAddition.color(messageColor);

        return MATERIAL_LIST_PREFIX_STARTING_BRACKET
                .append(MATERIAL_LIST_PREFIX)
                .append(Component.text(" "))
                .append(coloredAddition)
                .append(MATERIAL_LIST_PREFIX_ENDING_BRACKET);
    }

    public TextColor getMessageColor() {
        return this.messageColor;
    }

//    public Component getPrefixAddition() {
//        return this.prefixAddition;
//    }
}
