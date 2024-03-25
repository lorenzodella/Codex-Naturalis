package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

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

    public void addKingdomOrObject(Kingdom kingdom, SpecialObject object){
        if(kingdom != null)
            this.resources.put(kingdom, this.getNumberOfResources(kingdom)+1);
        else{
            this.specialObjects.put(object, this.getNumberOfObjects(object)+1);
        }
    }

    public void removeKingdomOrObject(Kingdom kingdom, SpecialObject object){
        if(kingdom != null)
            this.resources.put(kingdom, this.getNumberOfResources(kingdom)-1);
        else{
            this.specialObjects.put(object, this.getNumberOfObjects(object)-1);
        }
    }

    public void addKIngdom(Kingdom kingdom){
        this.resources.put(kingdom, this.getNumberOfResources(kingdom)+1);
    }

    public void addSpecialObjects(SpecialObject object){
        this.specialObjects.put(object, this.getNumberOfObjects(object)+1);
    }

    public void removeKingdom(Kingdom kingdom){
        this.resources.put(kingdom, this.getNumberOfResources(kingdom)-1);
    }

    public void removeSpeicalObjects(SpecialObject obj){
        this.specialObjects.put(obj, this.getNumberOfObjects(obj)+1);
    }
}
