package it.polimi.ingsw.controller.messages;

public class PingMessage extends Message {

    @Override
    public String getType() {
        return Message.PING;
    }
}
