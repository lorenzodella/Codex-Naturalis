package it.polimi.ingsw.model;

public class CornerGoldCard extends GoldCard{

    public CornerGoldCard(String ID, Corner[] frontCorners, Corner[] backCorners) {
        super(ID, frontCorners, backCorners);
    }

    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}