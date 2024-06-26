package it.polimi.ingsw.controller.messages;
/**
 * Message that needs to be sent, to all players, when all players chose their starter card, in order to inform them
 * that they can finally start choosing their secret obejective
 */

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.ArrayList;

public class StartChoosingObjectiveMessage extends StarterCardAckMessage {

    /**
     * This attribute a 2 items array that contains the 2 common objectives
     */
    private ObjectiveCard[] commonObjectives;
    /**
     * This attribute is an arrayList of 2 items that contains the 2 secret objectives
     * ps: the player now needs to choose the secret objective they want
     */
    private ArrayList<ObjectiveCard> secretObjectives;

    @Override
    public boolean shouldChooseObjective() {
        return true;
    }

    @Override
    public ObjectiveCard[] getCommonObjectives() {
        return commonObjectives;
    }

    @Override
    public void setCommonObjectives(ObjectiveCard[] commonObjectives) {
        this.commonObjectives = commonObjectives;
    }

    @Override
    public ArrayList<ObjectiveCard> getSecretObjectives() {
        return secretObjectives;
    }

    public void setSecretObjectives(ArrayList<ObjectiveCard> secretObjectives) {
        this.secretObjectives = secretObjectives;
    }



    @Override
    public String toString() {
        return "StartChoosingObjectiveMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
