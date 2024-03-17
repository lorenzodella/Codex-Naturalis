package it.polimi.ingsw.model;

import java.util.Arrays;

public abstract class PlayableCard extends Card{
    // playableCard è abstract Class
    private Corner[] frontCorners;
    private Corner[] backCorners;
    private boolean front;

    public PlayableCard(String ID, Corner[] frontCorners, Corner[] backCorners) {
        super(ID);
        this.frontCorners = frontCorners;
        this.backCorners = backCorners;
    }

    @Override
    public String toString() {
        return "PlayableCard{" +
                "frontCorners=" + Arrays.toString(frontCorners) +
                ", backCorners=" + Arrays.toString(backCorners) +
                "} " + super.toString();
    }
}

