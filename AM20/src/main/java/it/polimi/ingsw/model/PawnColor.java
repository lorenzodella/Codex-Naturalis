package it.polimi.ingsw.model;

public enum PawnColor {
    RED,
    BLUE,
    GREEN,
    YELLOW;

    public static PawnColor parsePawnColor(String s){
        switch(s){
            case "red":
                return RED;
            case "blue":
                return BLUE;
            case "green":
                return GREEN;
            case "yellow":
                return YELLOW;
            default:
                return null;
        }
    }
}
