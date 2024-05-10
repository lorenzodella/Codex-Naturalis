package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.controller.PlayerInfo;

import java.util.HashMap;

public class PlayAckMessage extends AcknowledgeMessage{
    /**
     * This attribyte stands for the player's info after they played a card
     */
    private PlayerInfo yourPlayerInfo;
    /**
     * This attribute is a map that, per each player, says the player info of all other players  
     */
    private HashMap<String, PlayerInfo> othersPlayerInfo;
    /**
     * This attribute ia a boolean that's going to be true only to the player whose turn is the next one
     */
    private boolean mustPick;

    @Override
    public String getAction() {
        return AcknowledgeMessage.PLAY;
    }

    @Override
    public PlayerInfo getYourPlayerInfo() {
        return yourPlayerInfo;
    }

    @Override
    public void setYourPlayerInfo(PlayerInfo yourPlayerInfo) {
        this.yourPlayerInfo = yourPlayerInfo;
    }

    @Override
    public HashMap<String, PlayerInfo> getOthersPlayerInfo() {
        return othersPlayerInfo;
    }

    @Override
    public void setOthersPlayerInfo(HashMap<String, PlayerInfo> othersPlayerInfo) {
        this.othersPlayerInfo = othersPlayerInfo;
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
