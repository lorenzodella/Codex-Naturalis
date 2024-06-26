package it.polimi.ingsw.controller.messages;

/**
 * Empty message that needs to be sent to any client to make sure that their connection is still alive
 */

public class PingMessage extends Message {

    @Override
    public String getType() {
        return Message.PING;
    }
}
