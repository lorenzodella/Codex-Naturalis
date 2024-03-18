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

    public Player(int score, String nickname, PlayableCard[] cards, StarterCard starterCard, ObjectiveCard[] secretObjective, PlayerTable table) {
        this.score = score;
        this.nickname = nickname;
        this.cards = cards;
        this.starterCard = starterCard;
        this.secretObjective = secretObjective;
        this.table = table;
    }

    public void setStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
    }

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public void positionStarterCard(boolean front){
        //table.insertStarterCard(front, this.starterCard);
    }
}
