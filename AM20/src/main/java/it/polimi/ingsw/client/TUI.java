package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.controller.messages.ChatMessage;
import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.objective.*;
import it.polimi.ingsw.model.cards.playable.*;
import it.polimi.ingsw.model.exceptions.InvalidAngleCoveredException;
import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.RequirementsNotRespectedException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;
import it.polimi.ingsw.model.util.XMLparser;

import java.util.*;

public class TUI implements UIManager {
    /*TODO:
      - ATTRIBUTO PER CAPIRE DI CHE ANGOLO SI TRATTA --> PER ITERARE SU ANGOLI
    */
    private ConsoleColors consoleColors = new ConsoleColors();
    private List<PlayableCard> cards;
    private List<ChatMessage> messages;
    private ArrayList<ObjectiveCard> secretObjectives;
    private PlayableCard goldTop;
    private PlayableCard resourceTop;
    private PlayableCard[] goldVisible;
    private PlayableCard[] resourceVisible;
    private PlayerInfo yourPlayerInfo;
    private HashMap<String, PlayerInfo> othersPlayerInfo;
    private ObjectiveCard[] commonObjectives;
    private StarterCard starterCard;
    private String nickname;

    public TUI(){
        this.messages = new LinkedList<>();
        this.cards = new ArrayList<>();

    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void updateCards(List<PlayableCard> cards) {
        this.cards = cards;
        viewHandCards();
    }

    @Override
    public void updateChatMessage(ChatMessage msg) {
        this.messages.add(msg);
        if(msg.getRecipient().equals(nickname))
            System.out.println("You received a message");
        viewChat();
    }

    @Override
    public void updateSecretObjectives(ArrayList<ObjectiveCard> secretObjectives) {
        if(secretObjectives!=null) {
            this.secretObjectives = secretObjectives;
            viewSecretObjective();
        }
    }

    @Override
    public void updateGoldTop(PlayableCard goldTop) {
        this.goldTop = goldTop;
        viewGoldTop();
    }

    @Override
    public void updateResourceTop(PlayableCard resourceTop) {
        this.resourceTop = resourceTop;
        viewResourceTop();
    }

    @Override
    public void updateGoldVisible(PlayableCard[] goldVisible) {
        this.goldVisible = goldVisible;
        viewGoldVisibleCards();
    }

    @Override
    public void updateResourceVisible(PlayableCard[] resourceVisible) {
        this.resourceVisible = resourceVisible;
        viewResourceVisibleCards();
    }

    @Override
    public void updateYourPlayerInfo(PlayerInfo yourPlayerInfo) {
        if(yourPlayerInfo!=null) {
            this.yourPlayerInfo = yourPlayerInfo;
            viewPlayerInfo();
        }
    }

    @Override
    public void updateOtherPlayerInfo(HashMap<String, PlayerInfo> otherPlayerInfo) {
        if(otherPlayerInfo!=null) {
            this.othersPlayerInfo = otherPlayerInfo;
            for (String nickname : otherPlayerInfo.keySet())
                viewOtherPlayerInfo(nickname);
        }
    }

    @Override
    public void updateCommonObjectives(ObjectiveCard[] commonObjectives) {
        this.commonObjectives = commonObjectives;
        viewCommonObjective();
    }

    @Override
    public void updateStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
        viewStarterCard();
    }

    @Override
    public void showResult(String result) {
        System.out.println(result);
    }

    @Override
    public void showNextTurn(String nextPlayer){
        if(nextPlayer.equals(nickname)){
            System.out.println("Is your turn");
        }
        else{
            System.out.println("Is " + nextPlayer + "'s turn");
        }
    }

    @Override
    public void showMustPick(){
        System.out.println("You have to pick a card");
    }

    @Override
    public void showError(String error){
        System.err.println(error);
    }

