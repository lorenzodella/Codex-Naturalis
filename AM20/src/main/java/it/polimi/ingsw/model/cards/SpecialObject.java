package it.polimi.ingsw.model.cards;

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
}
