package it.polimi.ingsw.client.clientcard;

import it.polimi.ingsw.client.tui.ConsoleColors;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.objective.VerticalConfigurationObjectiveCard;

public class VerticalConfigurationObjectiveCardClient implements  ObjectiveCardClient {

    private VerticalConfigurationObjectiveCard verticalConfigurationObjectiveCard;

    public VerticalConfigurationObjectiveCardClient(VerticalConfigurationObjectiveCard verticalConfigurationObjectiveCard) {
        this.verticalConfigurationObjectiveCard = verticalConfigurationObjectiveCard;
    }


    public void draw(){
        System.out.println("- VERTICAL CONFIGURATION OBJECTIVE: ");
        System.out.print("You need to create a vertical configuration, with a vertical occurrence of a ");
        if(this.verticalConfigurationObjectiveCard.getKingdom2().equals(Kingdom.Fungi))
            System.out.print(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
        else if(this.verticalConfigurationObjectiveCard.getKingdom2().equals(Kingdom.Insect))
            System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT"+ConsoleColors.TEXT_RESET);
        else if(this.verticalConfigurationObjectiveCard.getKingdom2().equals(Kingdom.Plant))
            System.out.print(ConsoleColors.TEXT_GREEN + "PLANT"+ ConsoleColors.TEXT_RESET);
        else
            System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL"+ConsoleColors.TEXT_RESET);

        System.out.print(ConsoleColors.TEXT_RESET + " card.");

        System.out.print(" This objective also needs of a ");
        if(this.verticalConfigurationObjectiveCard.getKingdom1().equals(Kingdom.Fungi))
            System.out.print(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
        else if(this.verticalConfigurationObjectiveCard.getKingdom1().equals(Kingdom.Insect))
            System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT"+ConsoleColors.TEXT_RESET);
        else if(this.verticalConfigurationObjectiveCard.getKingdom1().equals(Kingdom.Plant))
            System.out.print(ConsoleColors.TEXT_GREEN + "PLANT"+ ConsoleColors.TEXT_RESET);
        else
            System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL"+ConsoleColors.TEXT_RESET);

        System.out.print(ConsoleColors.TEXT_RESET+ " card that should cover the ");

        String angle;
        if(this.verticalConfigurationObjectiveCard.getCoveredCorner() == 0)
            angle= "UL";
        else if(this.verticalConfigurationObjectiveCard.getCoveredCorner() == 1)
            angle= "UR";
        else if(this.verticalConfigurationObjectiveCard.getCoveredCorner() == 2)
            angle= "DL";
        else
            angle= "DR";
        System.out.println( angle + "angle of the bottom " +this.verticalConfigurationObjectiveCard.getKingdom2() + " card." );
        System.out.println("You get 3 points every time you create this kind of configuration \n");
    }
}
