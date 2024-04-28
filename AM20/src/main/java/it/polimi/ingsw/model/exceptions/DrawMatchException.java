package it.polimi.ingsw.model.exceptions;

public class DrawMatchException extends Exception{
    @Override
    public String toString() {
        return "Match ended in a draw!";
    }
}
