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

        //array delle 4 carte coperte dalla carta che viene posizionata
        PlayableCard[] cardsCovered = new PlayableCard[4];
        cardsCovered[Corner.UL] = this.matrix.get(card.getID(), Corner.UL);
        cardsCovered[Corner.UR] = this.matrix.get(card.getID(), Corner.UR);
        cardsCovered[Corner.DL] = this.matrix.get(card.getID(), Corner.DL);
        cardsCovered[Corner.DR] = this.matrix.get(card.getID(), Corner.DR);

        try {
            /* TODO PER TIA
            queste variabili ti servono anche dopo perché devi updatare i loro corner se
            l'inserimento va a buon fine, una bella idea sarebbe fare un vettore come hai fatto sotto
            però invece di usare le posizioni 0,1,2,3 usa direttamente i nomi dei corner.
            ES. coveredCards[Corner.UL] = matrix.get(card.getID(), Corner.UL)
             */
            this.isPositionValid(cardsCovered[Corner.UL], Corner.DR);
            this.isPositionValid(cardsCovered[Corner.UR], Corner.DL);
            this.isPositionValid(cardsCovered[Corner.DL], Corner.UR);
            this.isPositionValid(cardsCovered[Corner.DR], Corner.UL);

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
        //PlayableCard tmp = this.matrix.find(targetID);
        //tmp.setSide(side); //setta il side della carta

        card.setSide(side);





        for(int i=0;i<cardsCovered.length;i++){
            if(cardsCovered[i] != null){
                Corner[] corners;
                if(cardsCovered[i].getSide() == PlayableCard.FRONT)
                    corners = cardsCovered[i].getFrontCorners();
                else
                    corners = cardsCovered[i].getBackCorners();

                //this.updateCorner(cardsCovered[i], corner);


                switch (i){
                    case Corner.UL:
                        corners[Corner.DR].setHidden(true);
                        this.stats.removeKingdomOrObject(corners[i].getContentKingdom(), corners[i].getContentObject());
                        break;
                    case Corner.UR:
                        corners[Corner.DL].setHidden(true);
                        this.stats.removeKingdomOrObject(corners[i].getContentKingdom(), corners[i].getContentObject());
                        break;
                    case Corner.DL:
                        corners[Corner.UR].setHidden(true);
                        this.stats.removeKingdomOrObject(corners[i].getContentKingdom(), corners[i].getContentObject());
                        break;
                    case Corner.DR:
                        corners[Corner.UL].setHidden(true);
                        this.stats.removeKingdomOrObject(corners[i].getContentKingdom(), corners[i].getContentObject());
                        break;
                }
            }
        }

        // update stats dopo la giocata della carta
        this.updateStats(card);
    }

    /*private void updateCorner(PlayableCard card, int angle){
        c.setHi


    }*/







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