package com.ourfallenfriend.materiallist.messenger;

import com.ourfallenfriend.materiallist.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


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

    public void sendMessage(Player receiver, MessageType messageType, String message) {
        boolean wasSuccessful = dispatch(receiver, messageType, message);
        if(!wasSuccessful) {
            logger.log(Level.WARNING, "Attempted to dispatch message to null player!");
        }
    }

    private boolean dispatch(Player receiver, MessageType messageType, String message) {
        if(Objects.isNull(receiver)) return false;
        Component completedMessage = buildCompleteMessage(messageType, message);
        receiver.sendMessage(completedMessage);
        return true;
    }

    private Component buildCompleteMessage(MessageType mT, String msg) {
        Component prefix = mT.getPrefixComplete();
        Component convertedMessage = Component.text(msg).color(mT.getMessageColor());
        return prefix.append(Component.text(" ")).append(convertedMessage);
    }
}