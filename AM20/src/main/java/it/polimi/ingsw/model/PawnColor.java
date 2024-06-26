package it.polimi.ingsw.model;

/**
 * This enumeration represents the color of the pawn of a player
 */
public enum PawnColor {
    RED,
    BLUE,
    GREEN,
    YELLOW;

    /**
     * This method is used to parse a string into a PawnColor
     * @param s : the string that's being parsed
     * @return the PawnColor that corresponds to the string
     */
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
