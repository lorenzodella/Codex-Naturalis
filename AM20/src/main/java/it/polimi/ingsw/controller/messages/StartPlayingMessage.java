package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

public class StartPlayingMessage extends Message {
    private boolean startPlaying;
    private ObjectiveCard[] secretObjectives;
    private String firstPlayer;
}
