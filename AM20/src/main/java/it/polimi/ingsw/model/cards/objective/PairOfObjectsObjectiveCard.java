package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.SpecialObject;

import java.util.Objects;

public class PairOfObjectsObjectiveCard extends ObjectiveCard{
    /**
     * The player needs to have at least 2 occurrences of this specific special object in order to let
     * the player gain the promised points (points in the upper class) that this card could give.
     * PS: This card gives you #points every 2 occurrences of the same special object.
     */
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

    /**
     * After the user plays the card, and thanks to their PlayerTable (given by the param), this method returns
     * the points of that action.
     * PS: playertable allows us to know the number of specialObjects that there are on the table
     * @param table: the table of the player that plays the card
     * @return the number of the points of that action
     */
    @Override
    public int computePoints(PlayerTable table) {
        return 2*(table.getStats().getNumberOfObjects(this.specialObject)/2);
    }

    public SpecialObject getSpecialObject(){
        return specialObject;
    }

}