package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PlayerStats implements Serializable {
    /**
     * Hashmap that, per each kingdom, says the number of occurrences of that resource
     */
    private Map<Kingdom, Integer> resources;
    /**
     * Hashmap that, per each object, says the number of occurrences of that object
     */
    private Map<SpecialObject, Integer> specialObjects;

    public PlayerStats(){
        resources = Kingdom.createEmptyMap();
        specialObjects = SpecialObject.createEmptyMap();
    }

    /**
     * This method returns the occurrences of the kingdom "res"
     * @param res: the kingdom that's being analyzed
     * @return the number of occurrences of that kingdom
     */
    public int getNumberOfResources(Kingdom res){ return  resources.get(res);}
    /**
     * This method returns the occurrences of the object "obj"
     * @param obj: the object that's being analyzed
     * @return the number of occurrences of that object
     */
    public int getNumberOfObjects(SpecialObject obj){return  specialObjects.get(obj);}

    /**
     * This method is called every time that a gold card is played and, it checks if the
     * requirements (number of resources) are satisfied
     * @param req : hashmap that says the number of occurrences per each kingdom
     * @return 1 if the requirements are satisfied, 0 otherwise
     */
    public boolean checkRequirements(HashMap<Kingdom, Integer> req){
        return req.entrySet().stream()
                .allMatch(e -> resources.get(e.getKey()) >= e.getValue());
    }

    public void addKingdomOrObject(Kingdom kingdom, SpecialObject object){
        if(kingdom != null)
            this.resources.put(kingdom, this.getNumberOfResources(kingdom)+1);
        else if(object != null){
            this.specialObjects.put(object, this.getNumberOfObjects(object)+1);
        }
    }

    public void removeKingdomOrObject(Kingdom kingdom, SpecialObject object){
        if(kingdom != null)
            this.resources.put(kingdom, this.getNumberOfResources(kingdom)-1);
        else if(object != null) {
            this.specialObjects.put(object, this.getNumberOfObjects(object)-1);
        }
    }

    public void addKingdom(Kingdom kingdom){
        this.resources.put(kingdom, this.getNumberOfResources(kingdom)+1);
    }

    public void addSpecialObjects(SpecialObject object){
        this.specialObjects.put(object, this.getNumberOfObjects(object)+1);
    }

    public void removeKingdom(Kingdom kingdom){
        this.resources.put(kingdom, this.getNumberOfResources(kingdom)-1);
    }

    public void removeSpecialObjects(SpecialObject obj){
        this.specialObjects.put(obj, this.getNumberOfObjects(obj)+1);
    }

    public void addKingdom(Kingdom kingdom, int num){
        this.resources.put(kingdom, this.resources.get(kingdom)+num);

    }
    public void addObject(SpecialObject specialObject, int num){
        this.specialObjects.put(specialObject, this.specialObjects.get(specialObject)+ num);
    }
}
