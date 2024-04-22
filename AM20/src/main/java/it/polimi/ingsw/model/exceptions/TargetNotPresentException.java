package it.polimi.ingsw.model.exceptions;

public class TargetNotPresentException extends DynamicMapException {

    @Override
    public String toString() {
        return "Target element is not present";
    }
}
