package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

public class Player {
    private int score;
    private String nickname;
    private PlayableCard[] cards;
    private StarterCard starterCard;
    private ObjectiveCard[] secretObjective;
    private PlayerTable table;

}
