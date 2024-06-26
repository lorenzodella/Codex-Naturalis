package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.PlayerTable;

/**
 * This interface is used to compute the points of a player
 */
public interface PointsProvider {
    /**
     * This method computes the points of a player given their table
     * @param table : the table of the player
     * @return the points of the player
     */
    public int computePoints(PlayerTable table);
}