package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.ArrayList;
import java.util.List;

public class ReconnectionMessage extends StartGameMessage{

    private ArrayList<ObjectiveCard> secretObjective;
    private ObjectiveCard[] commonObjectives;

    public ArrayList<ObjectiveCard> getSecretObjective() {
        return secretObjective;
    }

    public void setSecretObjective(ArrayList<ObjectiveCard> secretObjective) {
        this.secretObjective = secretObjective;
    }

    public ObjectiveCard[] getCommonObjectives() {
        return commonObjectives;
    }

    public void setCommonObjectives(ObjectiveCard[] commonObjectives) {
        this.commonObjectives = commonObjectives;
    }

    @Override
    public boolean isReconnection() {
        return true;
    }

}
