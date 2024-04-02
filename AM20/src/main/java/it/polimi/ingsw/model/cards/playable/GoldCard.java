package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.PointsProvider;
import it.polimi.ingsw.model.cards.SpecialObject;

import java.util.*;

public abstract class GoldCard extends PlayableCard implements PointsProvider {
    private Kingdom kingdom;

    /**
     * map that shows the kingdoms and the number of occurrences of that specific kingdoms that
     * are needed in order to play the card.
     */
    private HashMap<Kingdom, Integer> requirements;


    public GoldCard(String ID, Corner[] frontCorners, Corner[] backCorners,
                    Kingdom kingdom, HashMap<Kingdom, Integer> requirements) {
        super(ID, frontCorners, backCorners);
        this.kingdom = kingdom;
        this.requirements = requirements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GoldCard goldCard = (GoldCard) o;
        return kingdom == goldCard.kingdom && Objects.equals(requirements, goldCard.requirements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), kingdom, requirements);
    }

    @Override
    public String toString() {
        return super.toString() +
                "kingdom=" + kingdom +
                ", backCorners=" + requirements +
                ", ";
    }

    @Override
    public Kingdom getCardKingdom() {
        return kingdom;
    }

    /**
     * If the card's been played by the front side, this method calls the upper class method.
     * Otherwise the method returns a map with just a single element (the kingdom of the card).
     * @return a map with one or more elements
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
     * Otherwise the method returns an empty map.
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
     * If the card's been played by the front side, this method returns the attribute (requirements).
     * Otherwise the method returns an empty map.
     * @return the map requirements or an empty map
     */
    @Override
    public HashMap<Kingdom, Integer> getRequirements() {
        if(getSide() == PlayableCard.FRONT)
            return requirements;
        else {
            return Kingdom.createEmptyMap();
        }
    }
}
