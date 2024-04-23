package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.HashMap;

public class StartChoosingObjectiveMessage extends Message {

    private boolean chooseObjective;
    private PlayerInfo playerInfo;
    private HashMap<String, PlayerInfo> othersPlayerInfo;
    private ObjectiveCard[] commonObjectives;
    private ObjectiveCard[] secretObjectives;

    public boolean isChooseObjective() {
        return chooseObjective;
    }

    public void setChooseObjective(boolean chooseObjective) {
        this.chooseObjective = chooseObjective;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public ObjectiveCard[] getCommonObjectives() {
        return commonObjectives;
    }

    public void setCommonObjectives(ObjectiveCard[] commonObjectives) {
        this.commonObjectives = commonObjectives;
    }

    public ObjectiveCard[] getSecretObjectives() {
        return secretObjectives;
    }

    public void setSecretObjectives(ObjectiveCard[] secretObjectives) {
        this.secretObjectives = secretObjectives;
    }

    public HashMap<String, PlayerInfo> getOthersPlayerInfo() {
        return othersPlayerInfo;
    }

    public void setOthersPlayerInfo(HashMap<String, PlayerInfo> othersPlayerInfo) {
        this.othersPlayerInfo = othersPlayerInfo;
    }
}
