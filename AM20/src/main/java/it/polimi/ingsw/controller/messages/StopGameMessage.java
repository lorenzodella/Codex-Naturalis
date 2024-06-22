package it.polimi.ingsw.controller.messages;

public class StopGameMessage extends Message {
    /**
     * Message that
     */
    @Override
    public String getType() {
        return Message.STOPGAME;
    }
}
