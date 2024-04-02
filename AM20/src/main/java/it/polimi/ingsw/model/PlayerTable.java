package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.objective.DiagonalConfigurationObjectiveCard;
import it.polimi.ingsw.model.cards.objective.VerticalConfigurationObjectiveCard;
import it.polimi.ingsw.model.cards.playable.GoldCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.exceptions.DynamicMatrixException;
import it.polimi.ingsw.model.cards.playable.ResourceCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.util.DynamicMatrix;

import java.util.ArrayList;
import java.util.HashSet;

public class PlayerTable {
    /**
     * This is the player's matrix that shows the cards that have been player by this specific player
     */
    private DynamicMatrix<String, PlayableCard> matrix;
    /**
     * This attribute stands for the statistics of this player.
     * It basically says the number of resources and the number of visible objects.
     */
    private PlayerStats stats;

    public PlayerTable(){
        this.stats = new PlayerStats();
    }

    public PlayerStats getStats() {
        return this.stats;
    }

    /**
     * This method puts down the starter card of this player, by the side that's specified by the indicated "side".
     * @param side : the side of the card (front or back)
     * @param card : this is the starter card that the player needs to put down
     */
    public void insertStarterCard(int side, PlayableCard card){
        this.matrix = new DynamicMatrix<>(card.getID(), card );
         card.setSide(side);
    }

    /**
     * This method puts down on the player table, the card "card" by the side that's specified by the side "side".
     * This card needs to be put down by covering the angle "angle" of the card that's marked by the "targetID".
     * @param card: this attribute stands for the card that the player wants to play
     * @param angle : this attribute stands for the angle that you want to cover by positioning the card you're playing
     * @param targetID : this attribute stands for the card ID of the card that you want to cover by playing the card
     * @param side : this attribute specifies if the player want to play the card by the front or the back
     * @throws InsertionException
     * @throws InvalidAngleCoveredException
     * @throws TargetNotPresentException
     * @throws InvalidPositionException
     */
    //TODO
    public void insertCard(PlayableCard card, int angle, String targetID, int side) throws InsertionException, InvalidAngleCoveredException, TargetNotPresentException, InvalidPositionException {

        this.matrix.insert(card.getID(), card, targetID, angle);

        //array delle 4 carte coperte dalla carta che viene posizionata
        PlayableCard[] cardsCovered = new PlayableCard[4];
        cardsCovered[Corner.UL] = this.matrix.get(card.getID(), Corner.UL);
        cardsCovered[Corner.UR] = this.matrix.get(card.getID(), Corner.UR);
        cardsCovered[Corner.DL] = this.matrix.get(card.getID(), Corner.DL);
        cardsCovered[Corner.DR] = this.matrix.get(card.getID(), Corner.DR);

        try {

            this.isPositionValid(cardsCovered[Corner.UL], Corner.DR);
            this.isPositionValid(cardsCovered[Corner.UR], Corner.DL);
            this.isPositionValid(cardsCovered[Corner.DL], Corner.UR);
            this.isPositionValid(cardsCovered[Corner.DR], Corner.UL);

        } catch (Exception e){
            this.matrix.remove(card.getID());
            throw new InsertionException();
        }

        card.setSide(side);

        this.updateCorner(cardsCovered[Corner.UL], Corner.DR);
        this.updateCorner(cardsCovered[Corner.UR], Corner.DL);
        this.updateCorner(cardsCovered[Corner.DL], Corner.UR);
        this.updateCorner(cardsCovered[Corner.DR], Corner.UL);


        // update stats dopo la giocata della carta
        this.updateStats(card);
    }

    private void updateCorner(PlayableCard card, int angle){
        if(card != null){
            Corner[] tmp;
            if(card.getSide() == PlayableCard.FRONT)
                tmp = card.getFrontCorners();
            else
                tmp = card.getBackCorners();

            tmp[angle].setHidden(true);
            this.stats.removeKingdomOrObject(tmp[angle].getContentKingdom(), tmp[angle].getContentObject());
        }
    }


    /**
     * This method is called every time that a player wants to play a card and:
     * 1. it checks if the position of the card is valid --> it basically tells you if the way that the player wants to put down the
     *    card is correct
     * 2. it updates the resources' statistics --> it basically updates the player's statistics because, when the player
     *    plays the card, it may be covering some objects or resources
     * NON SERVE ANCHE CARD ID??
     * @param c :
     * @param angle
     * @throws InvalidAngleCoveredException
     * @throws TargetNotPresentException
     */
    /* TODO PER TIA:
     qua secondo me non serve il parametro side (anche perché non lhai usato)
     ATTENZIONE: controlla che la carta non sia null
     */
    private void isPositionValid(PlayableCard c, int angle) throws InvalidAngleCoveredException, TargetNotPresentException {

        if(c != null){
            Corner[] obj;
            if(c.getSide() == PlayableCard.FRONT)
                obj = c.getFrontCorners();
            else
                obj = c.getBackCorners();

            if(obj[angle] == null)
                throw new InvalidAngleCoveredException();
            if(obj[angle].isHidden())
                throw new InvalidAngleCoveredException();
        }
    }

