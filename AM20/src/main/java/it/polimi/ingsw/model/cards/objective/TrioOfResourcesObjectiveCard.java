package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Kingdom;

import java.util.Objects;

public class TrioOfResourcesObjectiveCard extends ObjectiveCard{
    /**
     * The player needs to have at least 3 occurences of this specific resource in order to let
     * the player gain the promised points (points in the upperclass) that this card could give.
     * PS: This card gives you #points every 3 occureces of the same special resource.
     */
    private Kingdom resourcesKingdom;

    public TrioOfResourcesObjectiveCard(String ID, int points, Kingdom resourcesKingdom) {
        super(ID, points);
        this.resourcesKingdom = resourcesKingdom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TrioOfResourcesObjectiveCard that = (TrioOfResourcesObjectiveCard) o;
        return resourcesKingdom == that.resourcesKingdom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), resourcesKingdom);
    }

    @Override
    public String toString() {
        return "TrioOfResourcesObjectiveCard{" +
                super.toString() +
                "resourcesKingdom=" + resourcesKingdom +
                "}";
    }

    /**
     * After the user plays the card, and thanks to their PlayerTable (given by the param), this method returns
     * the points of that action.
     * PS: playertable allows us to know the number of kingdoms that there are on the table
     * @param table: the table of the player that plays the card
     * @return the number of the points of that action
     */
    @Override
    public int computePoints(PlayerTable table) {
        return 2*(table.getStats().getNumberOfResources(this.resourcesKingdom)/3);
    }
}