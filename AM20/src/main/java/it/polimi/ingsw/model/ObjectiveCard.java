package it.polimi.ingsw.model;

public abstract class ObjectiveCard extends Card implements PointsProvider{
    private int points;

    public ObjectiveCard(String ID) {
        super(ID);
    }
}