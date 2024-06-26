package it.polimi.ingsw.controller.exceptions;
/**
 * EXCEPTION THROWN WHEN A PLAYER DISCONNECTS DURING THE PRELIMINARY PHASE AND IT CAUSES THE GAME RESET
 */
public class InvalidDisconnectionException extends Exception{
    @Override
    public String toString() {
        return "A player disconnected during preliminary phase of the game";
    }
}
