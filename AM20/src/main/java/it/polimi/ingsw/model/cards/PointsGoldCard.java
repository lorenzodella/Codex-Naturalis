package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.PlayerTable;

import java.util.HashMap;
import java.util.Objects;

public class PointsGoldCard extends GoldCard{
    private int points;

    public PointsGoldCard(String ID, Corner[] frontCorners, Corner[] backCorners,
                          Kingdom kingdom, HashMap<Kingdom, Integer> requirements,
                          int points) {
        super(ID, frontCorners, backCorners, kingdom, requirements);
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PointsGoldCard that = (PointsGoldCard) o;
        return points == that.points;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), points);
    }

    @Override
    public String toString() {
        return "PointsGoldCard{" +
                super.toString() +
                "points=" + points +
                "}";
    }

    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}