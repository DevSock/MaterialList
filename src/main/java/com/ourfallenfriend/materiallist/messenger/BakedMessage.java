package com.ourfallenfriend.materiallist.messenger;

import net.kyori.adventure.text.Component;

public enum BakedMessage {
    WRONG_ARGS("Invalid command arguments, please try again.", MessageType.FAILURE),
    SERVER_CONNECTION_FAILED("Server connection failed, please inform the system administrators.", MessageType.WARN),
    NOT_CONTRACTED("You aren't currently making a material list.", MessageType.FAILURE),
    ALREADY_CONTRACTED("You're already under making a material list.", MessageType.FAILURE),
    CONTRACT_STARTED("You're now making a material list, your block interactions are being monitored!", MessageType.SUCCESS),
    CONTRACT_HOWTO_END("To stop making a material list type /MaterialList Stop.", MessageType.TIP),
    CONTRACT_SUBMITTED("Your material list has been submitted!", MessageType.INFO),
    CONTRACT_ENDED("Your material list has been completed!", MessageType.SUCCESS),
    COMMAND_USAGE("/MaterialList <Start / Stop>", MessageType.INFO);

    private final String message;
    private final MessageType messageType;

    BakedMessage(String message, MessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