    //TODO ELE E TIA: si puo migliorare
    //si può migliorare
    public void viewStarterCard(){
        this.printTitle();
        this.viewCommand();
        System.out.println("\nYou have received the starter card:\n");
        System.out.println("The front of this card has " + this.starterCard.getFrontCorners().length + " visible corners: \n");

        for(int i=0;i<this.starterCard.getFrontCorners().length;i++){
            if(i==0){
                if(this.starterCard.getFrontCorners()[i] == null)
                    System.out.println("The UL corner doesn't exist");
                else if(this.starterCard.getFrontCorners()[i].getContentKingdom() != null){
                    System.out.print(ConsoleColors.TEXT_RESET + "- UL: ");
                    if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant))
                        System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                    else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect))
                        System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi))
                        System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                    else if (this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal))
                        System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                } else if(this.starterCard.getFrontCorners()[i].getContentObject() != null){
                    System.out.print(ConsoleColors.TEXT_RESET + "- UL: ");
                    if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell))
                        System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                    else if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill))
                        System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                    else if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript))
                        System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                } else {
                    System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                }
            }else if(i==1){
                if(this.starterCard.getFrontCorners()[i] == null)
                    System.out.println("The UL corner doesn't exist");
                else if(this.starterCard.getFrontCorners()[i].getContentKingdom() != null){
                    System.out.print(ConsoleColors.TEXT_RESET + "- UR: ");
                    if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant))
                        System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                    else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect))
                        System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi))
                        System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                    else if (this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal))
                        System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                } else if(this.starterCard.getFrontCorners()[i].getContentObject() != null) {
                    System.out.print(ConsoleColors.TEXT_RESET + "- UR: ");
                    if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell))
                        System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                    else if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill))
                        System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                    else if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript))
                        System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                }else {
                    System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                }
            }else if(i==2){
                if(this.starterCard.getFrontCorners()[i] == null)
                    System.out.println("The DL corner doesn't exist");
                else if(this.starterCard.getFrontCorners()[i].getContentKingdom() != null){
                    System.out.print(ConsoleColors.TEXT_RESET + "- DL: ");
                    if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant))
                        System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                    else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect))
                        System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi))
                        System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                    else if (this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal))
                        System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                } else if(this.starterCard.getFrontCorners()[i].getContentObject() != null) {
                    System.out.print(ConsoleColors.TEXT_RESET + "- DL: ");
                    if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell))
                        System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                    else if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill))
                        System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                    else if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript))
                        System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                }else {
                    System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                }
            }else {
                if(this.starterCard.getFrontCorners()[i] == null)
                    System.out.println("The DR corner doesn't exist");
                else if(this.starterCard.getFrontCorners()[i].getContentKingdom() != null){
                    System.out.print(ConsoleColors.TEXT_RESET + "- DR: ");
                    if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant))
                        System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                    else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect))
                        System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi))
                        System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                    else if (this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal))
                        System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                } else if(this.starterCard.getFrontCorners()[i].getContentObject() != null) {
                    System.out.print(ConsoleColors.TEXT_RESET + "- DR: ");
                    if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell))
                        System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                    else if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill))
                        System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                    else if (this.starterCard.getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript))
                        System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                }else {
                    System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                }
            }
        }
        System.out.println("\n");
        System.out.println(ConsoleColors.TEXT_RESET + "The back of this card has " + this.starterCard.getBackCorners().length + " visible corners: \n");
        //da qua
        for(int i=0;i<this.starterCard.getBackCorners().length;i++){
            if(i==0){
                if(this.starterCard.getBackCorners()[i] == null)
                    System.out.println("The UL corner doesn't exist");
                else if(this.starterCard.getBackCorners()[i].getContentKingdom() != null){
                    System.out.print(ConsoleColors.TEXT_RESET + "- UL: ");
                    if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Plant))
                        System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                    else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Insect))
                        System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Fungi))
                        System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                    else if (this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Animal))
                        System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                } else if(this.starterCard.getBackCorners()[i].getContentObject() != null){
                    System.out.print(ConsoleColors.TEXT_RESET + "- UL: ");
                    if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Inkwell))
                        System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                    else if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Quill))
                        System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                    else if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Manuscript))
                        System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                } else {
                    System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                }
            }else if(i==1){
                if(this.starterCard.getBackCorners()[i] == null)
                    System.out.println("The UL corner doesn't exist");
                else if(this.starterCard.getBackCorners()[i].getContentKingdom() != null){
                    System.out.print(ConsoleColors.TEXT_RESET + "- UR: ");
                    if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Plant))
                        System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                    else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Insect))
                        System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Fungi))
                        System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                    else if (this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Animal))
                        System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                } else if(this.starterCard.getBackCorners()[i].getContentObject() != null) {
                    System.out.print(ConsoleColors.TEXT_RESET + "- UR: ");
                    if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Inkwell))
                        System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                    else if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Quill))
                        System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                    else if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Manuscript))
                        System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                }else {
                    System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                }
            }else if(i==2){

                if(this.starterCard.getBackCorners()[i] == null)
                    System.out.println("The DL corner doesn't exist");
                else if(this.starterCard.getBackCorners()[i].getContentKingdom() != null){
                    System.out.print(ConsoleColors.TEXT_RESET + "- DL: ");
                    if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Plant))
                        System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                    else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Insect))
                        System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Fungi))
                        System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                    else if (this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Animal))
                        System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                } else if(this.starterCard.getBackCorners()[i].getContentObject() != null) {
                    System.out.print(ConsoleColors.TEXT_RESET + "- DL: ");
                    if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Inkwell))
                        System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                    else if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Quill))
                        System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                    else if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Manuscript))
                        System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                }else {
                    System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                }


            }else {

                if(this.starterCard.getBackCorners()[i] == null)
                    System.out.println("The DR corner doesn't exist");
                else if(this.starterCard.getBackCorners()[i].getContentKingdom() != null){
                    System.out.print(ConsoleColors.TEXT_RESET + "- DR: ");
                    if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Plant))
                        System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                    else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Insect))
                        System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Fungi))
                        System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                    else if (this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Animal))
                        System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                } else if(this.starterCard.getBackCorners()[i].getContentObject() != null) {
                    System.out.print(ConsoleColors.TEXT_RESET + "- DR: ");
                    if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Inkwell))
                        System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                    else if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Quill))
                        System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                    else if (this.starterCard.getBackCorners()[i].getContentObject().equals(SpecialObject.Manuscript))
                        System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                }else {
                    System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                }

            }
        }

        System.out.print(ConsoleColors.TEXT_RESET + "\nThe back of the card has " + this.starterCard.getResources().toArray().length + " resources in the middle: ");
        for(int i=0; i< this.starterCard.getResources().toArray().length;i++){
            if(this.starterCard.getResources().get(i).equals(Kingdom.Plant)){
                System.out.print(ConsoleColors.TEXT_GREEN + "PLANT ");
            }else if(this.starterCard.getResources().get(i).equals(Kingdom.Insect)){
                System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT ");
                //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
            }else if(this.starterCard.getResources().get(i).equals(Kingdom.Fungi)){
                System.out.print(ConsoleColors.TEXT_RED + "FUNGI ");
            }else if (this.starterCard.getResources().get(i).equals(Kingdom.Animal)) {
                System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL ");
            }
        }
        System.out.println(ConsoleColors.TEXT_RESET);
    }

    //TODO: NPASSAGGIO DELLE CARTE PER PROVARE SE VA
    public void viewHandCards(){
        //assegnazione carte iniziali x esempio --> HELP

        this.printTitle();
        System.out.println(ConsoleColors.TEXT_RESET + "These are your cards: \n");

        //scorro lista delle carte
        for(int i=0; i< this.cards.size(); i++) {
            if(i==0){
                System.out.print("The card to the left is a");

                if(this.cards.get(i) instanceof ResourceCard) {
                    System.out.print(" resource card");
                }else
                    System.out.print(" gold card");
            }

            if(i==1){
                System.out.print(ConsoleColors.TEXT_RESET + "\nThe card in the middle is a");

                if(this.cards.get(i) instanceof ResourceCard) {
                    System.out.print(" resource card");
                }else
                    System.out.print(" gold card");
            }

            if(i==2){
                System.out.print(ConsoleColors.TEXT_RESET + "\nThe card to the right is a");

                if(this.cards.get(i) instanceof ResourceCard) {
                    System.out.print(" resource card");
                }else
                    System.out.print(" gold card");
            }
            System.out.print(" of ");

            if(this.cards.get(i).getCardKingdom().equals(Kingdom.Plant))
                System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
            else if(this.cards.get(i).getCardKingdom().equals(Kingdom.Insect))
                System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                    //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
            else if(this.cards.get(i).getCardKingdom().equals(Kingdom.Fungi))
                System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
            else if (this.cards.get(i).getCardKingdom().equals(Kingdom.Animal))
                System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");

            //SE SONO CARTE GOLD
            if(this.cards.get(i) instanceof GoldCard){
                //REQUIREMENTS
                System.out.println(ConsoleColors.TEXT_RESET + "The card has the following requirements:  ");
                if(this.cards.get(i).getRequirements().get(Kingdom.Plant) != 0){
                    System.out.print(ConsoleColors.TEXT_RESET + " - " + this.cards.get(i).getRequirements().get(Kingdom.Plant));
                    System.out.println(ConsoleColors.TEXT_GREEN + " PLANT");
                }
                if(this.cards.get(i).getRequirements().get(Kingdom.Insect) != 0){
                    System.out.print(ConsoleColors.TEXT_RESET + " - " + this.cards.get(i).getRequirements().get(Kingdom.Insect));
                    System.out.println(ConsoleColors.TEXT_PURPLE + " INSECT");
                }
                if(this.cards.get(i).getRequirements().get(Kingdom.Fungi) != 0){
                    System.out.print(ConsoleColors.TEXT_RESET + " - " + this.cards.get(i).getRequirements().get(Kingdom.Fungi));
                    System.out.println(ConsoleColors.TEXT_RED + " FUNGI");
                }
                if(this.cards.get(i).getRequirements().get(Kingdom.Animal) != 0){
                    System.out.print(ConsoleColors.TEXT_RESET + " - " + this.cards.get(i).getRequirements().get(Kingdom.Animal));
                    System.out.println(ConsoleColors.TEXT_CYAN + " ANIMAL");
                }

                //TIPO DI GOLD CARD
                if(this.cards.get(i) instanceof PointsGoldCard){
                    System.out.println(ConsoleColors.TEXT_RESET + "You get" + ((PointsGoldCard) this.cards.get(i)).getPoints() + "points if you play this card correctly \n");
                }else if(this.cards.get(i) instanceof ObjectGoldCard){
                    System.out.println(ConsoleColors.TEXT_RESET +  "You get 1 point if you play this card correctly for every");
                    if(this.cards.get(i).getSpecialObjects().get(SpecialObject.Inkwell) != 0)
                        System.out.println(" Inkwell as a special object");
                    else if(this.cards.get(i).getSpecialObjects().get(SpecialObject.Quill) != 0)
                        System.out.println(" Quill as a special object");
                    else if(this.cards.get(i).getSpecialObjects().get(SpecialObject.Manuscript) != 0)
                        System.out.println(" Manuscript as a special object");
                }else if(this.cards.get(i) instanceof CornerGoldCard){
                    System.out.println(ConsoleColors.TEXT_RESET + "You get 2 points every time you cover another card's angle with this card ");
                }
            }

            System.out.println(ConsoleColors.TEXT_RESET + "\nThe front of this card has " + this.cards.get(i).getFrontCorners().length + " visible corners: ");
            //scorro angoli FRONT
            for(int j=0; j < this.cards.get(i).getFrontCorners().length; j++){
                if(j==0){
                    if(this.cards.get(i).getFrontCorners()[j] == null)
                        System.out.println("The UL corner doesn't exist");
                    //se ha una risorsa
                    else if(this.cards.get(i).getFrontCorners()[j].getContentKingdom() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- UL: ");
                        if(this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Plant))
                            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                        else if(this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Insect))
                            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        else if(this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Fungi))
                            System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                        else if (this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Animal))
                            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                    //se ha un oggetto
                    } else if(this.cards.get(i).getFrontCorners()[j].getContentObject() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- UL: ");
                        if (this.cards.get(i).getFrontCorners()[j].getContentObject().equals(SpecialObject.Inkwell))
                            System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                        else if (this.cards.get(i).getFrontCorners()[j].getContentObject().equals(SpecialObject.Quill))
                            System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                        else if (this.cards.get(i).getFrontCorners()[j].getContentObject().equals(SpecialObject.Manuscript))
                            System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                    //se non ha nulla
                    } else {
                        System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                    }

                }else if(j==1){
                    if(this.cards.get(i).getFrontCorners()[j] == null)
                        System.out.println("The UR corner doesn't exist");
                    else if(this.cards.get(i).getFrontCorners()[j].getContentKingdom() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- UR: ");
                        if(this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Plant))
                            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                        else if(this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Insect))
                            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        else if(this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Fungi))
                            System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                        else if (this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Animal))
                            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                    } else if(this.cards.get(i).getFrontCorners()[j].getContentObject() != null) {
                        System.out.print(ConsoleColors.TEXT_RESET + "- UR: ");
                        if (this.cards.get(i).getFrontCorners()[j].getContentObject().equals(SpecialObject.Inkwell))
                            System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                        else if (this.cards.get(i).getFrontCorners()[j].getContentObject().equals(SpecialObject.Quill))
                            System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                        else if (this.cards.get(i).getFrontCorners()[j].getContentObject().equals(SpecialObject.Manuscript))
                            System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                    }else {
                        System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                    }

                }else if(j==2){
                    if(this.cards.get(i).getFrontCorners()[j] == null)
                        System.out.println("The DL corner doesn't exist");
                    else if(this.cards.get(i).getFrontCorners()[j].getContentKingdom() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- DL: ");
                        if(this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Plant))
                            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                        else if(this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Insect))
                            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        else if(this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Fungi))
                            System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                        else if (this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Animal))
                            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                    } else if(this.cards.get(i).getFrontCorners()[j].getContentObject() != null) {
                        System.out.print(ConsoleColors.TEXT_RESET + "- DL: ");
                        if (this.cards.get(i).getFrontCorners()[j].getContentObject().equals(SpecialObject.Inkwell))
                            System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                        else if (this.cards.get(i).getFrontCorners()[j].getContentObject().equals(SpecialObject.Quill))
                            System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                        else if (this.cards.get(i).getFrontCorners()[j].getContentObject().equals(SpecialObject.Manuscript))
                            System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                    }else {
                        System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                    }


                }else {
                    if(this.cards.get(i).getFrontCorners()[j] == null)
                        System.out.println("The DR corner doesn't exist");
                    else if(this.cards.get(i).getFrontCorners()[j].getContentKingdom() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- DR: ");
                        if(this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Plant))
                            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                        else if(this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Insect))
                            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        else if(this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Fungi))
                            System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                        else if (this.cards.get(i).getFrontCorners()[j].getContentKingdom().equals(Kingdom.Animal))
                            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                    } else if(this.cards.get(i).getFrontCorners()[j].getContentObject() != null) {
                        System.out.print(ConsoleColors.TEXT_RESET + "- DR: ");
                        if (this.cards.get(i).getFrontCorners()[j].getContentObject().equals(SpecialObject.Inkwell))
                            System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                        else if (this.cards.get(i).getFrontCorners()[j].getContentObject().equals(SpecialObject.Quill))
                            System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                        else if (this.cards.get(i).getFrontCorners()[j].getContentObject().equals(SpecialObject.Manuscript))
                            System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                    }else {
                        System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                    }
                }
            }

            //BACK
            //ps il back di ogni carta ha 4 visible corners sempre
            System.out.print(ConsoleColors. TEXT_RESET + "\nThe back of this card has the 4 visible corners and a ");
            if(this.cards.get(i).getCardKingdom().equals(Kingdom.Plant))
                System.out.print(ConsoleColors.TEXT_GREEN + "PLANT");
            else if(this.cards.get(i).getCardKingdom().equals(Kingdom.Insect))
                System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT");
                //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
            else if(this.cards.get(i).getCardKingdom().equals(Kingdom.Fungi))
                System.out.print(ConsoleColors.TEXT_RED + "FUNGI");
            else if (this.cards.get(i).getCardKingdom().equals(Kingdom.Animal))
                System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL");
            System.out.println(ConsoleColors.TEXT_RESET + " resource");
        }
    }

    public void viewCommonObjective(){
        System.out.println("These are the common objectives: \n");
        //scorro array dei common objectives
        for(int i=0; i<this.commonObjectives.length; i++){
            if(this.commonObjectives[i] instanceof DiagonalConfigurationObjectiveCard){
                System.out.println("- DIAGONAL CONFIGURATION OBJECTIVE: ");
                System.out.print("You need to create a diagonal configuration, with ");

                if(((DiagonalConfigurationObjectiveCard) this.commonObjectives[i]).getKingdom().equals(Kingdom.Fungi))
                    System.out.print(ConsoleColors.TEXT_RED + "FUNGI");
                else if(((DiagonalConfigurationObjectiveCard) this.commonObjectives[i]).getKingdom().equals(Kingdom.Insect))
                    System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT");
                else if(((DiagonalConfigurationObjectiveCard) this.commonObjectives[i]).getKingdom().equals(Kingdom.Plant))
                    System.out.print(ConsoleColors.TEXT_GREEN + "PLANT");
                else
                    System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                System.out.print(ConsoleColors.TEXT_RESET + " resource cards only, starting by covering the ");

                if(((DiagonalConfigurationObjectiveCard) this.commonObjectives[i]).getCoveredCorner() == 0)
                    System.out.println("UL first");
                else if(((DiagonalConfigurationObjectiveCard) this.commonObjectives[i]).getCoveredCorner() == 1)
                    System.out.println("UR first");
                else if(((DiagonalConfigurationObjectiveCard) this.commonObjectives[i]).getCoveredCorner() == 2)
                    System.out.println("DL first");
                else if(((DiagonalConfigurationObjectiveCard) this.commonObjectives[i]).getCoveredCorner() == 3)
                    System.out.println("DR first");
                System.out.println("You get 2 points every time you create this kind of configuration\n ");
            }else if(this.commonObjectives[i] instanceof PairOfObjectsObjectiveCard){
                System.out.println("- PAIR OF OBJECTS OBJECTIVES: ");
                System.out.println("You get 2 points every time you collect two " +
                                   ((PairOfObjectsObjectiveCard) this.commonObjectives[i]).getSpecialObject() +" objects \n");
            }else if(this.commonObjectives[i] instanceof TrioOfObjectsObjectiveCard){
                System.out.println("- TRIO OF OBJECTS OBJECTIVE: ");
                System.out.println("You get 2 points every time you collect an Inkwell, a Manuscript and a Quill \n");
            }else if(this.commonObjectives[i] instanceof TrioOfResourcesObjectiveCard){
                System.out.println("- TRIO OF RESOURCES OBJECTIVE: ");
                System.out.print("You get 2 points every time you collect three ");
                if(((TrioOfResourcesObjectiveCard)this.commonObjectives[i]).getResourcesKingdom().equals(Kingdom.Fungi))
                    System.out.print(ConsoleColors.TEXT_RED + "FUNGI");
                else if(((TrioOfResourcesObjectiveCard) this.commonObjectives[i]).getResourcesKingdom().equals(Kingdom.Insect))
                    System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT");
                else if(((TrioOfResourcesObjectiveCard) this.commonObjectives[i]).getResourcesKingdom().equals(Kingdom.Plant))
                    System.out.print(ConsoleColors.TEXT_GREEN + "PLANT");
                else
                    System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL");
                System.out.println(ConsoleColors.TEXT_RESET + " resource cards \n");

            }else if(this.commonObjectives[i] instanceof VerticalConfigurationObjectiveCard){
                System.out.println("- VERTICAL CONFIGURATION OBJECTIVE: ");
                System.out.print("You need to create a vertical configuration, with a vertical occurrence of a ");
                if(((VerticalConfigurationObjectiveCard) this.commonObjectives[i]).getKingdom2().equals(Kingdom.Fungi))
                    System.out.print(ConsoleColors.TEXT_RED + "FUNGI");
                else if(((VerticalConfigurationObjectiveCard) this.commonObjectives[i]).getKingdom2().equals(Kingdom.Insect))
                    System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT");
                else if(((VerticalConfigurationObjectiveCard) this.commonObjectives[i]).getKingdom2().equals(Kingdom.Plant))
                    System.out.print(ConsoleColors.TEXT_GREEN + "PLANT");
                else
                    System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL");

                System.out.print(ConsoleColors.TEXT_RESET + " card.");

                System.out.print(" This objective also needs of a ");

                if(((VerticalConfigurationObjectiveCard) this.commonObjectives[i]).getKingdom1().equals(Kingdom.Fungi))
                    System.out.print(ConsoleColors.TEXT_RED + "FUNGI");
                else if(((VerticalConfigurationObjectiveCard) this.commonObjectives[i]).getKingdom1().equals(Kingdom.Insect))
                    System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT");
                else if(((VerticalConfigurationObjectiveCard) this.commonObjectives[i]).getKingdom1().equals(Kingdom.Plant))
                    System.out.print(ConsoleColors.TEXT_GREEN + "PLANT");
                else
                    System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL");

                System.out.print(ConsoleColors.TEXT_RESET+ " card that should cover the ");

                if(((VerticalConfigurationObjectiveCard) this.commonObjectives[i]).getCoveredCorner() == 0)
                    System.out.println("DR angle of the bottom " +((VerticalConfigurationObjectiveCard) this.commonObjectives[i]).getKingdom2() + " card." );
                else if(((VerticalConfigurationObjectiveCard) this.commonObjectives[i]).getCoveredCorner() == 1)
                    System.out.println("DL angle of the bottom " + ((VerticalConfigurationObjectiveCard) this.commonObjectives[i]).getKingdom2() + " card.");
                else if(((VerticalConfigurationObjectiveCard) this.commonObjectives[i]).getCoveredCorner() == 2)
                    System.out.println("UR angle of the top "+ ((VerticalConfigurationObjectiveCard) this.commonObjectives[i]).getKingdom2() + " card." );
                else
                    System.out.println("UL angle of the top "+ ((VerticalConfigurationObjectiveCard) this.commonObjectives[i]).getKingdom2() + " card.");
                System.out.println("You get 3 points every time you create this kind of configuration \n");
            }
        }
    }

    //TODO ELE: GUARDARE COME SCRIVERE BENE VERTICAL E DIAGONAL CONFIG
    public void viewSecretObjective(){
        System.out.println("This is your secret objectives: \n");
        //scorro array dei common objectives
        for(int i=0; i<this.secretObjectives.size(); i++){
            if(this.secretObjectives.get(i) instanceof DiagonalConfigurationObjectiveCard){
                System.out.println("- DIAGONAL CONFIGURATION OBJECTIVE: ");
                System.out.print("You need to create a diagonal configuration, with ");

                if(((DiagonalConfigurationObjectiveCard) this.secretObjectives.get(i)).getKingdom().equals(Kingdom.Fungi))
                    System.out.print(ConsoleColors.TEXT_RED + "FUNGI");
                else if(((DiagonalConfigurationObjectiveCard) this.secretObjectives.get(i)).getKingdom().equals(Kingdom.Insect))
                    System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT");
                else if(((DiagonalConfigurationObjectiveCard) this.secretObjectives.get(i)).getKingdom().equals(Kingdom.Plant))
                    System.out.print(ConsoleColors.TEXT_GREEN + "PLANT");
                else
                    System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                System.out.print(ConsoleColors.TEXT_RESET + " resource cards only, starting by covering the ");

                if(((DiagonalConfigurationObjectiveCard) this.secretObjectives.get(i)).getCoveredCorner() == 0)
                    System.out.println("UL first");
                else if(((DiagonalConfigurationObjectiveCard) this.secretObjectives.get(i)).getCoveredCorner() == 1)
                    System.out.println("UR first");
                else if(((DiagonalConfigurationObjectiveCard) this.secretObjectives.get(i)).getCoveredCorner() == 2)
                    System.out.println("DL first");
                else if(((DiagonalConfigurationObjectiveCard) this.secretObjectives.get(i)).getCoveredCorner() == 3)
                    System.out.println("DR first");
                System.out.println("You get 2 points every time you create this kind of configuration\n ");
            }else if(this.secretObjectives.get(i) instanceof PairOfObjectsObjectiveCard){
                System.out.println("- PAIR OF OBJECTS OBJECTIVES: ");
                System.out.println("You get 2 points every time you collect two " +
                        ((PairOfObjectsObjectiveCard) this.secretObjectives.get(i)).getSpecialObject() +" objects \n");
            }else if(this.secretObjectives.get(i) instanceof TrioOfObjectsObjectiveCard){
                System.out.println("- TRIO OF OBJECTS OBJECTIVE: ");
                System.out.println("You get 2 points every time you collect an Inkwell, a Manuscript and a Quill \n");
            }else if(this.secretObjectives.get(i) instanceof TrioOfResourcesObjectiveCard){
                System.out.println("- TRIO OF RESOURCES OBJECTIVE: ");
                System.out.print("You get 2 points every time you collect three ");
                if(((TrioOfResourcesObjectiveCard) this.secretObjectives.get(i)).getResourcesKingdom().equals(Kingdom.Fungi))
                    System.out.print(ConsoleColors.TEXT_RED + "FUNGI");
                else if(((TrioOfResourcesObjectiveCard)this.secretObjectives.get(i)).getResourcesKingdom().equals(Kingdom.Insect))
                    System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT");
                else if(((TrioOfResourcesObjectiveCard) this.secretObjectives.get(i)).getResourcesKingdom().equals(Kingdom.Plant))
                    System.out.print(ConsoleColors.TEXT_GREEN + "PLANT");
                else
                    System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL");
                System.out.println(ConsoleColors.TEXT_RESET + " resource cards \n");

            }else if(this.secretObjectives.get(i) instanceof VerticalConfigurationObjectiveCard){
                System.out.println("- VERTICAL CONFIGURATION OBJECTIVE: ");
                System.out.print("You need to create a vertical configuration, with a vertical occurrence of a ");
                if(((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i)).getKingdom2().equals(Kingdom.Fungi))
                    System.out.print(ConsoleColors.TEXT_RED + "FUNGI");
                else if(((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i)).getKingdom2().equals(Kingdom.Insect))
                    System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT");
                else if(((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i)).getKingdom2().equals(Kingdom.Plant))
                    System.out.print(ConsoleColors.TEXT_GREEN + "PLANT");
                else
                    System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL");

                System.out.print(ConsoleColors.TEXT_RESET + " card.");

                System.out.print(" This objective also needs of a ");

                if(((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i)).getKingdom1().equals(Kingdom.Fungi))
                    System.out.print(ConsoleColors.TEXT_RED + "FUNGI");
                else if(((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i)).getKingdom1().equals(Kingdom.Insect))
                    System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT");
                else if(((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i)).getKingdom1().equals(Kingdom.Plant))
                    System.out.print(ConsoleColors.TEXT_GREEN + "PLANT");
                else
                    System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL");

                System.out.print(ConsoleColors.TEXT_RESET+ " card that should cover the ");

                if(((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i)).getCoveredCorner() == 0)
                    System.out.println("DR angle of the top " +((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i)).getKingdom2() + " card." );
                else if(((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i)).getCoveredCorner() == 1)
                    System.out.println("DL angle of the top " + ((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i)).getKingdom2() + " card.");
                else if(((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i)).getCoveredCorner() == 2)
                    System.out.println("UR angle of the bottom "+ ((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i)).getKingdom2() + " card." );
                else
                    System.out.println("UL angle of the bottom "+ ((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i)).getKingdom2() + " card.");
                System.out.println("You get 3 points every time you create this kind of configuration \n");
            }
        }
    }

    public void viewGoldTop(){
        System.out.print("The card that's now on top of the gold deck is a ");
        //KINGDOM
        if(this.goldTop.getCardKingdom().equals(Kingdom.Fungi))
            System.out.print(ConsoleColors.TEXT_RED + "FUNGI");
        else if(this.goldTop.getCardKingdom().equals(Kingdom.Insect))
            System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT");
        else if(this.goldTop.getCardKingdom().equals(Kingdom.Plant))
            System.out.print(ConsoleColors.TEXT_GREEN + "PLANT");
        else
            System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL");
        System.out.println(ConsoleColors.TEXT_RESET + " card \n");
    }

    public void viewResourceTop(){
        System.out.print("The card that's now on top of the resource deck is a ");
        if(this.resourceTop.getCardKingdom().equals(Kingdom.Fungi))
            System.out.print(ConsoleColors.TEXT_RED + "FUNGI");
        else if(this.resourceTop.getCardKingdom().equals(Kingdom.Insect))
            System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT");
        else if(this.resourceTop.getCardKingdom().equals(Kingdom.Plant))
            System.out.print(ConsoleColors.TEXT_GREEN + "PLANT");
        else
            System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL");
        System.out.println(ConsoleColors.TEXT_RESET + " card \n");
    }

    public void viewGoldVisibleCards(){
        //List<PlayableCard> listTest = new ArrayList<>();
        System.out.println("The gold cards that are now visible on the table are the following: \n");
        for(int i=0; i < this.goldVisible.length; i++) {
            //KINGDOM GENERICO
            if (i == 0) {
                System.out.print("First card: ");
            } else
                System.out.print("Second card: ");

            if(this.goldVisible[i].getCardKingdom().equals(Kingdom.Fungi))
                System.out.print(ConsoleColors.TEXT_RED + "FUNGI");
            else if(this.goldVisible[i].getCardKingdom().equals(Kingdom.Insect))
                System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT");
            else if(this.goldVisible[i].getCardKingdom().equals(Kingdom.Plant))
                System.out.print(ConsoleColors.TEXT_GREEN + "PLANT");
            else
                System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL");

            System.out.println(ConsoleColors.TEXT_RESET + " card \n");

            //ELENCO DELLE RISORSE IN OGNI CORNER:
            System.out.println("This card has " + this.goldVisible[i].getFrontCorners().length + " visible corners: ");
            //scorro angoli FRONT
            for(int j=0; j < this.goldVisible[i].getFrontCorners().length; j++) {
                if(j==0){
                    if(this.goldVisible[i].getFrontCorners()[j] == null)
                        System.out.println("The UL corner doesn't exist");
                    else if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- UL: ");
                        if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Plant))
                            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                        else if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Insect))
                            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        else if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Fungi))
                            System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                        else if (this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Animal))
                            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                    } else if(this.goldVisible[i].getFrontCorners()[j].getContentObject() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- UL: ");
                        if (this.goldVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Inkwell))
                            System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                        else if (this.goldVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Quill))
                            System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                        else if (this.goldVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Manuscript))
                            System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                    } else {
                        System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                    }
                }else if(j==1){
                    if(this.goldVisible[i].getFrontCorners()[j] == null)
                        System.out.println("The UL corner doesn't exist");
                    else if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- UR: ");
                        if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Plant))
                            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                        else if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Insect))
                            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        else if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Fungi))
                            System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                        else if (this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Animal))
                            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                    } else if(this.goldVisible[i].getFrontCorners()[j].getContentObject() != null) {
                        System.out.print(ConsoleColors.TEXT_RESET + "- UR: ");
                        if (this.goldVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Inkwell))
                            System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                        else if (this.goldVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Quill))
                            System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                        else if (this.goldVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Manuscript))
                            System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                    }else {
                        System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                    }
                }else if(j==2){
                    if(this.goldVisible[i].getFrontCorners()[j] == null)
                        System.out.println("The DL corner doesn't exist");
                    else if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- DL: ");
                        if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Plant))
                            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                        else if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Insect))
                            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        else if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Fungi))
                            System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                        else if (this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Animal))
                            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                    } else if(this.goldVisible[i].getFrontCorners()[j].getContentObject() != null) {
                        System.out.print(ConsoleColors.TEXT_RESET + "- DL: ");
                        if (this.goldVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Inkwell))
                            System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                        else if (this.goldVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Quill))
                            System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                        else if (this.goldVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Manuscript))
                            System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                    }else {
                        System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                    }
                }else {
                    if(this.goldVisible[i].getFrontCorners()[j] == null)
                        System.out.println("The DR corner doesn't exist");
                    else if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- DR: ");
                        if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Plant))
                            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                        else if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Insect))
                            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        else if(this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Fungi))
                            System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                        else if (this.goldVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Animal))
                            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                    } else if(this.goldVisible[i].getFrontCorners()[j].getContentObject() != null) {
                        System.out.print(ConsoleColors.TEXT_RESET + "- DR: ");
                        if (this.goldVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Inkwell))
                            System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                        else if (this.goldVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Quill))
                            System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                        else if (this.goldVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Manuscript))
                            System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                    }else {
                        System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                    }
                }
            }
            System.out.println("\n");
        }
    }

    public void viewResourceVisibleCards(){
        System.out.println("The resource cards that are now visible on the table are the following \n");
        //List<PlayableCard> listTest = new ArrayList<>();
        for(int i=0; i < this.resourceVisible.length; i++) {
            //KINGDOM GENERICO
            if (i == 0) {
                System.out.print(ConsoleColors.TEXT_RESET + "First card: ");
            } else
                System.out.print(ConsoleColors.TEXT_RESET +  "Second card: ");
            if(this.resourceVisible[i].getCardKingdom().equals(Kingdom.Fungi))
                System.out.print(ConsoleColors.TEXT_RED + "FUNGI");
            else if(this.resourceVisible[i].getCardKingdom().equals(Kingdom.Insect))
                System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT");
            else if(this.resourceVisible[i].getCardKingdom().equals(Kingdom.Plant))
                System.out.print(ConsoleColors.TEXT_GREEN + "PLANT");
            else
                System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL");
            System.out.println(ConsoleColors.TEXT_RESET + " card \n");

            //ELENCO DELLE RISORSE IN OGNI CORNER:
            System.out.println("This card has " + this.resourceVisible[i].getFrontCorners().length + " visible corners: ");
            //scorro angoli FRONT
            for(int j=0; j < this.resourceVisible[i].getFrontCorners().length; j++) {
                if(j==0){
                    if(this.resourceVisible[i].getFrontCorners()[j] == null)
                        System.out.println("The UL corner doesn't exist");
                    else if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- UL: ");
                        if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Plant))
                            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                        else if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Insect))
                            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        else if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Fungi))
                            System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                        else if (this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Animal))
                            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                    } else if(this.resourceVisible[i].getFrontCorners()[j].getContentObject() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- UL: ");
                        if (this.resourceVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Inkwell))
                            System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                        else if (this.resourceVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Quill))
                            System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                        else if (this.resourceVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Manuscript))
                            System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                    } else {
                        System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                    }
                }else if(j==1){
                    if(this.resourceVisible[i].getFrontCorners()[j] == null)
                        System.out.println("The UL corner doesn't exist");
                    else if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- UR: ");
                        if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Plant))
                            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                        else if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Insect))
                            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        else if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Fungi))
                            System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                        else if (this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Animal))
                            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                    } else if(this.resourceVisible[i].getFrontCorners()[j].getContentObject() != null) {
                        System.out.print(ConsoleColors.TEXT_RESET + "- UR: ");
                        if (this.resourceVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Inkwell))
                            System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                        else if (this.resourceVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Quill))
                            System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                        else if (this.resourceVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Manuscript))
                            System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                    }else {
                        System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                    }
                }else if(j==2){
                    if(this.resourceVisible[i].getFrontCorners()[j] == null)
                        System.out.println("The DL corner doesn't exist");
                    else if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- DL: ");
                        if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Plant))
                            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                        else if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Insect))
                            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        else if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Fungi))
                            System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                        else if (this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Animal))
                            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                    } else if(this.resourceVisible[i].getFrontCorners()[j].getContentObject() != null) {
                        System.out.print(ConsoleColors.TEXT_RESET + "- DL: ");
                        if (this.resourceVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Inkwell))
                            System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                        else if (this.resourceVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Quill))
                            System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                        else if (this.resourceVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Manuscript))
                            System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                    }else {
                        System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                    }
                }else {
                    if(this.resourceVisible[i].getFrontCorners()[j] == null)
                        System.out.println("The DR corner doesn't exist");
                    else if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom() != null){
                        System.out.print(ConsoleColors.TEXT_RESET + "- DR: ");
                        if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Plant))
                            System.out.println(ConsoleColors.TEXT_GREEN + "PLANT");
                        else if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Insect))
                            System.out.println(ConsoleColors.TEXT_PURPLE + "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        else if(this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Fungi))
                            System.out.println(ConsoleColors.TEXT_RED + "FUNGI");
                        else if (this.resourceVisible[i].getFrontCorners()[j].getContentKingdom().equals(Kingdom.Animal))
                            System.out.println(ConsoleColors.TEXT_CYAN + "ANIMAL");
                    } else if(this.resourceVisible[i].getFrontCorners()[j].getContentObject() != null) {
                        System.out.print(ConsoleColors.TEXT_RESET + "- DR: ");
                        if (this.resourceVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Inkwell))
                            System.out.println(ConsoleColors.TEXT_RESET + "INKWELL");
                        else if (this.resourceVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Quill))
                            System.out.println(ConsoleColors.TEXT_RESET + "QUILL");
                        else if (this.resourceVisible[i].getFrontCorners()[j].getContentObject().equals(SpecialObject.Manuscript))
                            System.out.println(ConsoleColors.TEXT_RESET + "MANUSCRIPT");
                    }else {
                        System.out.println(ConsoleColors.TEXT_RESET + "The corner is visible but it doesn't have any resource or object");
                    }
                }
            }
            System.out.println("\n");
        }
    }

    public void viewPlayerInfo(){
        this.printTitle();
        System.out.println("These are your inforamtion");
        System.out.println("You have done "+ this.yourPlayerInfo.getScore());

        //TODO
        //System.out.println("The common objective "); oppure chaiamata al metodo viewCommonObjective
        System.out.println("You have: ");
        System.out.print("- ");
        System.out.print(ConsoleColors.TEXT_GREEN +"PLANT: ");
        System.out.println(ConsoleColors.TEXT_RESET + this.yourPlayerInfo.getStats().getNumberOfResources(Kingdom.Plant));
        System.out.print(ConsoleColors.TEXT_RESET + "- ");
        System.out.print(ConsoleColors.TEXT_PURPLE +"INSECT: ");
        System.out.println(ConsoleColors.TEXT_RESET + this.yourPlayerInfo.getStats().getNumberOfResources(Kingdom.Insect));
        System.out.print(ConsoleColors.TEXT_RESET + "- ");
        System.out.print(ConsoleColors.TEXT_RED +"FUNGI: ");
        System.out.println(ConsoleColors.TEXT_RESET + this.yourPlayerInfo.getStats().getNumberOfResources(Kingdom.Fungi));
        System.out.print(ConsoleColors.TEXT_RESET + "- ");
        System.out.print(ConsoleColors.TEXT_CYAN +"ANIMAL: ");
        System.out.println(ConsoleColors.TEXT_RESET + this.yourPlayerInfo.getStats().getNumberOfResources(Kingdom.Animal));
        System.out.println("- QUILL: "+ this.yourPlayerInfo.getStats().getNumberOfObjects(SpecialObject.Quill));
        System.out.println("- INKWELL: "+ this.yourPlayerInfo.getStats().getNumberOfObjects(SpecialObject.Inkwell));
        System.out.println("- MANUSCRIPT: "+ this.yourPlayerInfo.getStats().getNumberOfObjects(SpecialObject.Manuscript));

        System.out.println("The player has the follwoing board:");
        System.out.println(this.yourPlayerInfo.getMap().toString());
    }

    public void viewOtherPlayerInfo(String username){
        this.printTitle();
        System.out.println("The following information are the one of " + username+" game");
        System.out.println(username + " has done "+ this.othersPlayerInfo.get(username).getScore());

        //TODO
        //System.out.println("The common objective "); oppure chaiamata al metodo viewCommonObjective
        System.out.println(username + " has:");
        System.out.print("- ");
        System.out.print(ConsoleColors.TEXT_GREEN +"PLANT: ");
        System.out.println(ConsoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Plant));
        System.out.print(ConsoleColors.TEXT_RESET + "- ");
        System.out.print(ConsoleColors.TEXT_PURPLE +"INSECT: ");
        System.out.println(ConsoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Insect));
        System.out.print(ConsoleColors.TEXT_RESET + "- ");
        System.out.print(ConsoleColors.TEXT_RED +"FUNGI: ");
        System.out.println(ConsoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Fungi));
        System.out.print(ConsoleColors.TEXT_RESET + "- ");
        System.out.print(ConsoleColors.TEXT_CYAN +"ANIMAL: ");
        System.out.println(ConsoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Animal));
        System.out.println("- QUILL: "+ this.othersPlayerInfo.get(username).getStats().getNumberOfObjects(SpecialObject.Quill));
        System.out.println("- INKWELL: "+ this.othersPlayerInfo.get(username).getStats().getNumberOfObjects(SpecialObject.Inkwell));
        System.out.println("- MANUSCRIPT: "+ this.othersPlayerInfo.get(username).getStats().getNumberOfObjects(SpecialObject.Manuscript));

        System.out.println("The player has the follwoing board:");
        System.out.println(this.othersPlayerInfo.get(username).getMap().toString());
    }

    public void viewPlacement(){
        System.out.println("The placement has the following order: ");
        System.out.println("- " + this.nickname + " has " + this.yourPlayerInfo.getScore());
        for(String s : othersPlayerInfo.keySet()){
            System.out.println("- " + s + " has " + this.othersPlayerInfo.get(s));
        }
    }

    public void viewChat(){
        System.out.println("CHAT");
        for(ChatMessage m : messages){
            if(m.getSender().equals(nickname))
                System.out.println("[to: "+m.getRecipient()+"] " + m.getMessage());
            else if(m.getRecipient()==null)
                System.out.println("[to: everyone] " + m.getMessage());
            else if(m.getRecipient().equals(nickname))
                System.out.println("[from: "+m.getSender()+"] " + m.getMessage());
        }
    }
    public void viewErrorCommand(){

    }

    public void printTitle(){
        System.out.println("   ____          _             _   _       _                   _ _     ");
        System.out.println("  / ___|___   __| | _____  __ | \\ | | __ _| |_ _   _ _ __ __ _| (_)___ ");
        System.out.println(" | |   / _ \\ / _` |/ _ \\ \\/ / |  \\| |/ _` | __| | | | '__/ _` | | / __|");
        System.out.println(" | |__| (_) | (_| |  __/>  <  | |\\  | (_| | |_| |_| | | | (_| | | \\__ \\");
        System.out.println("  \\____\\___/ \\__,_|\\___/_/\\_\\ |_| \\_|\\__,_|\\__|\\__,_|_|  \\__,_|_|_|___/");
        System.out.println("                                                                       ");
        System.out.println("\n");
    }

    public void clearTerminal(int linesToPreserve) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_CLEAR_SCREEN = "\u001B[2J";
        String ANSI_MOVE_CURSOR_UP = "\u001B[%dA"; // Move cursor up by specified number of lines
        // Move the cursor up by the specified number of lines
        System.out.print(String.format(ANSI_MOVE_CURSOR_UP, linesToPreserve));
        // Clear the screen
        System.out.print(ANSI_CLEAR_SCREEN);
        // Move the cursor to the beginning (optional)
        System.out.print("\u001B[H");
        System.out.flush();
    }

    public static void waitSeconds(int seconds) {
        long startTime = System.currentTimeMillis();
        long targetTime = startTime + seconds * 1000L;

        while (System.currentTimeMillis() < targetTime) {
            // Loop until the current time exceeds the target time
        }
    }

    public void viewCommand( ){
        System.out.println("The following lines explain which are the parameters for every action: \n");
        System.out.println("Reminder: ypu don't need to type the + but just a space between parameters");
        System.out.println("/join + username");
        System.out.println("/newGame + username +  numPlayers");
        System.out.println("/chooseObjective +  index  ");
        System.out.println("/chooseStarterSide + side (0 for back / 1 for front)");
        System.out.println("/pickCardDeck +  deck (0 for gold deck / 1 for resource deck)");
        System.out.println("/pickCardVisible +  deck (0 for gold deck / 1 for resource deck) +  index  (0 for the left card and 1 for the right one)");
        System.out.println("/playCard + index (index of the card you want to play) +  angle (angel of the card you want to cover: 0 for UL / 1 for UR / 2 for DL /3  for DR)+  targetIDcard (ID of the card you want to cover) + side (0 for back / 1 for front)");
        System.out.println("/chat + broadCast + message ");
        System.out.println("/chat + username (username of the reciever) + message");
        System.out.println("/myPlayerInfo (to visualize your info)");
        System.out.println("/playerInfo + username (usarname of the player you want to view)");
        System.out.println("/placement (view the placement of the game)");
        System.out.println("/viewDeck + index (0 for the resource deck and 1 for gold deck)");
        System.out.println("/viewCommonObjective");
        System.out.println("/viewSecretObjective");
        System.out.println("/viewStarterCard");
        System.out.println("/viewResourceVisibile");
        System.out.println("/viewGoldVisible");
        System.out.println("/viewChat");
        System.out.println("/viewHand");
    }

    public static void main(String[] args) throws InterruptedException, TargetNotPresentException, InvalidPositionException, RequirementsNotRespectedException, InvalidAngleCoveredException {
        TUI myTui = new TUI();
        //myTui.printTitle();

        //myTui.viewCommand();
        //myTui.waitSeconds(5);
        //myTui.clearTerminal(10);
        //myTui.viewStarterCard();

        //TESTATE DI SICURO:
        //myTui.viewHandCards();
        //myTui.viewStarterCard();
        //myTui.viewCommonObjective();
        //myTui.viewSecretObjective();
        //myTui.viewGoldTop();
        //myTui.viewResourceTop();
        //myTui.viewGoldVisibleCards();
        //myTui.viewResourceVisibleCards();
        ;
        //myTui.viewStarterCard();

//        PlayerTable table = new PlayerTable();
//        table.insertStarterCard(PlayableCard.FRONT, myTui.getExampleStarterCard());
//        table.insertCard(myTui.getExampleResourceCard("R15"), Corner.UL, "S85", PlayableCard.BACK);
//
//        CardPrinter.printMap(table.getMap());
    }
}
