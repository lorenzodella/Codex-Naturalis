package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.PointsProvider;
import it.polimi.ingsw.model.cards.SpecialObject;

import java.util.*;

public abstract class GoldCard extends PlayableCard implements PointsProvider {
    private Kingdom kingdom;
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
    public HashMap<Kingdom, Integer> getKingdoms(){
        if(getSide() == PlayableCard.FRONT)
            return super.getKingdoms();
        else {
            HashMap<Kingdom, Integer> res = Kingdom.createEmptyMap();
            res.put(kingdom, 1);
            return res;
        }
    }

    @Override
    public HashMap<SpecialObject, Integer> getSpecialObjects(){
        if(getSide() == PlayableCard.FRONT)
            return super.getSpecialObjects();
        else {
            return SpecialObject.createEmptyMap();
        }
    }

    @Override
    public HashMap<Kingdom, Integer> getRequirements() {
        if(getSide() == PlayableCard.FRONT)
            return requirements;
        else {
            return Kingdom.createEmptyMap();
        }
    }
}
