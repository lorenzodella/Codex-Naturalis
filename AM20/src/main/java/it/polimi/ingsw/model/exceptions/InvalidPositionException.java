package it.polimi.ingsw.model.exceptions;

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
