package it.polimi.ingsw.model.exceptions;

import java.util.Objects;
/**
 * EXCEPTION THROWN WHEN A PLAYER WANTS TO PLAY A CARD BY COVERING A TARGET CARD THAT DOES NOT EXIST
 */
public class TargetNotPresentException extends DynamicMapException {
    Object o;

    public TargetNotPresentException(Object o){
        this.o = o;
    }

    @Override
    public String toString() {
        return "Target element '"+o+"' is not present";
    }
}
