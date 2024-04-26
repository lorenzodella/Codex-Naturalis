package it.polimi.ingsw.model.exceptions;

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
