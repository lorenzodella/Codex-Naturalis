package it.polimi.ingsw.model;

import java.util.Arrays;
import java.util.HashMap;

public abstract class PlayableCard extends Card{
    // playableCard è abstract Class
    private Corner[] frontCorners;

    public Corner[] getFrontCorners() {
        return frontCorners;
    }

    public Corner[] getBackCorners() {
        return backCorners;
    }

    private Corner[] backCorners;

    public boolean isFront() {
        return front;
    }

    private boolean front;

    public PlayableCard(String ID, Corner[] frontCorners, Corner[] backCorners) {
        super(ID);
        this.frontCorners = frontCorners;
        this.backCorners = backCorners;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        PlayableCard that = (PlayableCard) o;
        return Arrays.equals(frontCorners, that.frontCorners) && Arrays.equals(backCorners, that.backCorners);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(frontCorners);
        result = 31 * result + Arrays.hashCode(backCorners);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() +
                "frontCorners=" + Arrays.toString(frontCorners) +
                ", backCorners=" + Arrays.toString(backCorners) +
                ", ";
    }

    public HashMap getKingdoms(){return null;}
    public HashMap getObjects(){return null;}
    public HashMap getRequirements(){return null;}
}

