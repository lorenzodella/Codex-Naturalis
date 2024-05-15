package it.polimi.ingsw.client.clientcard;

import it.polimi.ingsw.model.cards.objective.TrioOfObjectsObjectiveCard;

public class TrioOfObjectsObjectiveCardClient implements  ObjectiveCardClient {
    private TrioOfObjectsObjectiveCard trioOfObjectsObjectiveCard;

    public TrioOfObjectsObjectiveCardClient(TrioOfObjectsObjectiveCard trioOfObjectsObjectiveCard) {
        this.trioOfObjectsObjectiveCard = trioOfObjectsObjectiveCard;
    }


    public void draw(){
        System.out.println("- TRIO OF OBJECTS OBJECTIVE: ");
        System.out.println("You get 2 points every time you collect an Inkwell, a Manuscript and a Quill \n");
    }
}
