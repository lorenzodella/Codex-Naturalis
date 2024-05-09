package it.polimi.ingsw.controller.messages;

public class StopGameMessage extends Message {

    @Override
    public String getType() {
        return Message.STOPGAME;
    }
}
