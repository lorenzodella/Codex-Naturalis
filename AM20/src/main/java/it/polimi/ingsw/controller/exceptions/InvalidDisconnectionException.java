package it.polimi.ingsw.controller.exceptions;

public class InvalidDisconnectionException extends Exception{
    @Override
    public String toString() {
        return "A player disconnected during preliminary phase of the game";
    }
}
