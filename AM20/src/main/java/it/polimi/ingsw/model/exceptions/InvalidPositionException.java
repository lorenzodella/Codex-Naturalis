package it.polimi.ingsw.model.exceptions;

/**
 * EXCEPTION THROWN WHEN A PLAYER WANTS TO PLAY A CARD IN A WRONG POSITION
 */
public class InvalidPositionException extends DynamicMapException {
    private final int pos;

    public InvalidPositionException(int pos){
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "Position '"+pos+"' is not valid.\n";
    }
}
