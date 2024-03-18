package it.polimi.ingsw.model.cards;

import java.util.Arrays;
import java.util.HashMap;

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

    public Corner[] getFrontCorners() {
        return frontCorners;
    }

    public Corner[] getBackCorners() {
        return backCorners;
    }

    public boolean isFront() {
        return front;
    }

    public HashMap<Kingdom, Integer> getKingdoms(){return null;}
    public HashMap<SpecialObject, Integer> getObjects(){return null;}
    public HashMap<Kingdom, Integer> getRequirements(){return null;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
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
}

