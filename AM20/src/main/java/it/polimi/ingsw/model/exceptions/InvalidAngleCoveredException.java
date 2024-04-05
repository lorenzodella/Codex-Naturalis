package it.polimi.ingsw.model.exceptions;

public class InvalidAngleCoveredException extends Exception{
    private final int angle;

    public InvalidAngleCoveredException(int angle) {
        this.angle = angle;
    }

    @Override
    public String toString() {
        return "Angle '"+angle
                +"' is not coverable.\n";
    }
}
