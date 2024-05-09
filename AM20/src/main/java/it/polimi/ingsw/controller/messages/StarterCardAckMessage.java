package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.HashMap;

public class StarterCardAckMessage extends Message {

    private PlayerInfo playerInfo;
    private HashMap<String, PlayerInfo> othersPlayerInfo;


    public boolean shouldChooseObjective() {
        return false;
    }


    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public ObjectiveCard[] getCommonObjectives() {
        return null;
    }

    public void setCommonObjectives(ObjectiveCard[] commonObjectives) {
    }

    public ObjectiveCard[] getSecretObjectives() {
        return null;
    }

    public void setSecretObjectives(ObjectiveCard[] secretObjectives) {

    }

    public HashMap<String, PlayerInfo> getOthersPlayerInfo() {
        return othersPlayerInfo;
    }

    public void setOthersPlayerInfo(HashMap<String, PlayerInfo> othersPlayerInfo) {
        this.othersPlayerInfo = othersPlayerInfo;
    }

    @Override
    public String getType() {
        return Message.STARTERCARDACK;
    }

    @Override
    public String toString() {
        return "StarterCardAckMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
