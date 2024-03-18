package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;

import java.util.Map;

public class PlayerStats {
    private Map<Kingdom, Integer> resources;
    private Map<SpecialObject, Integer> specialObjects;
}
