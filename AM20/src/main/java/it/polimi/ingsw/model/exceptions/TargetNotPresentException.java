package it.polimi.ingsw.model.exceptions;

import java.util.Objects;

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
