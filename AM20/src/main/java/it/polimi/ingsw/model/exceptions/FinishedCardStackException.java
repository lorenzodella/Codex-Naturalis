package it.polimi.ingsw.model.exceptions;

public class FinishedCardStackException extends Exception{

    @Override
    public String toString() {
        return "Chosen deck is empty";
    }
}
