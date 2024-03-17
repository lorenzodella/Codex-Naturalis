package it.polimi.ingsw.model;

public class PointsGoldCard extends GoldCard{
    private int point;

    public PointsGoldCard(String ID, Corner[] frontCorners, Corner[] backCorners) {
        super(ID, frontCorners, backCorners);
    }

    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}