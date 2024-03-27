package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;

import java.util.Arrays;
import java.util.HashMap;

public abstract class PlayableCard extends Card {
    public static final int NONE = -1;
    public static final int FRONT = 1;
    public static final int BACK = 0;
    // playableCard è abstract Class
    private Corner[] frontCorners;
    private Corner[] backCorners;
    private int side;

    public PlayableCard(String ID, Corner[] frontCorners, Corner[] backCorners) {
        super(ID);
        this.frontCorners = frontCorners;
        this.backCorners = backCorners;
        this.side = FRONT;
    }

    public Kingdom getCardKingdom(){return null;}

    public Corner[] getFrontCorners() {
        return frontCorners;
    }

    public Corner[] getBackCorners() {
        return backCorners;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side){
        this.side = side;
    }

    //public abstract String getID();

    public HashMap<Kingdom, Integer> getKingdoms(){
        HashMap<Kingdom, Integer> map = Kingdom.createEmptyMap();
        for (Corner corner : getSide() == PlayableCard.FRONT ? getFrontCorners() : getBackCorners()){
            if(corner!=null && !corner.isHidden()) {
                map.computeIfPresent(corner.getContentKingdom(), (k,v)->v+1);
            }
        }
        return map;
    }
    public HashMap<SpecialObject, Integer> getSpecialObjects(){
        HashMap<SpecialObject, Integer> map = SpecialObject.createEmptyMap();
        for (Corner corner : getSide() == PlayableCard.FRONT ? getFrontCorners() : getBackCorners()){
            if(corner!=null && !corner.isHidden()) {
                map.computeIfPresent(corner.getContentObject(), (k,v)->v+1);
            }
        }
        return map;
    }

    public HashMap<Kingdom, Integer> getRequirements(){
        return Kingdom.createEmptyMap();
    }

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

