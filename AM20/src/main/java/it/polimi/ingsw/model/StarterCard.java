package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Objects;

public class StarterCard extends PlayableCard{
    private ArrayList<Kingdom> resources;

    public StarterCard(String ID, Corner[] frontCorners, Corner[] backCorners, ArrayList<Kingdom> resources) {
        super(ID, frontCorners, backCorners);
        this.resources = resources;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        StarterCard that = (StarterCard) o;
        return Objects.equals(resources, that.resources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resources);
    }

    @Override
    public String toString() {
        return "StarterCard{" +
                super.toString() +
                "resources=" + resources +
                "}";
    }
}
