package it.polimi.ingsw.client.clientcard;

import it.polimi.ingsw.model.cards.objective.PairOfObjectsObjectiveCard;

public class PairOfObjectsObjectiveCardClient implements ObjectiveCardClient {

    private PairOfObjectsObjectiveCard pairOfObjectsObjectiveCard;

    public PairOfObjectsObjectiveCardClient(PairOfObjectsObjectiveCard pairOfObjectsObjectiveCard) {
        this.pairOfObjectsObjectiveCard = pairOfObjectsObjectiveCard;
    }

    /**
     * This method allows to print the information of the card
     */
    public void draw(){
        System.out.println("- PAIR OF OBJECTS OBJECTIVES: ");
        System.out.println("You get 2 points every time you collect two " +
                this.pairOfObjectsObjectiveCard.getSpecialObject() +" objects \n");
    }

}

