package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.ArrayList;

public class ObjectiveAckMessage extends Message {
    /**
     * This attribute a 2 items array that contains the 2 secret objectives
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
