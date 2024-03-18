package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.PointsProvider;
import it.polimi.ingsw.model.cards.SpecialObject;

import java.util.HashMap;
import java.util.Objects;

public class ResourceCard extends PlayableCard implements PointsProvider {
    private Kingdom kingdom;
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
    public HashMap<Kingdom, Integer> getKingdoms(){
        if(isFront())
            return super.getKingdoms();
        else {
            HashMap<Kingdom, Integer> res = Kingdom.createEmptyMap();
            res.put(kingdom, 1);
            return res;
        }
    }

    @Override
    public HashMap<SpecialObject, Integer> getSpecialObjects(){
        if(isFront())
            return super.getSpecialObjects();
        else {
            return SpecialObject.createEmptyMap();
        }
    }

    @Override
    public int computePoints(PlayerTable table) {
        return this.points;
    }
}