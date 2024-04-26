package it.polimi.ingsw.controller.exceptions;

public class StopGameException extends Exception{
    String reason;

    public StopGameException(String reason){
        this.reason = reason;
    }

    @Override
    public String toString() {
        return reason;
    }
}
