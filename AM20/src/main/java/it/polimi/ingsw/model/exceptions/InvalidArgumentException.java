package it.polimi.ingsw.model.exceptions;
/**
 * EXCEPTION THROWN WHEN THE VALUE OF ANY PARAMETER PASSED TO THE METHOD IS NOT ACCEPTED
 */
public class InvalidArgumentException extends Exception{
    private final String argumentName;
    private final Object argumentValue;

    public InvalidArgumentException(String argumentName, Object argumentValue){
        this.argumentValue = argumentValue;
        this.argumentName = argumentName;
    }

    @Override
    public String toString() {
        return "Invalid argument '"+argumentName+"': value '"+argumentValue+"' is not valid.\n";
    }
}
