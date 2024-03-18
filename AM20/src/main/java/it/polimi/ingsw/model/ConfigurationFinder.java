package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.util.DynamicMatrix;

public interface ConfigurationFinder {
    public int findConfiguration(DynamicMatrix<String, PlayableCard> mat);
}