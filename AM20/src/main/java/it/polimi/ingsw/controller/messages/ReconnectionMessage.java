package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.ArrayList;
import java.util.List;

public class ReconnectionMessage extends StartGameMessage{
    /**
     * list of the secret objectives of the player that's rejoining the game now.
     */
    private ArrayList<ObjectiveCard> secretObjective;
    /**
     * Array of the two common objectives of the player that's rejoining the game now.
     */
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
