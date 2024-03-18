package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.ConfigurationFinder;
import it.polimi.ingsw.model.DynamicMatrix;
import it.polimi.ingsw.model.PlayerTable;

import java.util.Objects;

public class DiagonalConfigurationObjectiveCard extends ObjectiveCard implements ConfigurationFinder {
    private Kingdom kingdom;
    private int coveredCorner;

    public DiagonalConfigurationObjectiveCard(String ID, int points, Kingdom kingdom, int coveredCorner) {
        super(ID, points);
        this.kingdom = kingdom;
        this.coveredCorner = coveredCorner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DiagonalConfigurationObjectiveCard that = (DiagonalConfigurationObjectiveCard) o;
        return coveredCorner == that.coveredCorner && kingdom == that.kingdom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), kingdom, coveredCorner);
    }

    @Override
    public String toString() {
        return "DiagonalConfigurationObjectiveCard{" +
                super.toString() +
                "kingdom=" + kingdom +
                ", coveredCorner=" + coveredCorner +
                "}";
    }

    @Override
    public int findConfiguration(DynamicMatrix<String, PlayableCard> mat) {
        return 0;
    }

    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}
