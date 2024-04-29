package it.polimi.ingsw.controller.exceptions;

public class NoOneIsConnectedException extends Exception{
    @Override
    public String toString() {
        return "No one is connected";
    }
}
