package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

public class StartPlayingMessage extends Message {
    private boolean startPlaying;
    private ObjectiveCard[] secretObjectives;
    private String firstPlayer;

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
        return firstPlayer;
    }

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
}
