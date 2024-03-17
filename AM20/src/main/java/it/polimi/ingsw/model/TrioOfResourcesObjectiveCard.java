package it.polimi.ingsw.model;

public class TrioOfResourcesObjectiveCard extends ObjectiveCard{
    private Kingdom resourcesKingdom;

    public TrioOfResourcesObjectiveCard(String ID) {
        super(ID);
    }

    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}