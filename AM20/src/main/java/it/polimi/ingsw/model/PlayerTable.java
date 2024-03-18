package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.util.DynamicMatrix;

public class PlayerTable {
    private DynamicMatrix<String, PlayableCard> matrix;
    private PlayerStats stats;
}