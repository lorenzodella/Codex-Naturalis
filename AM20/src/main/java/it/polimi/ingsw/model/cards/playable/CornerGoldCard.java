package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;

import java.util.HashMap;

public class CornerGoldCard extends GoldCard{

    public CornerGoldCard(String ID, Corner[] frontCorners, Corner[] backCorners,
                          Kingdom kingdom, HashMap<Kingdom, Integer> requirements) {
        super(ID, frontCorners, backCorners, kingdom, requirements);
    }

    @Override
    public String toString() {
        return "CornerGoldCard{" +
                super.toString() +
                "}";
    }

    /**
     * After the user plays the card, and thanks to their PlayerTable (given by the param), this method returns
     * the points of that action.
     * PS: playertable allows us to know the number of corners that are covered by this specific card
     * @param table: the table of the player that plays the card
     * @return
     */
    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}