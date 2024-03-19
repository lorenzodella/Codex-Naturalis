package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.util.DynamicMatrix;

public class PlayerTable {
    private DynamicMatrix<String, PlayableCard> matrix;
    private PlayerStats stats;

    public PlayerTable(){
        this.stats = new PlayerStats();
    }





    //TODO: il metodo insert della dynamicMatrix viene chaiamto quando devo nserire una carta generico, ma quando devo posizionare la starterCard non esiste un metodo che lo fa, ma la carta centrale
    // nella dynamicMatrix viene settat tramite il costruttore della DynamicMatrix quindi non so come gestire questa cosa (stesso problema del costruttore del Player)
    public void insertStarterCard(int front, PlayableCard card){
        this.matrix = new DynamicMatrix<>(card.getID(), card );
    }
}