package it.polimi.ingsw.model.exceptions;

/**
 * EXCEPTION THROWN WHEN A DECK IS FINISHED
 */
public class FinishedCardStackException extends Exception{

    @Override
    public String toString() {
        return "Chosen deck is empty";
    }
}
