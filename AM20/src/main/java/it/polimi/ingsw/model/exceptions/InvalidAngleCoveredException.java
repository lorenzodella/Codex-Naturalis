package it.polimi.ingsw.model.exceptions;

/**
 * EXCEPTION THROWN WHEN A PLAYER WANTS TO PLAY A CARD BY COVERING AN ANGLE THAT DOES NOT EXIST OR AN ANGLE THAT IS
 * ALREADY COVERED BY ANOTHER CARD
 */
public class InvalidAngleCoveredException extends Exception{
    private final int angle;
    private final String cardID;

    public InvalidAngleCoveredException(String cardID, int angle) {
        this.cardID = cardID;
        this.angle = angle;
    }

    @Override
    public String toString() {
        return "Angle '"+angle+"' of card '"+cardID+"' is not coverable.\n";
    }
}
