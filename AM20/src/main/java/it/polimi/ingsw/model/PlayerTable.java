package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.PlayableCard;

public class PlayerTable {
    private DynamicMatrix<String, PlayableCard> matrix;
    private PlayerStats stats;
}