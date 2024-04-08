package it.polimi.ingsw.controller.exceptions;

public class CannotJoinGameException extends Exception{
    private final String reason;

    public CannotJoinGameException(String reason){
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Cannot join game: " + reason + "\n";
    }
}
