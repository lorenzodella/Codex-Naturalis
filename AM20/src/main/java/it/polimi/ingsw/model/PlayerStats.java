package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;

import java.util.HashMap;
import java.util.Map;

public class PlayerStats {
    private Map<Kingdom, Integer> resources;
    private Map<SpecialObject, Integer> specialObjects;

    public PlayerStats(){
        resources = Kingdom.createEmptyMap();
        specialObjects = SpecialObject.createEmptyMap();
    }

    public int getNumberOfResources(Kingdom res){ return  resources.get(res);}

    public int getNumberOfObjects(SpecialObject obj){return  specialObjects.get(obj);}

    //TODO da testare
    public boolean checkRequirements(HashMap<Kingdom, Integer> req){
        return req.entrySet().stream()
                .allMatch(e -> resources.get(e.getKey()) >= e.getValue());
    }
}
