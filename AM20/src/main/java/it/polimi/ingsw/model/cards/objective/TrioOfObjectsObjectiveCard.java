package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.SpecialObject;

import java.util.HashMap;

public class TrioOfObjectsObjectiveCard extends ObjectiveCard{

    public TrioOfObjectsObjectiveCard(String ID, int points) {
        super(ID, points);
    }

    @Override
    public String toString() {
        return "TrioOfObjectsObjectiveCard{" +
                super.toString() +
                "}";
    }

    /**
     * After the user plays the card, and thanks to their PlayerTable (given by the param), this method returns
     * the points of that action.
     * PS: playertable allows us to know the number of specialObjects that there are on the table
     * @param table: the table of the player that plays the card
     * @return
     */
    //TODO da testare
    @Override
    public int computePoints(PlayerTable table) {
        HashMap<SpecialObject, Integer> tmp = new HashMap<>();
        tmp.put(SpecialObject.Quill, table.getStats().getNumberOfObjects(SpecialObject.Quill));
        tmp.put(SpecialObject.Inkwell, table.getStats().getNumberOfObjects(SpecialObject.Inkwell));
        tmp.put(SpecialObject.Manuscript, table.getStats().getNumberOfObjects(SpecialObject.Manuscript));
        return 3*tmp.entrySet().stream().map(x->x.getValue()).min((int1, int2) ->int1<int2 ? int1 : int2).orElse(0);
    }
}
