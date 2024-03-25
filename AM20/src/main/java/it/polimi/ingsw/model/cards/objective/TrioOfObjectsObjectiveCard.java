package it.polimi.ingsw.model.cards.objective;

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
     * After the user plays the card, and thanks to their PlayerTable (given by the param), this method returns
     * the points of that action.
     * PS: playertable allows us to know the number of specialObjects that there are on the table
     * @param table: the table of the player that plays the card
     * @return
     */
    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}
