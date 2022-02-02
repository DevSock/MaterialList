package com.ourfallenfriend.materiallist.messenger;

import com.ourfallenfriend.materiallist.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class Messenger {
    public static final Messenger instance;
    private final Logger logger;

    private Messenger() {
        logger = Main.getInstance().getLogger();
        Main.getInstance().getLogger().log(Level.INFO, "Messenger Instantiated.");
    }

    static {
        instance = new Messenger();
    }

    public static Messenger getInstance() {
        return instance;
    }

    public void sendMessage(Player receiver, MessageType messageType, Boolean shouldLog, Component message){
        boolean wasSuccessful = dispatch(receiver, messageType, message);
        if(shouldLog) {
            logger.log(Level.INFO, buildCompleteMessage(messageType, message).toString());
            if(!wasSuccessful) {
                logger.log(Level.WARNING, "Recipient of logged message was null, and message was not delivered.");
            }
        }
    }

    public void sendMessage(Player receiver, MessageType messageType, Component message) {
        sendMessage(receiver, messageType, false, message);
    }

    public void sendMessage(Player receiver, MessageType messageType, Boolean shouldLog, String message) {
        sendMessage(receiver, messageType, shouldLog, Component.text(message));
    }

    public void sendMessage(Player receiver, MessageType messageType, String message) {
        sendMessage(receiver, messageType, false, message);
    }

    public void sendMessage(Player receiver, Boolean shouldLog, BakedMessage bakedMessage) {
        sendMessage(receiver, bakedMessage.getMessageType(), shouldLog, bakedMessage.getMessage());
    }

    public void sendMessage(Player receiver, BakedMessage bakedMessage) {
        sendMessage(receiver, false, bakedMessage);
    }

    public void sendMessage(Player receiver, boolean shouldLog, BakedMessage... bakedMessage) {
        Stream<BakedMessage> stream = Stream.of(bakedMessage);
        stream.forEach(message -> sendMessage(receiver, shouldLog, message));
    }

    public void sendMessage(Player receiver, BakedMessage... bakedMessages) {
        sendMessage(receiver, false, bakedMessages);
    }

    private boolean dispatch(Player receiver, MessageType messageType, Component message) {
        if(Objects.isNull(receiver)) return false;
        Component completedMessage = buildCompleteMessage(messageType, message);
        receiver.sendMessage(completedMessage);
        return true;
    }

    private Component buildCompleteMessage(MessageType mT, Component msg) {
        Component prefix = mT.getPrefixComplete();
        Component styledMessage = msg.color(mT.getMessageColor()).decoration(TextDecoration.BOLD, false);

        return prefix.append(Component.text(" ")).append(styledMessage);
    }
}
