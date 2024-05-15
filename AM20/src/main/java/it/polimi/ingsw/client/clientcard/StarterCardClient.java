package it.polimi.ingsw.client.clientcard;

import it.polimi.ingsw.client.ConsoleColors;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.playable.StarterCard;

public class StarterCardClient implements Drawer {

    private StarterCard starterCard;

    public StarterCardClient(StarterCard starterCard){
        this.starterCard = starterCard;
    }

    public void draw(){
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("STARTER CARD\n");

        //FRONT
        System.out.println("\nFRONT CORNERS: ");
        for(int i=0;i<this.starterCard.getFrontCorners().length;i++){
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

            if(this.starterCard.getFrontCorners()[i] == null)
                System.out.println("the corner doesn't exist");
            else if(this.starterCard.getFrontCorners()[i].getContentKingdom() != null){
                if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant))
                    System.out.println(ConsoleColors.TEXT_GREEN + "PLANT" + ConsoleColors.TEXT_RESET);
                else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect))
                    System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT"+ ConsoleColors.TEXT_RESET);
                    //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi))
                    System.out.println(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
                else if (this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal))
                    System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL"+ ConsoleColors.TEXT_RESET);
            } else if(this.starterCard.getFrontCorners()[i].getContentObject() != null){
                if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell))
                    System.out.println("INKWELL");
                else if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill))
                    System.out.println("QUILL");
                else if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript))
                    System.out.println("MANUSCRIPT");
            } else {
                System.out.println("visible but empty");
            }
        }
        System.out.print("\n");

        //BACK
        System.out.println("BACK CORNERS: ");
        for(int i=0;i<this.starterCard.getBackCorners().length;i++){
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
            if(this.starterCard.getBackCorners()[i] == null)
                System.out.println("the corner doesn't exist");
            else if(this.starterCard.getBackCorners()[i].getContentKingdom() != null){
                if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Plant))
                    System.out.println(ConsoleColors.TEXT_GREEN + "PLANT" + ConsoleColors.TEXT_RESET);
                else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Insect))
                    System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT"+ ConsoleColors.TEXT_RESET);
                    //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Fungi))
                    System.out.println(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
                else if (this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Animal))
                    System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL"+ ConsoleColors.TEXT_RESET);
            } else if(this.starterCard.getBackCorners()[i].getContentObject() != null){
                if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Inkwell))
                    System.out.println("INKWELL");
                else if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Quill))
                    System.out.println("QUILL");
                else if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Manuscript))
                    System.out.println("MANUSCRIPT");
            } else {
                System.out.println("visible but empty");
            }
        }
        System.out.println("\nResources in the middle: ");
        for(int i=0; i< this.starterCard.getResources().toArray().length;i++){
            if(this.starterCard.getResources().get(i).equals(Kingdom.Plant)){
                System.out.println(ConsoleColors.TEXT_GREEN + "PLANT "+ ConsoleColors.TEXT_RESET);
            }else if(this.starterCard.getResources().get(i).equals(Kingdom.Insect)){
                System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT "+ ConsoleColors.TEXT_RESET);
                //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
            }else if(this.starterCard.getResources().get(i).equals(Kingdom.Fungi)){
                System.out.println(ConsoleColors.TEXT_RED + "FUNGI "+ ConsoleColors.TEXT_RESET);
            }else if (this.starterCard.getResources().get(i).equals(Kingdom.Animal)) {
                System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL "+ ConsoleColors.TEXT_RESET);
            }
        }

    }
}
