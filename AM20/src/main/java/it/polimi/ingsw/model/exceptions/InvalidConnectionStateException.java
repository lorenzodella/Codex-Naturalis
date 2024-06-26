package it.polimi.ingsw.model.exceptions;
/**
 * EXCEPTION THROWN WHEN A PLAYER WANTS TO DISCONNECT FROM THE GAME BUT HE HAD ALREADY DISCONNECTED THE GAME PREVIOUSLY
 */
public class InvalidConnectionStateException extends Exception{
    String message;

    public InvalidConnectionStateException(boolean wasConnected){
        if(wasConnected)
            message = "You were already connected";
        else
            message = "You were already disconnected";
    }

    @Override
    public String toString() {
        return message;
    }
}
