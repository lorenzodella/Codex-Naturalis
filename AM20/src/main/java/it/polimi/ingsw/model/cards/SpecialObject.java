package it.polimi.ingsw.model.cards;

import java.util.HashMap;

/**
 * enum of all the 3 different types of special objects
 */
public enum SpecialObject {
    Quill,
    Inkwell,
    Manuscript;

    public static SpecialObject parseSpecialObject(String s){
        switch(s){
            case "Quill":
                return Quill;
            case "Inkwell":
                return Inkwell;
            case "Manuscript":
                return Manuscript;
            default:
                return null;
        }
    }

    public static HashMap<SpecialObject, Integer> createEmptyMap(){
        HashMap<SpecialObject, Integer> map = new HashMap<>();
        map.put(SpecialObject.Quill, 0);
        map.put(SpecialObject.Inkwell, 0);
        map.put(SpecialObject.Manuscript, 0);
        return map;
    }
}
