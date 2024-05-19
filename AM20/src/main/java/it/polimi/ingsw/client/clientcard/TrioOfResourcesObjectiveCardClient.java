package it.polimi.ingsw.client.clientcard;

import it.polimi.ingsw.client.tui.ConsoleColors;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.objective.TrioOfResourcesObjectiveCard;

public class TrioOfResourcesObjectiveCardClient implements  ObjectiveCardClient {

    private TrioOfResourcesObjectiveCard trioOfResourcesObjectiveCard;

    public TrioOfResourcesObjectiveCardClient(TrioOfResourcesObjectiveCard trioOfResourcesObjectiveCard) {
        this.trioOfResourcesObjectiveCard = trioOfResourcesObjectiveCard;
    }


    public void draw(){
        System.out.println("- TRIO OF RESOURCES OBJECTIVE: ");
        System.out.print("You get 2 points every time you collect three ");
        if(this.trioOfResourcesObjectiveCard.getResourcesKingdom().equals(Kingdom.Fungi))
            System.out.print(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
        else if(this.trioOfResourcesObjectiveCard.getResourcesKingdom().equals(Kingdom.Insect))
            System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT"+ ConsoleColors.TEXT_RESET);
        else if(this.trioOfResourcesObjectiveCard.getResourcesKingdom().equals(Kingdom.Plant))
            System.out.print(ConsoleColors.TEXT_GREEN + "PLANT"+ ConsoleColors.TEXT_RESET);
        else
            System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL"+ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_RESET + " resource cards \n");

    }
}
