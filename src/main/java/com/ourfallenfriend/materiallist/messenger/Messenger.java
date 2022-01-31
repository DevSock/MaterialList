package com.ourfallenfriend.materiallist.messenger;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Messenger {
    public static void dispatch(@NotNull Player receiver, @NotNull MessageType messageType, String message) {
        Component prefix = messageType.getPrefixComplete();
        TextColor messageColor = messageType.getMessageColor();
        Component convertedMessage = Component.text(message).color(messageColor);
        Component completedMessage = prefix.append(Component.text(" ")).append(convertedMessage);

        receiver.sendMessage(completedMessage);
    }
}
