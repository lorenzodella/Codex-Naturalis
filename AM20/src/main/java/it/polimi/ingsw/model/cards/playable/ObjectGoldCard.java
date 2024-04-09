package it.polimi.ingsw.model.cards.playable;


import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;

import java.util.HashMap;
import java.util.Objects;

public class ObjectGoldCard extends GoldCard{
    /**
     * This attribute points out the type of object of the gold card.
     * You need to count the occurrences of that object (on the playertable) to
     * set the amount of points that the user deserves
     */
    private SpecialObject specialObject;

    public ObjectGoldCard(String ID, Corner[] frontCorners, Corner[] backCorners,
                          Kingdom kingdom, HashMap<Kingdom, Integer> requirements,
                          SpecialObject specialObject) {
        super(ID, frontCorners, backCorners, kingdom, requirements);
        this.specialObject = specialObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ObjectGoldCard that = (ObjectGoldCard) o;
        return specialObject == that.specialObject;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialObject);
    }

    @Override
    public String toString() {
        return "ObjectGoldCard{" +
                super.toString() +
                "specialObject=" + specialObject +
                "}";
    }

    /**
     * After the user plays the card, and thanks to their PlayerTable (given by the param), this method returns
     * the points of that action.
     * PS: playertable allows us to know the number of the occurrences of the specialObject that's on the user's table.
     * @param table: the table of the player that plays the card
     * @return the number of the points of this action
     */
    @Override
    public int computePoints(PlayerTable table) {
        if(this.getSide() == PlayableCard.FRONT) {
            int res = table.getStats().getNumberOfObjects(this.specialObject);
            return res * 1;
        }else
            return 0;
    }
}