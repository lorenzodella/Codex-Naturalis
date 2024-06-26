package it.polimi.ingsw.controller.messages;
/**
 * Message that needs to be sent when:
 * 1. an invalidDiusconnectionExcpetion is thorwn
 * 2. the game stops becuase the endGameTimer has run out
 * @see it.polimi.ingsw.server.EndGameTimer
 */
public class StopGameMessage extends Message {
    @Override
    public String getType() {
        return Message.STOPGAME;
    }
}
