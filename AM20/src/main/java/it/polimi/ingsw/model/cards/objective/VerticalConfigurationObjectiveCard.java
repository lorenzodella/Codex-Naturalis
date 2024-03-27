package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.PlayerTable;

import java.util.Objects;

public class VerticalConfigurationObjectiveCard extends ObjectiveCard {
    /**
     * this attribute says the kingdom of the card that has a single occurence in this specific veryical configuration
     */
    private Kingdom kingdom1;
    /**
     * this attribute says the kingdom of the card that has a single occurence in this specific veryical configuration
     */
    private Kingdom kingdom2;
    /**
     * this attribute says which corner (of the card that's been placed on the top of the configuration)
     * has to be covered in this specific vertical configuration
     */
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

    public Kingdom getKingdom1() {
        return kingdom1;
    }

    public Kingdom getKingdom2() {
        return kingdom2;
    }

    public int getCoveredCorner() {
        return coveredCorner;
    }

    //TODO da testare
    /**
     * After the user plays the card, and thanks to their PlayerTable (given by the param), this method returns
     * the points of that action.
     * PS: playertable allows us to know the number of this type of diagonal configuration that there are on the table
     * @param table: the table of the player that plays the card
     * @return
     */
    @Override
    public int computePoints(PlayerTable table) {
        return 3*table.findVerticalConfiguration(this);
    }

}