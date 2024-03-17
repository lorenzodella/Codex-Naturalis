package it.polimi.ingsw.model;

import java.util.ArrayList;

public class StarterCard extends PlayableCard{
    private ArrayList<Kingdom> resources;

    public StarterCard(String ID, Corner[] frontCorners, Corner[] backCorners, ArrayList<Kingdom> resources) {
        super(ID, frontCorners, backCorners);
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "StarterCard{" +
                "resources=" + resources +
                "} " + super.toString();
    }
}
