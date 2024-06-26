package it.polimi.ingsw.controller.exceptions;
/**
 * EXCEPTION THROWN WHEN THE SERVER MANAGER DETECTS A DISCONNECTION AND NONE IS CONNECTED TO THE GAME ANYMORE
 */
public class NoOneIsConnectedException extends Exception{
    @Override
    public String toString() {
        return "No one is connected";
    }
}
