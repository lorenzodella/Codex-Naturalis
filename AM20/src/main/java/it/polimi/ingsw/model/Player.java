package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.PointsProvider;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.InsertionException;
import it.polimi.ingsw.model.exceptions.InvalidAngleCoveredException;
import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Player {
    /**
     * This attribute stands for the score of this specific player
     */
    private int score;
    /**
     * This attribute stands for the name of this specific player
     */
    private String nickname;
    /**
     * This is a list of 3 items that stands for the playable cards that a player has in their hands
     */
    private List<PlayableCard> cards;
    /**
     * This attribute stands for the starter card of this specific player
     */
    private StarterCard starterCard;
    /**
     * This attribute stands for the secret objective card of this specific player
     */
    private ObjectiveCard[] secretObjective;
    /**
     * This attribute stands for the "table spot" of this specific player
     */
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

    public int getScore() {
        return score;
    }

    public ObjectiveCard[] getSecretObjective() {
        return secretObjective;
    }

    public void setSecretObjective(ObjectiveCard[] secretObjective) {
        this.secretObjective = secretObjective;
    }

    /**
     * This method actually puts down a card by the side that's specied by the parameter "side"
     * @param side: This attribute stands for the side of the card (front of back)
     */
    public void positionStarterCard(int side){
        table.insertStarterCard(side, this.starterCard);
    }

    public void drawInitialPlayableCard(LinkedList<PlayableCard> playableCards){this.cards = playableCards;}

    /**
     * This method actually puts the card (found at the index position of the cards list)
     * into the player's table, covering the angle (angle) of the card that's given by the card ID.
     * ps: the card ha sto be put down by the side that's specified by the "side" attribute.
     * @param indexCard : index of the card that you want to play that's found into the cards list (list of the
     *                  playable cards of that specific player)
     * @param angle : this attribute stands for the angle that you want to cover by positioning the card you're playing
     * @param cardID : this attribute stands for the card ID of the card that you want to cover by playing the card
     * @param side : this attribute specifies if the player want to play the card by the front or the back
     * @throws TargetNotPresentException
     * @throws InvalidAngleCoveredException
     * @throws InvalidPositionException
     * @throws InsertionException
     */
    public void playCard(int indexCard, int angle, String cardID, int side) throws TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, InsertionException {
        this.table.insertCard(this.cards.get(indexCard), angle, cardID, side);
        this.addPoints((PointsProvider) this.cards.get(indexCard));
        this.cards.remove(this.cards.get(indexCard));
    }

    /**
     * This method adds the point that the player has gained by playing their card in their turn.
     * @param card: this attribute stands for the card that's just been played
     */
    //TODO da testare
    private void addPoints(PointsProvider card){
        score += card.computePoints(this.table);
    }

    /**
     * This method puts, the card that's just been drawn by the player, into the list of playable cards that the player
     * has in their hands.
     *
     * @param card: this attribute stands for the card that's just been drawn
     */
    public void drawCard(PlayableCard card){
        this.cards.add(card);
    }

    /**
     * This method chooses the secret objective of the player.
     * Basically, this method, chooses one (the one that's in the index position) of the two objective cards that
     * have been given.
     * @param index: this attribute stands for the index of the card that the player want to choose
     */
    //dopo la chaimata al metodo il secretObjetive sarà sempre in posizione zero e quello in posizione 1 saraà null
    public void chooseObjectiveCard(int index){
        this.secretObjective[0] = this.secretObjective[index];
        this.secretObjective[1] = null;
    }

    /**
     * This method computes the points of the secret obejctive of that specific player
     */
    public void computeSecretObjective(){
        this.secretObjective[0].computePoints(this.table);
    }

    /**
     * This method computes the points of all the common objetives (once at a time) by passing every time a different
     * objective card.
     * @param objectiveCard
     */
    public void computeCommonObjective(ObjectiveCard objectiveCard){
        objectiveCard.computePoints(this.table);
    }
}
