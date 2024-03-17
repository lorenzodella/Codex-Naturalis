package it.polimi.ingsw.model;
import java.util.*;

public abstract class GoldCard extends PlayableCard implements PointsProvider{
    private Kingdom kingdom;
    private Map<Kingdom, Integer> requirements;


    public GoldCard(String ID, Corner[] frontCorners, Corner[] backCorners) {
        super(ID, frontCorners, backCorners);
    }
}