    /**
     * This method updates the player's stats, every time that a card's played
     * @param card: the card that's just been played
     */
    public void updateStats(PlayableCard card){//usi il metodo setResourve(Kingdom, int) che verrà implementato nella Playersatts)
        Corner[] corners ;
        if(card.getSide() == PlayableCard.FRONT){
            corners = card.getFrontCorners();
        }else
            corners = card.getBackCorners();

        for(int i=0;i<corners.length;i++){
            if(corners[i] != null){
                this.stats.addKingdomOrObject(corners[i].getContentKingdom(),corners[i].getContentObject());
            }
        }

    }

    /**
     * This method returns the number of the covered angles of that card
     * @param card: this stands for the card that's being analyzed
     * @return the number of covered corner of that card
     */
    //TODO da testare
    public int numOfCoveredCorner(PlayableCard card){
        int num=0;
        try {
            for (int c = Corner.UL; c <= Corner.DR; c++) {
                if (matrix.get(card.getID(), c) != null)
                    num++;
            }
            return num;
        } catch (DynamicMatrixException e){
            return 0;
        }
    }

    /**
     * This method checks, inside the matrix, if there's a diagonal configuration.
     * Thanks to the finder, the method gets all the needed information for the configuration (such as the kingdom,
     * and the corner that needs to be covered).
     * @param finder : ...
     * @return the number of that type of configuration that has been found in the matrix
     */
    //TODO da testare
    public int findDiagonalConfiguration(DiagonalConfigurationObjectiveCard finder){
        PlayableCard card;
        HashSet<PlayableCard> alreadyUsedCards = new HashSet<>();
        HashSet<PlayableCard> tmp;
        int numOfConfigurations = 0;

        for(int i=0; i<matrix.height(); i++){
            for(int j=0; j<matrix.width(); j++){
                card = matrix.getElementAt(i,j);
                if(card!=null) {
                    tmp = checkDiagonally(card, finder.getKingdom(), finder.getCoveredCorner(), alreadyUsedCards);
                    if (tmp != null) {
                        numOfConfigurations++;
                        alreadyUsedCards.addAll(tmp);
                    }
                }
            }
        }
        return numOfConfigurations;
    }

    private HashSet<PlayableCard> checkDiagonally(PlayableCard card, Kingdom kingdom,
                                                    int corner, HashSet<PlayableCard> alreadyUsedCards){
        HashSet<PlayableCard> tmp = new HashSet<>();
        try {
            for (int n = 0; n < 3; n++) {
                if (card.getCardKingdom()!=null && card.getCardKingdom().equals(kingdom) && !alreadyUsedCards.contains(card)) {
                    tmp.add(card);
                    card = matrix.get(card.getID(), corner);
                } else
                    return null;
            }
            return tmp;
        } catch (DynamicMatrixException e){
            return null;
        }
    }
    /**
     * This method checks, inside the matrix, if there's a vertical configuration.
     * Thanks to the finder, the method gets all the needed information for the configuration (such as the kingdom,
     * and the corner that needs to be covered).
     * @param finder : ...
     * @return the number of that type of configuration that has been found in the matrix
     */
    //TODO da testare
    public int findVerticalConfiguration(VerticalConfigurationObjectiveCard finder){
        PlayableCard card;
        HashSet<PlayableCard> alreadyUsedCards = new HashSet<>();
        HashSet<PlayableCard> tmp;
        int numOfConfigurations = 0;

        for(int i=0; i<matrix.height(); i++){
            for(int j=0; j<matrix.width(); j++){
                card = matrix.getElementAt(i,j);
                if(card!=null) {
                    tmp = checkVertically(card, finder.getKingdom1(), finder.getKingdom2(), finder.getCoveredCorner(), alreadyUsedCards);
                    if (tmp != null) {
                        numOfConfigurations++;
                        alreadyUsedCards.addAll(tmp);
                    }
                }
            }
        }
        return numOfConfigurations;
    }

    private HashSet<PlayableCard> checkVertically(PlayableCard card, Kingdom kingdom1, Kingdom kingdom2,
                                                    int corner, HashSet<PlayableCard> alreadyUsedCards){
        HashSet<PlayableCard> tmp = new HashSet<>();
        try {
            if(card.getCardKingdom()!=null && card.getCardKingdom().equals(kingdom1) && !alreadyUsedCards.contains(card)){
                tmp.add(card);
                card = matrix.get(card.getID(), corner);
                if(card!=null && card.getCardKingdom()!=null &&
                        card.getCardKingdom().equals(kingdom2) && !alreadyUsedCards.contains(card)){
                    tmp.add(card);
                    // if coveredCorner is one of the upper corners, then I should check if card above is correct,
                    // otherwise I check if card below is correct
                    card = matrix.get(card.getID(), corner < Corner.DL ? DynamicMatrix.U : DynamicMatrix.D);
                    if(card!=null && card.getCardKingdom()!=null &&
                            card.getCardKingdom().equals(kingdom2) && !alreadyUsedCards.contains(card)){
                        tmp.add(card);
                        return tmp;
                    }
                }
            }
            return null;
        } catch (DynamicMatrixException e){
            return null;
        }
    }

}