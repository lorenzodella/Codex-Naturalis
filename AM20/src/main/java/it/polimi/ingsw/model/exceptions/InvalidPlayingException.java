package it.polimi.ingsw.model.exceptions;
/**
 * EXCEPTION THROWN WHEN A PLAYER WANTS TO MAKE AN INVALID ACTION (AN ACTION THAT COMES FROM ANOTHER GAME PHASE)
 */

public class InvalidPlayingException extends Exception{
    String reason;

    public InvalidPlayingException(String reason){
        this.reason = reason;
    }

    @Override
    public String toString() {
        return reason;
    }
}
