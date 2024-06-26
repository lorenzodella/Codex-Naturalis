package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.PointsProvider;
import it.polimi.ingsw.model.cards.SpecialObject;

import java.util.HashMap;
import java.util.Objects;

public class ResourceCard extends PlayableCard implements PointsProvider {
    /**
     * this attribute stands for the specific kingdom of the card
     */
    private Kingdom kingdom;
    /**
     * in this case point could be 0 or 1
     */
    private int points;

    public ResourceCard(String ID, Corner[] frontCorners, Corner[] backCorners, Kingdom kingdom, int points) {
        super(ID, frontCorners, backCorners);
        this.kingdom = kingdom;
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ResourceCard that = (ResourceCard) o;
        return points == that.points && kingdom == that.kingdom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), kingdom, points);
    }

    @Override
    public String toString() {
        return "ResourceCard{" +
                super.toString() +
                "kingdom=" + kingdom +
                ", points=" + points +
                "}";
    }

    @Override
    public Kingdom getCardKingdom() {
        return kingdom;
    }

    /**
     * If the card's been played by the front side, this method calls the upper class method.
     * Otherwise, the method returns a map with just a single element (the kingdom of the card).
     * @return a map or a map with only one element
     */
    @Override
    public HashMap<Kingdom, Integer> getKingdoms(){
        if(getSide() == PlayableCard.FRONT)
            return super.getKingdoms();
        else {
            HashMap<Kingdom, Integer> res = Kingdom.createEmptyMap();
            res.put(kingdom, 1);
            return res;
        }
    }

    /**
     * If the card's been played by the front side, this method calls the upper class method.
     * Otherwise, the method returns an empty map.
     * @return a map or an empty map
     */
    @Override
    public HashMap<SpecialObject, Integer> getSpecialObjects(){
        if(getSide() == PlayableCard.FRONT)
            return super.getSpecialObjects();
        else {
            return SpecialObject.createEmptyMap();
        }
    }

    /**
     * After the user plays the card, and thanks to their PlayerTable (given by the param), this method returns
     * the points of that action.
     * PS: all the resource card's points have a specific, established and forever value.
     * @param table: the table of the player that plays the card
     * @return the number of the points of this action
     */
    @Override
    public int computePoints(PlayerTable table) {

        if(this.getSide() == PlayableCard.FRONT)
            return this.points;
        else
            return 0;
    }

    /**
     * @return the number of points given by the play of this card
     */
    public int getPoints() {
        return points;
    }
}