package it.polimi.ingsw.model;

public class PairOfObjectsObjectiveCard extends ObjectiveCard{
    private SpecialObject specialObject;

    public PairOfObjectsObjectiveCard(String ID) {
        super(ID);
    }

    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}