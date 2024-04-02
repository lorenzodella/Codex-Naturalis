package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.PlayerTable;

import java.util.Objects;

public class DiagonalConfigurationObjectiveCard extends ObjectiveCard{
    private Kingdom kingdom;
    /**
     * This attribute says which corner (of the card that's been placed on the top of the configuration) has been covered.
     * PS:
     * 0 = UL
     * 1 = UR
     * 2 = DL
     * 3 = DR
     */
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

    public Kingdom getKingdom() {
        return kingdom;
    }

    public int getCoveredCorner() {
        return coveredCorner;
    }

    /**
     * After the user plays the card, and thanks to their PlayerTable (given by the param), this method returns
     * the points of that action.
     * PS: playertable allows us to know the number of this type of diagonal configuration that there are on the table
     * @param table: the table of the player that plays the card
     * @return the number of the points of that action
     */
    @Override
    public int computePoints(PlayerTable table) {
        return 2*table.findDiagonalConfiguration(this);
    }
}
