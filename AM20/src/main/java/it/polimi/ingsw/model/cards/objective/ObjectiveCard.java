package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.PointsProvider;

import java.util.Objects;

public abstract class ObjectiveCard extends Card implements PointsProvider {
    private int points;

    public ObjectiveCard(String ID, int points) {
        super(ID);
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ObjectiveCard that = (ObjectiveCard) o;
        return points == that.points;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), points);
    }

    @Override
    public String toString() {
        return super.toString() +
                "points=" + points +
                ", ";
    }
}