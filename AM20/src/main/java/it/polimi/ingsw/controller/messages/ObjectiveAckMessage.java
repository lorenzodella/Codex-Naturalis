package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

public class ObjectiveAckMessage extends Message {
    private boolean startPlaying;
    private ObjectiveCard[] secretObjectives;


    public boolean isStartPlaying() {
        return startPlaying;
    }

    public void setStartPlaying(boolean startPlaying) {
        this.startPlaying = startPlaying;
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
    public String toString() {
        return "ObjectiveAckMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
