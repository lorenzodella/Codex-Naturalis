package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.PlayerTable;

import java.util.Objects;

public class PairOfObjectsObjectiveCard extends ObjectiveCard{
    private SpecialObject specialObject;

    public PairOfObjectsObjectiveCard(String ID, int points, SpecialObject specialObject) {
        super(ID, points);
        this.specialObject = specialObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PairOfObjectsObjectiveCard that = (PairOfObjectsObjectiveCard) o;
        return specialObject == that.specialObject;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialObject);
    }

    @Override
    public String toString() {
        return "PairOfObjectsObjectiveCard{" +
                super.toString() +
                "specialObject=" + specialObject +
                "}";
    }

    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}