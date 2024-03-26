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
import jdk.vm.ci.code.site.ConstantReference;

import java.util.ArrayList;
import java.util.HashSet;

public class PlayerTable {
    private DynamicMatrix<String, PlayableCard> matrix;
    private PlayerStats stats;

    public PlayerTable(){
        this.stats = new PlayerStats();
    }

    public PlayerStats getStats() {
        return this.stats;
    }


    public void insertStarterCard(int side, PlayableCard card){
        this.matrix = new DynamicMatrix<>(card.getID(), card );
    }

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

    private void updateStats(PlayableCard card){//usi il metodo setResourve(Kingdom, int) che verrà implementato nella Playersatts)
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
                if (card.getCardKingdom().equals(kingdom) && !alreadyUsedCards.contains(card)) {
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
            if(card.getCardKingdom().equals(kingdom1) && !alreadyUsedCards.contains(card)){
                tmp.add(card);
                card = matrix.get(card.getID(), corner);
                if(card!=null && card.getCardKingdom().equals(kingdom2) && !alreadyUsedCards.contains(card)){
                    tmp.add(card);
                    // if coveredCorner is one of the upper corners, then I should check if card above is correct,
                    // otherwise I check if card below is correct
                    card = matrix.get(card.getID(), corner < Corner.DL ? DynamicMatrix.U : DynamicMatrix.D);
                    if(card!=null && card.getCardKingdom().equals(kingdom2) && !alreadyUsedCards.contains(card)){
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