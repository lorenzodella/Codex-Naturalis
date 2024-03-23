package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.objective.DiagonalConfigurationObjectiveCard;
import it.polimi.ingsw.model.cards.objective.VerticalConfigurationObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
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
    public boolean insertCard(PlayableCard card, int angle, int cardID, boolean front){return true;}

    private boolean isPositionValid(PlayableCard card, int angle, int cardID){return true;}

    private void updateStats(PlayableCard card){}

    public int numOfCoveredCorner(PlayableCard card){return 0;}

    public int findDiagonalConfiguration(DiagonalConfigurationObjectiveCard finder){return 0;}

    public int findVerticalConfiguration(VerticalConfigurationObjectiveCard finder){return 0;}

}