package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.objective.DiagonalConfigurationObjectiveCard;
import it.polimi.ingsw.model.cards.objective.VerticalConfigurationObjectiveCard;
import it.polimi.ingsw.model.cards.playable.GoldCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.ResourceCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.util.DynamicMatrix;

public class PlayerTable {
    private DynamicMatrix<String, PlayableCard> matrix;
    private PlayerStats stats;

    public PlayerTable(){
        this.stats = new PlayerStats();
    }

    public PlayerStats getStats() {
        return this.stats;
    }



    //TODO: il metodo insert della dynamicMatrix viene chaiamto quando devo nserire una carta generico, ma quando devo posizionare la starterCard non esiste un metodo che lo fa, ma la carta centrale
    // nella dynamicMatrix viene settat tramite il costruttore della DynamicMatrix quindi non so come gestire questa cosa (stesso problema del costruttore del Player)
    public void insertStarterCard(int front, PlayableCard card){
        this.matrix = new DynamicMatrix<>(card.getID(), card );
    }

    public void insertCard(PlayableCard card, int angle, String cardID, int front) throws InsertionException, InvalidAngleCoveredException, TargetNotPresentException, InvalidPositionException {

        this.matrix.insert(card.getID(), card, cardID, angle);
        try {

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

        PlayableCard tmp = this.matrix.find(cardID);
        tmp.setSide(front); //setta il side della carta

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


        /*//controlla se la carta viene giocata di front o back e se la si gioca in una posizione disponibile
        if(front == PlayableCard.FRONT)
            this.isPositionValid(card, angle, cardID);
        //inserisci la carta all'angol ospecificato
        this.matrix.insert(card.getID(), card, cardID, angle);
        PlayableCard tmp = this.matrix.find(cardID);
        tmp.setSide(front); //setta il side della carta

        //setta hidden l'angolo
        if(front == PlayableCard.FRONT){
            Corner[] c = tmp.getFrontCorners();
            c[angle].setHidden(true);
        }else {
            Corner[] c = tmp.getBackCorners();
            c[angle].setHidden(true);
        }

        this.updateStats(card);*/
    }

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

    public int numOfCoveredCorner(PlayableCard card){return 0;}

    public int findDiagonalConfiguration(DiagonalConfigurationObjectiveCard finder){return 0;}

    public int findVerticalConfiguration(VerticalConfigurationObjectiveCard finder){return 0;}

}