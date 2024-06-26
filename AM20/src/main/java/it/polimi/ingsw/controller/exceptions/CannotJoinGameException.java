package it.polimi.ingsw.controller.exceptions;
/**
 * EXCEPTION THROWN WHEN THE SERVER DOES NOT ACCEPT A JOIN REQUEST FOR ANY SPECIFIC REASON
 */
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
