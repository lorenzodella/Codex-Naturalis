package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;

import java.util.HashMap;
import java.util.Objects;

public class PointsGoldCard extends GoldCard{

    /**
     * # of points that you owe if you play this card (with the needed requirements)
     */
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

    /**
     * After the user plays the card, and thanks to their PlayerTable (given by the param), this method returns
     * the points of that action.
     * PS: playertable allows us to know the number of the occurences of the specialObject that's on the user's table.
     * @param table: the table of the player that plays the card
     * @return
     */
    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}