package it.polimi.ingsw.controller.messages;
/**
 * Message that needs to be sent, to the player that just chose their secret objective
 */

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.ArrayList;

public class ObjectiveAckMessage extends Message {
    /**
     * This attribute is an arrayList of 2 items that contains the 2 secret objectives
     */
    private ArrayList<ObjectiveCard> secretObjectives;


    public boolean shouldStartPlaying() {
        return false;
    }


    public ArrayList<ObjectiveCard> getSecretObjectives() {
        return secretObjectives;
    }

    public void setSecretObjectives(ArrayList<ObjectiveCard> secretObjectives) {
        this.secretObjectives = secretObjectives;
    }

    public String getFirstPlayer() {
        return null;
    }

    public void setFirstPlayer(String firstPlayer) {

    }

    @Override
    public String getType() {
        return Message.OBJECTIVEACK;
    }

    @Override
    public String toString() {
        return "ObjectiveAckMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
