package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.PlayerTable;

public interface PointsProvider {
    public int computePoints(PlayerTable table);
}