package it.polimi.ingsw.model;

public class TrioOfObjectsObjectiveCard extends ObjectiveCard{


    public TrioOfObjectsObjectiveCard(String ID){ super(ID);
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
