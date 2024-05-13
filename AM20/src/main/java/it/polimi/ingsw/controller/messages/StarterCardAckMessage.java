package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.ArrayList;
import java.util.HashMap;

public class StarterCardAckMessage extends Message {

    /**
     * This attribute stands for the player info
     */
    private PlayerInfo playerInfo;
    /**
     * This attribute is a map that, per each player, shows all other players' info
     */
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

    public ArrayList<ObjectiveCard> getSecretObjectives() {
        return null;
    }

    public void setSecretObjectives(ArrayList<ObjectiveCard> secretObjectives) {

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
