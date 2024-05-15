package it.polimi.ingsw.client.clientcard;

import it.polimi.ingsw.client.ConsoleColors;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.playable.PointsGoldCard;

public class PointsGoldCardClient implements GoldCardClient{

    private PointsGoldCard pointsGoldCard;
    private int position;

    public PointsGoldCardClient(PointsGoldCard pointsGoldCard, int position){
        this.pointsGoldCard = pointsGoldCard;
        this.position = position;
    }

    public PointsGoldCardClient(PointsGoldCard pointsGoldCard) {
        this.pointsGoldCard = pointsGoldCard;
        this.position = 8;
    }

    public void draw(){

        if(this.position != 8){
            System.out.print("The card to the ");
            if(position == 0)
                System.out.print("left");
            else if(position == 1)
                System.out.print("middle");
            else if(position == 2)
                System.out.print("right");

            System.out.print(" is a gold card of ");
        }else
            System.out.print(" is a gold of ");


        if(pointsGoldCard.getCardKingdom().equals(Kingdom.Plant))
            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT"+ ConsoleColors.TEXT_RESET);
        else if(pointsGoldCard.getCardKingdom().equals(Kingdom.Insect))
            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT"+ ConsoleColors.TEXT_RESET);
            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
        else if(pointsGoldCard.getCardKingdom().equals(Kingdom.Fungi))
            System.out.println(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
        else if (pointsGoldCard.getCardKingdom().equals(Kingdom.Animal))
            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL"+ ConsoleColors.TEXT_RESET);

        System.out.println( "\nThe card has the following requirements:  ");
        if(this.pointsGoldCard.getRequirements().get(Kingdom.Plant) != 0){
            System.out.print(" - " + this.pointsGoldCard.getRequirements().get(Kingdom.Plant));
            System.out.println(ConsoleColors.TEXT_GREEN + " PLANT"+ ConsoleColors.TEXT_RESET);
        }
        if(this.pointsGoldCard.getRequirements().get(Kingdom.Insect) != 0){
            System.out.print(ConsoleColors.TEXT_RESET + " - " + this.pointsGoldCard.getRequirements().get(Kingdom.Insect));
            System.out.println(ConsoleColors.TEXT_PURPLE + " INSECT"+ConsoleColors.TEXT_RESET);
        }
        if(this.pointsGoldCard.getRequirements().get(Kingdom.Fungi) != 0){
            System.out.print(ConsoleColors.TEXT_RESET + " - " + this.pointsGoldCard.getRequirements().get(Kingdom.Fungi));
            System.out.println(ConsoleColors.TEXT_RED + " FUNGI" + ConsoleColors.TEXT_RESET);
        }
        if(this.pointsGoldCard.getRequirements().get(Kingdom.Animal) != 0){
            System.out.print(ConsoleColors.TEXT_RESET + " - " + this.pointsGoldCard.getRequirements().get(Kingdom.Animal));
            System.out.println(ConsoleColors.TEXT_CYAN + " ANIMAL"+ConsoleColors.TEXT_RESET);
        }

        System.out.println(ConsoleColors.TEXT_RESET + "\nYou get " + pointsGoldCard.getPoints() + " points if you play this card correctly ");

        System.out.println("\nFRONT CORNERS: ");
        for(int i=0;i<this.pointsGoldCard.getFrontCorners().length;i++){
            //definisco angolo
            String angle;
            if(i==0)
                angle= "UL";
            else if(i==1)
                angle= "UR";
            else if(i==2)
                angle= "DL";
            else
                angle= "DR";
            System.out.print("- "+angle+ ": ");

            if(this.pointsGoldCard.getFrontCorners()[i] == null)
                System.out.println("the corner doesn't exist");
            else if(this.pointsGoldCard.getFrontCorners()[i].getContentKingdom() != null){
                if(this.pointsGoldCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant))
                    System.out.println(ConsoleColors.TEXT_GREEN + "PLANT" + ConsoleColors.TEXT_RESET);
                else if(this.pointsGoldCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect))
                    System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT"+ ConsoleColors.TEXT_RESET);
                    //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                else if(this.pointsGoldCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi))
                    System.out.println(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
                else if (this.pointsGoldCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal))
                    System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL"+ ConsoleColors.TEXT_RESET);
            } else if(this.pointsGoldCard.getFrontCorners()[i].getContentObject() != null){
                if (this.pointsGoldCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell))
                    System.out.println("INKWELL");
                else if (this.pointsGoldCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill))
                    System.out.println("QUILL");
                else if (this.pointsGoldCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript))
                    System.out.println("MANUSCRIPT");
            } else {
                System.out.println("visible but empty");
            }
        }

        System.out.println("\nBACK CORNERS: ");
        //BACK: back di ogni carta ha 4 visible corners sempre
        System.out.print(ConsoleColors. TEXT_RESET + "The back of this card has the 4 visible corners and a ");
        if(this.pointsGoldCard.getCardKingdom().equals(Kingdom.Plant))
            System.out.print(ConsoleColors.TEXT_GREEN + "PLANT"+ ConsoleColors.TEXT_RESET);
        else if(this.pointsGoldCard.getCardKingdom().equals(Kingdom.Insect))
            System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT"+ ConsoleColors.TEXT_RESET);
            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
        else if(this.pointsGoldCard.getCardKingdom().equals(Kingdom.Fungi))
            System.out.print(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
        else if (this.pointsGoldCard.getCardKingdom().equals(Kingdom.Animal))
            System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL"+ ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_RESET + " resource");

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------\n");


    }

}
