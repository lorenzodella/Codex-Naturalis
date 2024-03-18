package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.PlayerTable;

import java.util.HashMap;
import java.util.Objects;

public class ObjectGoldCard extends GoldCard{
    private SpecialObject specialObject;

    public ObjectGoldCard(String ID, Corner[] frontCorners, Corner[] backCorners,
                          Kingdom kingdom, HashMap<Kingdom, Integer> requirements,
                          SpecialObject specialObject) {
        super(ID, frontCorners, backCorners, kingdom, requirements);
        this.specialObject = specialObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ObjectGoldCard that = (ObjectGoldCard) o;
        return specialObject == that.specialObject;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialObject);
    }

    @Override
    public String toString() {
        return "ObjectGoldCard{" +
                super.toString() +
                "specialObject=" + specialObject +
                "}";
    }

    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}