package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;

import java.util.Arrays;
import java.util.HashMap;

public abstract class PlayableCard extends Card {
    public static final int FRONT = 1;
    public static final int BACK = 0;
    // playableCard è abstract Class
    private Corner[] frontCorners;
    private Corner[] backCorners;
    private int side;
    /**
     * Integer representing the order in which the cards were played
     */
    private int order;

    public PlayableCard(String ID, Corner[] frontCorners, Corner[] backCorners) {
        super(ID);
        this.frontCorners = frontCorners;
        this.backCorners = backCorners;
        this.side = FRONT;
        order = -1;
    }

    public static PlayableCard getDummyInstance(String id){
        return new PlayableCard(id, Corner.getDummyArray(), Corner.getDummyArray()) {
            @Override
            public boolean isValid() {
                return false;
            }
        };
    }

    public boolean isValid(){
        return true;
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

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    //public abstract String getID();

    /**
     * This method returns a map that tells you, per each kingdom, how many
     * occurrences of that kingdom there are in that specific card.
     * It basically tells you the kingdoms that are drawn in each card's corner.
     * @return the map that says all the occurrences of every kingdom
     */
    public HashMap<Kingdom, Integer> getKingdoms(){
        HashMap<Kingdom, Integer> map = Kingdom.createEmptyMap();
        for (Corner corner : getSide() == PlayableCard.FRONT ? getFrontCorners() : getBackCorners()){
            if(corner!=null && !corner.isHidden()) {
                map.computeIfPresent(corner.getContentKingdom(), (k,v)->v+1);
            }
        }
        return map;
    }

    /**
     * This method returns a map that tells you, per each object, how many
     * occurrences of that object there are in that specific card.
     * It basically tells you the objects that are drawn in each card's corner.
     * @return the map that says all the occurrences of every object
     */
    public HashMap<SpecialObject, Integer> getSpecialObjects(){
        HashMap<SpecialObject, Integer> map = SpecialObject.createEmptyMap();
        for (Corner corner : getSide() == PlayableCard.FRONT ? getFrontCorners() : getBackCorners()){
            if(corner!=null && !corner.isHidden()) {
                map.computeIfPresent(corner.getContentObject(), (k,v)->v+1);
            }
        }
        return map;
    }

    /**
     * This method returns a map that tells you, per each kingdom, how many occurrences of that kingdom
     * the user needs to have on their table to be able to play the card.
     * @return an empty map
     */
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

