package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.PlayableCard;

public interface ConfigurationFinder {
    public int findConfiguration(DynamicMatrix<String, PlayableCard> mat);
}