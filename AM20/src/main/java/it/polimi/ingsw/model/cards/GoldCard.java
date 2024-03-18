package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.PointsProvider;

import java.util.*;

public abstract class GoldCard extends PlayableCard implements PointsProvider {
    private Kingdom kingdom;
    private Map<Kingdom, Integer> requirements;


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
}
