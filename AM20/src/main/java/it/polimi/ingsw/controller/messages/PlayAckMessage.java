package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.controller.PlayerInfo;

import java.util.HashMap;

public class PlayAckMessage extends AcknowledgeMessage{
    /**
     * This attribute ia a boolean that's going to be true only to the player whose turn is the next one
     */
    private boolean mustPick;

    @Override
    public String getAction() {
        return AcknowledgeMessage.PLAY;
    }

    @Override
    public boolean mustPick() {
        return mustPick;
    }

    @Override
    public void setMustPick(boolean mustPick) {
        this.mustPick = mustPick;
    }

    @Override
    public String toString() {
        return "PlayAckMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
