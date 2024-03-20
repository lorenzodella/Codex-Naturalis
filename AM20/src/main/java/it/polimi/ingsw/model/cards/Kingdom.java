package it.polimi.ingsw.model.cards;

import java.util.HashMap;

public enum Kingdom {
    Fungi,
    Animal,
    Plant,
    Insect;

    public static Kingdom parseKingdom(String s){
        switch(s){
            case "Fungi":
                return Fungi;
            case "Animal":
                return Animal;
            case "Plant":
                return Plant;
            case "Insect":
                return Insect;
            default:
                return null;
        }
    }

    public static HashMap<Kingdom, Integer> createEmptyMap(){
        HashMap<Kingdom, Integer> map = new HashMap<>();
        map.put(Kingdom.Fungi, 0);
        map.put(Kingdom.Animal, 0);
        map.put(Kingdom.Plant, 0);
        map.put(Kingdom.Insect, 0);
        return map;
    }

}
