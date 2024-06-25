package it.polimi.ingsw.client.tui.clientcard;

import it.polimi.ingsw.client.tui.ConsoleColors;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.objective.DiagonalConfigurationObjectiveCard;

public class DiagonalConfigurationObjectiveCardClient implements ObjectiveCardClient {
    /**
     * Card that has to be printed out
     */
    private DiagonalConfigurationObjectiveCard diagonalConfigurationObjectiveCard;

    public DiagonalConfigurationObjectiveCardClient(DiagonalConfigurationObjectiveCard diagonalConfigurationObjectiveCard) {
        this.diagonalConfigurationObjectiveCard = diagonalConfigurationObjectiveCard;
    }

    /**
     * This method allows to print the information of the card
     */
    public void draw(){
        System.out.println("- DIAGONAL CONFIGURATION OBJECTIVE: ");
        System.out.print("You need to create a diagonal configuration, with ");

        if(this.diagonalConfigurationObjectiveCard.getKingdom().equals(Kingdom.Fungi))
            System.out.print(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
        else if(this.diagonalConfigurationObjectiveCard.getKingdom().equals(Kingdom.Insect))
            System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT"+ ConsoleColors.TEXT_RESET);
        else if(this.diagonalConfigurationObjectiveCard.getKingdom().equals(Kingdom.Plant))
            System.out.print(ConsoleColors.TEXT_GREEN + "PLANT"+ ConsoleColors.TEXT_RESET);
        else
            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL"+ ConsoleColors.TEXT_RESET);
        System.out.print(ConsoleColors.TEXT_RESET + " resource cards only, starting by covering the ");

        //definisco angolo per scorrerli
        String angle;
        if(this.diagonalConfigurationObjectiveCard.getCoveredCorner() == 0)
            angle= "UL";
        else if(this.diagonalConfigurationObjectiveCard.getCoveredCorner() == 1)
            angle= "UR";
        else if(this.diagonalConfigurationObjectiveCard.getCoveredCorner() == 2)
            angle= "DL";
        else
            angle= "DR";
        System.out.println(angle + "angle.");
        System.out.println("You get 2 points every time you create this kind of configuration\n ");

    }
}
