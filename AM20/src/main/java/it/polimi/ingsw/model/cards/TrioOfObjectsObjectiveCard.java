package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.PlayerTable;

public class TrioOfObjectsObjectiveCard extends ObjectiveCard{

    public TrioOfObjectsObjectiveCard(String ID, int points) {
        super(ID, points);
    }

    @Override
    public String toString() {
        return "TrioOfObjectsObjectiveCard{" +
                super.toString() +
                "}";
    }

    /**
     * @param table
     * @return
     */
    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}
