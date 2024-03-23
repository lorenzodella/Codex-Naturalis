package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Kingdom;

import java.util.Objects;

public class TrioOfResourcesObjectiveCard extends ObjectiveCard{
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

    @Override
    public int computePoints(PlayerTable table) {
        return 2*(table.getStats().getNumberOfResources(this.resourcesKingdom)/3);
    }
}