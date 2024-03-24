package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.PointsProvider;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.InsertionException;
import it.polimi.ingsw.model.exceptions.InvalidAngleCoveredException;
import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;

import java.util.LinkedList;
import java.util.List;

public class Player {
    private int score;
    private String nickname;
    private List<PlayableCard> cards;
    private StarterCard starterCard;
    private ObjectiveCard[] secretObjective;
    private PlayerTable table;

    public Player(String nickname){
        this.nickname = nickname;
        this.score = 0;
        this.table = new PlayerTable();
    }

    /*public Player(int score, String nickname, PlayableCard[] cards, StarterCard starterCard, ObjectiveCard[] secretObjective, PlayerTable table) {
        this.score = score;
        this.nickname = nickname;
        this.cards = cards;
        this.starterCard = starterCard;
        this.secretObjective = secretObjective;
        this.table = table;
    }*/

    public void setStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
    }

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public void setSecretObjective(ObjectiveCard[] secretObjective) {
        this.secretObjective = secretObjective;
    }

    //TODO da testare
    public void positionStarterCard(int side){
        table.insertStarterCard(side, this.starterCard);
        this.starterCard.setSide(side);
    }

    public void drawInitialPlayableCard(LinkedList<PlayableCard> playableCards){this.cards = playableCards;}

    public void playCard(int indexCard, int angle, String cardID, int side) throws TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, InsertionException {
        this.table.insertCard(this.cards.get(indexCard), angle, cardID, side);
        this.addPoints((PointsProvider) this.cards.get(indexCard));
    }

    //TODO da testare
    private void addPoints(PointsProvider card){
        score += card.computePoints(this.table);
    }

    public void drawCard(PlayableCard card){}

    public void chooseObjectiveCard(int index){}

    public void computeSecretObjective(){}

    public void computeCommonObjective(ObjectiveCard objectiveCard){}
}
