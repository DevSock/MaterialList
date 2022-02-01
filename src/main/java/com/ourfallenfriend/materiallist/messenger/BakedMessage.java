package com.ourfallenfriend.materiallist.messenger;

import net.kyori.adventure.text.Component;

public enum BakedMessage {
    WRONG_ARGS("Invalid command arguments, please try again.", MessageType.FAILURE),
    SERVER_CONNECTION_FAILED("Server connection failed, please inform the system administrators.", MessageType.WARN),
    NOT_CONTRACTED("You aren't currently under a material list contract.", MessageType.FAILURE),
    ALREADY_CONTRACTED("You're already under a material list contract.", MessageType.FAILURE),
    CONTRACT_STARTED("You're now under a material list contract, and your block interactions are being tracked!", MessageType.SUCCESS),
    CONTRACT_HOWTO_END("In order to end your contract, type /MaterialList Stop.", MessageType.TIP),
    CONTRACT_SUBMITTED("Your material list contract has been submitted!", MessageType.INFO),
    CONTRACT_ENDED("Your material list contract has been completed!", MessageType.SUCCESS),
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
