package it.polimi.ingsw.model.exceptions;

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
