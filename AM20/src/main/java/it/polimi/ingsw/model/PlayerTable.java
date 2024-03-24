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

    public void insertCard(PlayableCard card, int angle, String targetID, int side) throws InsertionException, InvalidAngleCoveredException, TargetNotPresentException, InvalidPositionException {

        this.matrix.insert(card.getID(), card, targetID, angle);
        try {
            /* TODO PER TIA
            queste variabili ti servono anche dopo perché devi updatare i loro corner se
            l'inserimento va a buon fine, una bella idea sarebbe fare un vettore come hai fatto sotto
            però invece di usare le posizioni 0,1,2,3 usa direttamente i nomi dei corner.
            ES. coveredCards[Corner.UL] = matrix.get(card.getID(), Corner.UL)
             */
            PlayableCard ULcard = this.matrix.get(card.getID(), Corner.UL);
            PlayableCard URcard = this.matrix.get(card.getID(), Corner.UR);
            PlayableCard DLcard = this.matrix.get(card.getID(), Corner.DL);
            PlayableCard DRcard = this.matrix.get(card.getID(), Corner.DR);

            this.isPositionValid(ULcard, Corner.DR, ULcard.getSide());
            this.isPositionValid(URcard, Corner.DL, URcard.getSide());
            this.isPositionValid(DLcard, Corner.UR, DLcard.getSide());
            this.isPositionValid(DRcard, Corner.UL, DRcard.getSide());
        } catch (Exception e){
            this.matrix.remove(card.getID());
            throw new InsertionException();
        }

        /* TODO PER TIA
        non ho capito a cosa serve tmp secondo me è sbagliato ->
        devi fare sempre lo stesso ragionamento di sopra per ottenere le 4 carte agli angoli e settare
        i loro corner a hidden (da quello che vedo setti a hidden solo i corner della targetCard che passi
        come parametro al metodo)
        SUGGERIMENTO: fai un metodo aggiuntivo simile a isPositionValid ->
        updateCorner(card, corner) che prende una carta e l'angolo che deve diventare hidden,
        sempre in quel metodo puoi anche aggiornare le stats rimuovendo la risorsa che c'era li
         */
        PlayableCard tmp = this.matrix.find(targetID);
        tmp.setSide(side); //setta il side della carta

        PlayableCard[] cardsCovered = new PlayableCard[4];
        cardsCovered[0] = this.matrix.get(card.getID(), Corner.UL);
        cardsCovered[1] = this.matrix.get(card.getID(), Corner.UR);
        cardsCovered[2] = this.matrix.get(card.getID(), Corner.DL);
        cardsCovered[3] = this.matrix.get(card.getID(), Corner.DR);


        //devo settare a hidden tutti gli angoli coperti
        for(int i=0;i<cardsCovered.length;i++){
            if(cardsCovered[i] != null){
                switch (i) {
                    case 0:
                        if(cardsCovered[i].getSide() == PlayableCard.FRONT){
                            Corner[] c = tmp.getFrontCorners();
                            c[Corner.DR].setHidden(true);
                        }else {
                            Corner[] c = tmp.getBackCorners();
                            c[Corner.DR].setHidden(true);
                        }
                        break;
                    case 1:
                        if(cardsCovered[i].getSide() == PlayableCard.FRONT){
                            Corner[] c = tmp.getFrontCorners();
                            c[Corner.DL].setHidden(true);
                        }else {
                            Corner[] c = tmp.getBackCorners();
                            c[Corner.DL].setHidden(true);
                        }

                        break;
                    case 2:
                        if(cardsCovered[i].getSide() == PlayableCard.FRONT){
                            Corner[] c = tmp.getFrontCorners();
                            c[Corner.UR].setHidden(true);
                        }else {
                            Corner[] c = tmp.getBackCorners();
                            c[Corner.UR].setHidden(true);
                        }
                        break;
                    case 3:
                        if(cardsCovered[i].getSide() == PlayableCard.FRONT){
                            Corner[] c = tmp.getFrontCorners();
                            c[Corner.UL].setHidden(true);
                        }else {
                            Corner[] c = tmp.getBackCorners();
                            c[Corner.UL].setHidden(true);
                        }
                        break;
                }
            }
        }

        // update stats dopo la giocata della carta
        this.updateStats(card);
    }

    /* TODO PER TIA:
     qua secondo me non serve il parametro side (anche perché non lhai usato)
     ATTENZIONE: controlla che la carta non sia null
     */
    private void isPositionValid(PlayableCard c, int angle, int side) throws InvalidAngleCoveredException, TargetNotPresentException {
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

    private void updateStats(PlayableCard card){//usi il metodo setResourve(Kingdom, int) che verrà implementato nella Playersatts)
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
        ArrayList<PlayableCard> alreadyUsedCards = new ArrayList<>();
        ArrayList<PlayableCard> tmp;
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

    private ArrayList<PlayableCard> checkDiagonally(PlayableCard card, Kingdom kingdom,
                                                    int corner, ArrayList<PlayableCard> alreadyUsedCards){
        ArrayList<PlayableCard> tmp = new ArrayList<>();
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
        ArrayList<PlayableCard> alreadyUsedCards = new ArrayList<>();
        ArrayList<PlayableCard> tmp;
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

    private ArrayList<PlayableCard> checkVertically(PlayableCard card, Kingdom kingdom1, Kingdom kingdom2,
                                                    int corner, ArrayList<PlayableCard> alreadyUsedCards){
        ArrayList<PlayableCard> tmp = new ArrayList<>();
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