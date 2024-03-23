package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.PlayerTable;

import java.util.Objects;

public class VerticalConfigurationObjectiveCard extends ObjectiveCard {
    private Kingdom kingdom1;
    private Kingdom kingdom2;
    private int coveredCorner;

    public VerticalConfigurationObjectiveCard(String ID, int points, Kingdom kingdom1, Kingdom kingdom2, int coveredCorner) {
        super(ID, points);
        this.kingdom1 = kingdom1;
        this.kingdom2 = kingdom2;
        this.coveredCorner = coveredCorner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VerticalConfigurationObjectiveCard that = (VerticalConfigurationObjectiveCard) o;
        return coveredCorner == that.coveredCorner && kingdom1 == that.kingdom1 && kingdom2 == that.kingdom2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), kingdom1, kingdom2, coveredCorner);
    }

    @Override
    public String toString() {
        return "VerticalConfigurationObjectiveCard{" +
                super.toString() +
                "kingdom1=" + kingdom1 +
                ", kingdom2=" + kingdom2 +
                ", coveredCorner=" + coveredCorner +
                "}";
    }

    @Override
    public int computePoints(PlayerTable table) {
        return 3*table.findVerticalConfiguration(this);
    }
}