package it.polimi.ingsw.model;

public class ResourceCard extends PlayableCard implements PointsProvider{
    private Kingdom kingdom;
    private int point;

    public ResourceCard(String ID, Corner[] frontCorners, Corner[] backCorners) {
        super(ID, frontCorners, backCorners);
    }

    @Override
    public int computePoints(PlayerTable table) {
        return this.point;
    }
}