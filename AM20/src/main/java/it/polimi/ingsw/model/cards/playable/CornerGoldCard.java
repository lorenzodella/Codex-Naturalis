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

    @Override
    public int computePoints(PlayerTable table) {
        int num = table.numOfCoveredCorner(this);
        return 2*num;
    }
}