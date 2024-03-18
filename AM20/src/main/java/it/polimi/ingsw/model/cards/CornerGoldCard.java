package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.PlayerTable;

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
        return 0;
    }
}