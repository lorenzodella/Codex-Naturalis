package it.polimi.ingsw.client.tui.clientcard;

import it.polimi.ingsw.client.tui.ConsoleColors;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.playable.ObjectGoldCard;

public class ObjectGoldCardClient implements GoldCardClient{
    /**
     * Card that has to be printed out
     */
    private ObjectGoldCard objectGoldCard;
    /**
     * If the card that has to printed out is in the hand card, the attribute position is set to the correct position in the
     * hand card. Otherwise, if the card is located in the deck, the attribute position is set to 8
     */
    private int position;

    public ObjectGoldCardClient(ObjectGoldCard objectGoldCard,int position){

        this.objectGoldCard = objectGoldCard;
        this.position = position;
    }

    public ObjectGoldCardClient(ObjectGoldCard objectGoldCard) {
        this.objectGoldCard = objectGoldCard;
        this.position = 8;
    }

    /**
     * This method allows to print the information of the card
     */
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


        if(objectGoldCard.getCardKingdom().equals(Kingdom.Plant))
            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT"+ ConsoleColors.TEXT_RESET);
        else if(objectGoldCard.getCardKingdom().equals(Kingdom.Insect))
            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT"+ ConsoleColors.TEXT_RESET);
            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
        else if(objectGoldCard.getCardKingdom().equals(Kingdom.Fungi))
            System.out.println(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
        else if (objectGoldCard.getCardKingdom().equals(Kingdom.Animal))
            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL"+ ConsoleColors.TEXT_RESET);

        System.out.println( "\nThe card has the following requirements:  ");
        if(this.objectGoldCard.getRequirements().get(Kingdom.Plant) != 0){
            System.out.print(" - " + this.objectGoldCard.getRequirements().get(Kingdom.Plant));
            System.out.println(ConsoleColors.TEXT_GREEN + " PLANT"+ ConsoleColors.TEXT_RESET);
        }
        if(this.objectGoldCard.getRequirements().get(Kingdom.Insect) != 0){
            System.out.print(ConsoleColors.TEXT_RESET + " - " + this.objectGoldCard.getRequirements().get(Kingdom.Insect));
            System.out.println(ConsoleColors.TEXT_PURPLE + " INSECT"+ConsoleColors.TEXT_RESET);
        }
        if(this.objectGoldCard.getRequirements().get(Kingdom.Fungi) != 0){
            System.out.print(ConsoleColors.TEXT_RESET + " - " + this.objectGoldCard.getRequirements().get(Kingdom.Fungi));
            System.out.println(ConsoleColors.TEXT_RED + " FUNGI" + ConsoleColors.TEXT_RESET);
        }
        if(this.objectGoldCard.getRequirements().get(Kingdom.Animal) != 0){
            System.out.print(ConsoleColors.TEXT_RESET + " - " + this.objectGoldCard.getRequirements().get(Kingdom.Animal));
            System.out.println(ConsoleColors.TEXT_CYAN + " ANIMAL"+ConsoleColors.TEXT_RESET);
        }

        System.out.print(ConsoleColors.TEXT_RESET +  "You get 1 point if you play this card correctly for every");
        if(this.objectGoldCard.getSpecialObjects().get(SpecialObject.Inkwell) != 0)
            System.out.println(" Inkwell as a special object");
        else if(this.objectGoldCard.getSpecialObjects().get(SpecialObject.Quill) != 0)
            System.out.println(" Quill as a special object");
        else if(this.objectGoldCard.getSpecialObjects().get(SpecialObject.Manuscript) != 0)
            System.out.println(" Manuscript as a special object");

        System.out.println("\nFRONT CORNERS: ");
        for(int i=0;i<this.objectGoldCard.getFrontCorners().length;i++){
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

            if(this.objectGoldCard.getFrontCorners()[i] == null)
                System.out.println("the corner doesn't exist");
            else if(this.objectGoldCard.getFrontCorners()[i].getContentKingdom() != null){
                if(this.objectGoldCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant))
                    System.out.println(ConsoleColors.TEXT_GREEN + "PLANT" + ConsoleColors.TEXT_RESET);
                else if(this.objectGoldCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect))
                    System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT"+ ConsoleColors.TEXT_RESET);
                    //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                else if(this.objectGoldCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi))
                    System.out.println(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
                else if (this.objectGoldCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal))
                    System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL"+ ConsoleColors.TEXT_RESET);
            } else if(this.objectGoldCard.getFrontCorners()[i].getContentObject() != null){
                if (this.objectGoldCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell))
                    System.out.println("INKWELL");
                else if (this.objectGoldCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill))
                    System.out.println("QUILL");
                else if (this.objectGoldCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript))
                    System.out.println("MANUSCRIPT");
            } else {
                System.out.println("visible but empty");
            }
        }

        System.out.println("\nBACK CORNERS: ");
        //BACK: back di ogni carta ha 4 visible corners sempre
        System.out.print(ConsoleColors. TEXT_RESET + "The back of this card has the 4 visible corners and a ");
        if(this.objectGoldCard.getCardKingdom().equals(Kingdom.Plant))
            System.out.print(ConsoleColors.TEXT_GREEN + "PLANT"+ ConsoleColors.TEXT_RESET);
        else if(this.objectGoldCard.getCardKingdom().equals(Kingdom.Insect))
            System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT"+ ConsoleColors.TEXT_RESET);
            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
        else if(this.objectGoldCard.getCardKingdom().equals(Kingdom.Fungi))
            System.out.print(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
        else if (this.objectGoldCard.getCardKingdom().equals(Kingdom.Animal))
            System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL"+ ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_RESET + " resource");

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------\n");


    }


}
