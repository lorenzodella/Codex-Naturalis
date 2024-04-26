package it.polimi.ingsw.model.exceptions;

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
