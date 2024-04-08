package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.cards.Kingdom;

import java.util.HashMap;
import java.util.stream.Collectors;

public class RequirementsNotRespectedException extends Exception{
    private final HashMap<Kingdom, Integer> req;

    public RequirementsNotRespectedException(HashMap<Kingdom, Integer> req){this.req = req;}

    public String toString() {
        return "The player hasn't enough resources: \n" +
                req.entrySet().stream().filter(e -> e.getValue()>0)
                .map(e -> e.getKey() + ": " + e.getValue() + "\n")
                        .collect(Collectors.joining());
    }

}
