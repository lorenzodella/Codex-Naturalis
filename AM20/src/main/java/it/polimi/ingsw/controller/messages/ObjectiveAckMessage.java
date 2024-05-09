package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

public class ObjectiveAckMessage extends Message {
    private ObjectiveCard[] secretObjectives;


    public boolean shouldStartPlaying() {
        return false;
    }


    public ObjectiveCard[] getSecretObjectives() {
        return secretObjectives;
    }

    public void setSecretObjectives(ObjectiveCard[] secretObjectives) {
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
