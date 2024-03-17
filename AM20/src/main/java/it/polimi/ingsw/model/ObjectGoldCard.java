package it.polimi.ingsw.model;


public class ObjectGoldCard extends GoldCard{
    private SpecialObject specialObject;

    public ObjectGoldCard(String ID, Corner[] frontCorners, Corner[] backCorners) {
        super(ID, frontCorners, backCorners);
    }

    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}