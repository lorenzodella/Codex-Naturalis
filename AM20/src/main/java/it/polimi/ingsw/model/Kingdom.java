package it.polimi.ingsw.model;

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
}
