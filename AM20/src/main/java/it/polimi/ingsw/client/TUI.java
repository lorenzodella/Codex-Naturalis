package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.objective.*;
import it.polimi.ingsw.model.cards.playable.*;
import it.polimi.ingsw.model.util.XMLparser;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TUI implements UIManager {

    private ConsoleColors consoleColors;
    private List<PlayableCard> cards;
    private HashMap<String, List<String>> mapMsg;
    private ObjectiveCard[] secretObjectives;
    private PlayableCard goldTop;
    private PlayableCard resourceTop;
    private PlayableCard[] goldVisible;
    private PlayableCard[] resourceVisible;
    private PlayerInfo yourPlayerInfo;
    private HashMap<String, PlayerInfo> othersPlayerInfo;
    private ObjectiveCard[] commonObjectives;
    private StarterCard starterCard;
    private String nickname;
    private List<String> listOtherPlayer;

    public TUI(){
        this.mapMsg = new HashMap<>();
        this.consoleColors = new ConsoleColors();
        this.listOtherPlayer = new ArrayList<>();
        this.cards = new ArrayList<>();
    }


    @Override
    public void updateCards(List<PlayableCard> cards) {
        this.cards = cards;
    }

    @Override
    public void updateChatMessage(String sender, String message) {
        this.mapMsg.get(sender).add(message);
    }

    @Override
    public void updateSecretObjectives(ObjectiveCard[] secretObjectives) {
        this.secretObjectives = secretObjectives;
    }

    @Override
    public void updateGoldTop(PlayableCard goldTop) {
        this.goldTop = goldTop;
    }

    @Override
    public void updateResourceTop(PlayableCard resourceTop) {
        this.resourceTop = resourceTop;
    }

    @Override
    public void updateGoldVisible(PlayableCard[] goldVisible) {
        this.goldVisible = goldVisible;
    }

    @Override
    public void updateResourceVisible(PlayableCard[] resourceVisible) {
        this.resourceVisible = resourceVisible;
    }

    @Override
    public void updateYourPlayerInfo(PlayerInfo yourPlayerInfo) {
        this.yourPlayerInfo = yourPlayerInfo;
    }

    @Override
    public void updateOtherPlayerInfo(HashMap<String, PlayerInfo> otherPlayerInfo) {
        this.othersPlayerInfo = otherPlayerInfo;
        this.listOtherPlayer.addAll(otherPlayerInfo.keySet());
    }

    @Override
    public void updateCommonObjectives(ObjectiveCard[] commonObjectives) {
        this.commonObjectives = commonObjectives;
    }

    @Override
    public void updateStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
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
        this.starterCard = getExampleStarterCard();
        this.printTitle();
        this.viewCommand();
        System.out.println("You have received the starter card:\n");
        System.out.println("The front of this card has " + this.starterCard.getFrontCorners().length + " visible corners: \n");
        for(int i=0;i<this.starterCard.getFrontCorners().length;i++){
            if(this.starterCard.getFrontCorners()[i] != null && i==0 ){
                System.out.print("- UL: ");
                if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)){
                    System.out.println(this.consoleColors.TEXT_GREEN+ "PLANT");
                }else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)){
                    System.out.println(this.consoleColors.TEXT_PURPLE+ "INSECT");
                    //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                }else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)){
                    System.out.println(this.consoleColors.TEXT_RED+ "FUNGI");
                }else if (this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)){
                    System.out.println(this.consoleColors.TEXT_CYAN+ "ANIMAL");
                }else
                    System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't have any resource\n");
            }

            if(this.starterCard.getFrontCorners()[i] != null && i==1){
                System.out.print(this.consoleColors.TEXT_RESET  + "- UR: ");
                if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)){
                    System.out.println(this.consoleColors.TEXT_GREEN+ "PLANT");
                }else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)){
                    System.out.print(this.consoleColors.TEXT_PURPLE+ "INSECT");
                    //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                }else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)){
                    System.out.println(this.consoleColors.TEXT_RED+ "FUNGI");
                }else if (this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)){
                    System.out.println(this.consoleColors.TEXT_CYAN+ "ANIMAL");
                }else
                    System.out.println("The UR corner doesn't have any resource");
            }

            if(this.starterCard.getFrontCorners()[i] != null  && i==2){
                System.out.print(this.consoleColors.TEXT_RESET  + "- DL: ");
                if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)){
                    System.out.println(this.consoleColors.TEXT_GREEN+ "PLANT");
                }else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)){
                    System.out.print(this.consoleColors.TEXT_PURPLE+ "INSECT");
                    //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                }else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)){
                    System.out.println(this.consoleColors.TEXT_RED+ "FUNGI");
                }else if (this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)){
                    System.out.println(this.consoleColors.TEXT_CYAN+ "ANIMAL");
                }else
                    System.out.println("The DL corner doesn't have any resource");
            }

            if(this.starterCard.getFrontCorners()[i] != null  && i==3){
                System.out.print(this.consoleColors.TEXT_RESET  + "- DR: ");
                if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)){
                    System.out.println(this.consoleColors.TEXT_GREEN+ "PLANT");
                }else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)){
                    System.out.print(this.consoleColors.TEXT_PURPLE+ "INSECT");
                    //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                }else if(this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)){
                    System.out.println(this.consoleColors.TEXT_RED+ "FUNGI");
                }else if (this.starterCard.getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)){
                    System.out.println(this.consoleColors.TEXT_CYAN+ "ANIMAL");
                }else
                    System.out.println("The DL corner doesn't have any resource");
            }
        }

        System.out.println("\n");
        System.out.println(this.consoleColors.TEXT_RESET + "The front of this card has " + this.starterCard.getBackCorners().length + " visible corners: \n");
        for(int i=0;i<this.starterCard.getBackCorners().length;i++){

            if(this.starterCard.getBackCorners()[i]!=null){
                if(this.starterCard.getBackCorners()[i].getContentKingdom() != null){
                    if(this.starterCard.getBackCorners()[i] != null && i==0 ){
                        System.out.print("- UL: ");
                        if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Plant)){
                            System.out.println(this.consoleColors.TEXT_GREEN+ "PLANT");
                        }else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Insect)){
                            System.out.println(this.consoleColors.TEXT_PURPLE+ "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        }else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Fungi)){
                            System.out.println(this.consoleColors.TEXT_RED+ "FUNGI");
                        }else if (this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Animal)){
                            System.out.println(this.consoleColors.TEXT_CYAN+ "ANIMAL");
                        }else
                            System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't have any resource\n");
                    }

                    if(this.starterCard.getBackCorners()[i] != null && i==1){
                        System.out.print(this.consoleColors.TEXT_RESET  + "- UR: ");
                        if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Plant)){
                            System.out.println(this.consoleColors.TEXT_GREEN+ "PLANT");
                        }else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Insect)){
                            System.out.print(this.consoleColors.TEXT_PURPLE+ "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        }else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Fungi)){
                            System.out.println(this.consoleColors.TEXT_RED+ "FUNGI");
                        }else if (this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Animal)){
                            System.out.println(this.consoleColors.TEXT_CYAN+ "ANIMAL");
                        }else
                            System.out.println("The UR corner doesn't have any resource");
                    }

                    if(this.starterCard.getBackCorners()[i] != null  && i==2){
                        System.out.print(this.consoleColors.TEXT_RESET  + "- DL: ");
                        if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Plant)){
                            System.out.println(this.consoleColors.TEXT_GREEN+ "PLANT");
                        }else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Insect)){
                            System.out.print(this.consoleColors.TEXT_PURPLE+ "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        }else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Fungi)){
                            System.out.println(this.consoleColors.TEXT_RED+ "FUNGI");
                        }else if (this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Animal)){
                            System.out.println(this.consoleColors.TEXT_CYAN+ "ANIMAL");
                        }else
                            System.out.println("The DL corner doesn't have any resource");
                    }

                    if(this.starterCard.getBackCorners()[i] != null  && i==3){
                        System.out.print(this.consoleColors.TEXT_RESET  + "- DR: ");
                        if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Plant)){
                            System.out.println(this.consoleColors.TEXT_GREEN+ "PLANT");
                        }else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Insect)){
                            System.out.print(this.consoleColors.TEXT_PURPLE+ "INSECT");
                            //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                        }else if(this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Fungi)){
                            System.out.println(this.consoleColors.TEXT_RED+ "FUNGI");
                        }else if (this.starterCard.getBackCorners()[i].getContentKingdom().equals(Kingdom.Animal)){
                            System.out.println(this.consoleColors.TEXT_CYAN+ "ANIMAL");
                        }else
                            System.out.println("The DL corner doesn't have any resource");
                    }

                }else {
                    if(i==0)
                        System.out.println(this.consoleColors.TEXT_RESET+ "The UL doesn't exist");
                    if(i==1)
                        System.out.println(this.consoleColors.TEXT_RESET+ "The UR doesn't exist");
                    if(i==2)
                        System.out.println(this.consoleColors.TEXT_RESET+ "The DL doesn't exist");
                    if(i==3)
                        System.out.println(this.consoleColors.TEXT_RESET+ "The DR doesn't exist");
                }
            }
        }

        System.out.print(this.consoleColors.TEXT_RESET+ "\nThe back of the card has" + this.starterCard.getResources().toArray().length + " resources in the middle: ");
        for(int i=0; i< this.starterCard.getResources().toArray().length;i++){
            if(this.starterCard.getResources().get(i).equals(Kingdom.Plant)){
                System.out.print(this.consoleColors.TEXT_GREEN+ "PLANT ");
            }else if(this.starterCard.getResources().get(i).equals(Kingdom.Insect)){
                System.out.print(this.consoleColors.TEXT_PURPLE+ "INSECT ");
                //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
            }else if(this.starterCard.getResources().get(i).equals(Kingdom.Fungi)){
                System.out.print(this.consoleColors.TEXT_RED+ "FUNGI ");
            }else if (this.starterCard.getResources().get(i).equals(Kingdom.Animal)) {
                System.out.print(this.consoleColors.TEXT_CYAN + "ANIMAL ");
            }
        }
    }

    //TODO: NPASSAGGIO DELLE CARTE PER PROVARE SE VA
    public void viewHandCards(){
        //assegnazione carte iniziali x esempio --> HELP
        this.cards.add(this.getExampleResourceCard());
        this.cards.add(this.getExampleCornerGoldCard());
        this.cards.add(this.getExampleObjectGoldCard());

        System.out.println("These are your cards: \n");

        //scorro lista delle carte
        for(int i=0; i< this.cards.size(); i++) {
            //FRONT
            if(this.cards.get(i) instanceof ResourceCard) {
                System.out.println("This card is a resource card\n");
            }else
                System.out.println("This card is a gold card\n");

            System.out.println("The front of this card has " + this.cards.get(i).getFrontCorners().length + " visible corners: \n");
            //scorro angoli FRONT
            for(int j=0; j < this.cards.get(i).getFrontCorners().length; j++){
                //UL
                if(this.cards.get(i).getFrontCorners()[i] != null && i==0 ){
                    System.out.print("- UL: ");
                    if(this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)){
                        System.out.println(this.consoleColors.TEXT_GREEN+ "PLANT");
                    }else if(this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)){
                        System.out.println(this.consoleColors.TEXT_PURPLE+ "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    }else if(this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)){
                        System.out.println(this.consoleColors.TEXT_RED+ "FUNGI");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)){
                        System.out.println(this.consoleColors.TEXT_CYAN+ "ANIMAL");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell)){
                        System.out.println(this.consoleColors.TEXT_RESET+ "INKWELL");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill)){
                        System.out.println(this.consoleColors.TEXT_RESET+ "QUILL");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript)){
                        System.out.println(this.consoleColors.TEXT_RESET+ "MANUSCRIPT");
                    }else
                        System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't exist \n");
                }
                //UR
                if(this.cards.get(i).getFrontCorners()[i] != null && i==0 ){
                    System.out.print("- UR: ");
                    if(this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)){
                        System.out.println(this.consoleColors.TEXT_GREEN+ "PLANT");
                    }else if(this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)){
                        System.out.println(this.consoleColors.TEXT_PURPLE+ "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    }else if(this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)){
                        System.out.println(this.consoleColors.TEXT_RED+ "FUNGI");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)){
                        System.out.println(this.consoleColors.TEXT_CYAN+ "ANIMAL");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell)){
                        System.out.println(this.consoleColors.TEXT_RESET+ "INKWELL");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill)){
                        System.out.println(this.consoleColors.TEXT_RESET+ "QUILL");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript)){
                        System.out.println(this.consoleColors.TEXT_RESET+ "MANUSCRIPT");
                    }else
                        System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't exist \n");
                }
                //DL
                if(this.cards.get(i).getFrontCorners()[i] != null && i==0 ){
                    System.out.print("- DL: ");
                    if(this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)){
                        System.out.println(this.consoleColors.TEXT_GREEN+ "PLANT");
                    }else if(this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)){
                        System.out.println(this.consoleColors.TEXT_PURPLE+ "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    }else if(this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)){
                        System.out.println(this.consoleColors.TEXT_RED+ "FUNGI");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)){
                        System.out.println(this.consoleColors.TEXT_CYAN+ "ANIMAL");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell)){
                        System.out.println(this.consoleColors.TEXT_RESET+ "INKWELL");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill)){
                        System.out.println(this.consoleColors.TEXT_RESET+ "QUILL");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript)){
                        System.out.println(this.consoleColors.TEXT_RESET+ "MANUSCRIPT");
                    }else
                        System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't exist \n");
                }
                //DR
                if(this.cards.get(i).getFrontCorners()[i] != null && i==0 ){
                    System.out.print("- DR: ");
                    if(this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)){
                        System.out.println(this.consoleColors.TEXT_GREEN+ "PLANT");
                    }else if(this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)){
                        System.out.println(this.consoleColors.TEXT_PURPLE+ "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    }else if(this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)){
                        System.out.println(this.consoleColors.TEXT_RED+ "FUNGI");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)){
                        System.out.println(this.consoleColors.TEXT_CYAN+ "ANIMAL");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell)){
                        System.out.println(this.consoleColors.TEXT_RESET+ "INKWELL");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill)){
                        System.out.println(this.consoleColors.TEXT_RESET+ "QUILL");
                    }else if (this.cards.get(i).getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript)){
                        System.out.println(this.consoleColors.TEXT_RESET+ "MANUSCRIPT");
                    }else
                        System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't exist \n");
                }
            }

            //BACK
            //ps il back di ogni carta ha 4 visible corners sempre
            System.out.println("The back of this card has 4 visible corners: \n");

            //SE SONO CARTE GOLD
            if(this.cards.get(i) instanceof GoldCard){
                //REQUIREMENTS
                for(int j=0; j < this.cards.get(i).getRequirements().size(); j++){
                    if(this.cards.get(i).getRequirements().equals(Kingdom.Plant)){
                        System.out.println(this.consoleColors.TEXT_GREEN+ "PLANT");
                    }else if(this.cards.get(i).getRequirements().equals(Kingdom.Insect)){
                        System.out.println(this.consoleColors.TEXT_PURPLE+ "INSECT");
                    }else if(this.cards.get(i).getRequirements().equals(Kingdom.Fungi)){
                        System.out.println(this.consoleColors.TEXT_RED+ "FUNGI");
                    }else if(this.cards.get(i).getRequirements().equals(Kingdom.Animal)){
                        System.out.println(this.consoleColors.TEXT_CYAN+ "ANIMAL");
                    }
                }
                //TIPO DI GOLD CARD
                if(this.cards.get(i) instanceof PointsGoldCard){
                    System.out.println("You get" + ((PointsGoldCard) this.cards.get(i)).getPoints() + "points if you play this card correctly \n");
                }else if(this.cards.get(i) instanceof ObjectGoldCard){
                    System.out.println("You get" + ((ObjectGoldCard) this.cards.get(i)).getPoints() + "points if you play this card correctly " +
                                       " and if you have" + this.cards.get(i).getSpecialObjects() + "as a special object \n");
                }else if(this.cards.get(i) instanceof CornerGoldCard){
                    System.out.println("You get 2 points every time you cover another card's angle with this card ");
                }
            }
        }
    }

    //TODO ELE: FINIRE E RISOLVERE PROBLEMI (SI MA LORE DAI UN'OCCHIATA DAI GRAZIE)
    /*
     domande:
     - ci sono due tipi di vertical configuration --> mi manca da dire dove deve ster la seconda occorrenza della carta del 2^ kingdom
     - qui non dico nulla sul back degli objective perchès sono tutti uguali?
     */
    public void viewCommonObjective(){
        System.out.println("These are the common objectives: \n");
        //scorro array dei common objectives
        for(int i=0; i<commonObjectives.length; i++){
            if(commonObjectives[i] instanceof DiagonalConfigurationObjectiveCard){
                System.out.println("- DIAGONAL CONFIGURATION OBJECTIVE: \n");
                System.out.println("You need to create a diagonal configuration, with" +
                                       ((DiagonalConfigurationObjectiveCard) commonObjectives[i]).getKingdom() + "resource cards only," +
                                       "starting by covering the" + ((DiagonalConfigurationObjectiveCard) commonObjectives[i]).getCoveredCorner() +
                                        "first \n");
                System.out.println("You get 2 points every time you create this kind of configuration \n");
            }else if(commonObjectives[i] instanceof PairOfObjectsObjectiveCard){
                System.out.println("- PAIR OF OBJECTS OBJECTIVES: \n");
                System.out.println("You get 2 points every time you collect two" +
                                   ((PairOfObjectsObjectiveCard) commonObjectives[i]).getSpecialObject() +"objects \n");
            }else if(commonObjectives[i] instanceof TrioOfObjectsObjectiveCard){
                System.out.println("- TRIO OF OBJECTS OBJECTIVE: \n");
                System.out.println("You get 2 points every time you collect an inkwell, a manuscript and a quill \n");
            }else if(commonObjectives[i] instanceof TrioOfResourcesObjectiveCard){
                System.out.println("- TRIO OF RESOURCES OBJECTIVE: \n");
                System.out.println("You get 2 points every time you collect three" +
                                    ((TrioOfResourcesObjectiveCard) commonObjectives[i]).getResourcesKingdom() +"resource cards \n");
            }else if(commonObjectives[i] instanceof VerticalConfigurationObjectiveCard){
                System.out.println("- VERTICAL CONFIGURATION OBJECTIVE: \n");
                System.out.println("You need to create a vertical configuration, with just an occurrence of a"
                                  + ((VerticalConfigurationObjectiveCard) commonObjectives[i]).getKingdom1() + " resource card.\n" +
                                  " This card needs to cover the" + ((VerticalConfigurationObjectiveCard) commonObjectives[i]).getCoveredCorner() + "angle " +
                                  "of a " + ((VerticalConfigurationObjectiveCard) commonObjectives[i]).getKingdom2() + "resource card, which needs to have 2 occurrences in the configuration \n");
                System.out.println("You get 3 points every time you create this kind of configuration \n");
            }
        }
    }

    //TODO ELE: UGUALE A COMMON OBJECTIVES
    public void viewSecretObjective(){
        System.out.println("These is your secret objective: \n");
        if(secretObjectives[0] instanceof DiagonalConfigurationObjectiveCard){
            System.out.println("- DIAGONAL CONFIGURATION OBJECTIVE: \n");
            System.out.println("You need to create a diagonal configuration, with" +
                    ((DiagonalConfigurationObjectiveCard) secretObjectives[0]).getKingdom() + "resource cards only," +
                    "starting by covering the" + ((DiagonalConfigurationObjectiveCard) secretObjectives[0]).getCoveredCorner() +
                    "first \n");
            System.out.println("You get 2 points every time you create this kind of configuration \n");
        }else if(secretObjectives[0] instanceof PairOfObjectsObjectiveCard){
            System.out.println("- PAIR OF OBJECTS OBJECTIVES: \n");
            System.out.println("You get 2 points every time you collect two" +
                    ((PairOfObjectsObjectiveCard) secretObjectives[0]).getSpecialObject() +"objects \n");
        }else if(secretObjectives[0] instanceof TrioOfObjectsObjectiveCard){
            System.out.println("- TRIO OF OBJECTS OBJECTIVE: \n");
            System.out.println("You get 2 points every time you collect an inkwell, a manuscript and a quill \n");
        }else if(secretObjectives[0] instanceof TrioOfResourcesObjectiveCard){
            System.out.println("- TRIO OF RESOURCES OBJECTIVE: \n");
            System.out.println("You get 2 points every time you collect three" +
                    ((TrioOfResourcesObjectiveCard) secretObjectives[0]).getResourcesKingdom() +"resource cards \n");
        }else if(secretObjectives[0] instanceof VerticalConfigurationObjectiveCard){
            System.out.println("- VERTICAL CONFIGURATION OBJECTIVE: \n");
            System.out.println("You need to create a vertical configuration, with just an occurrence of a"
                    + ((VerticalConfigurationObjectiveCard) secretObjectives[0]).getKingdom1() + " resource card.\n" +
                    " This card needs to cover the" + ((VerticalConfigurationObjectiveCard) secretObjectives[0]).getCoveredCorner() + "angle " +
                    "of a " + ((VerticalConfigurationObjectiveCard) secretObjectives[0] ).getKingdom2() + "resource card, which needs to have 2 occurrences in the configuration \n");
            System.out.println("You get 3 points every time you create this kind of configuration \n");
        }

    }

    public void viewGoldTop(){
        System.out.println("The card that's now on top of the gold deck is a ");

        //KINGDOM
        if(this.goldTop.getKingdoms().equals(Kingdom.Plant)){
            System.out.println(this.consoleColors.TEXT_GREEN+ " PLANT");
        }else if(this.goldTop.getKingdoms().equals(Kingdom.Insect)){
            System.out.println(this.consoleColors.TEXT_PURPLE+ " INSECT");
        }else if(this.goldTop.getKingdoms().equals(Kingdom.Fungi)){
            System.out.println(this.consoleColors.TEXT_RED+ " FUNGI");
        }else if(this.goldTop.getKingdoms().equals(Kingdom.Animal)){
            System.out.println(this.consoleColors.TEXT_CYAN+ " ANIMAL");
        }
        System.out.println(this.consoleColors.TEXT_RESET+ "card \n");
    }

    public void viewResourceTop(){
        System.out.println("The card that's now on top of the resource deck is a ");

        //KINGDOM
        if(this.resourceTop.getKingdoms().equals(Kingdom.Plant)){
            System.out.println(this.consoleColors.TEXT_GREEN+ " PLANT");
        }else if(this.resourceTop.getKingdoms().equals(Kingdom.Insect)){
            System.out.println(this.consoleColors.TEXT_PURPLE+ " INSECT");
        }else if(this.resourceTop.getKingdoms().equals(Kingdom.Fungi)){
            System.out.println(this.consoleColors.TEXT_RED+ " FUNGI");
        }else if(this.resourceTop.getKingdoms().equals(Kingdom.Animal)){
            System.out.println(this.consoleColors.TEXT_CYAN+ " ANIMAL");
        }
        System.out.println(this.consoleColors.TEXT_RESET+ "card\n");
    }


    public void viewGoldVisibleCards(){
        System.out.println("The gold cards that are now visible on the table are the following \n");
        for(int i=0; i< goldVisible.length; i++) {
            //KINGDOM GENERICO
            if (i == 0) {
                System.out.println("First card:" + goldVisible[0].getCardKingdom() + "card\n");
            } else
                System.out.println("Second card:" + goldVisible[1].getCardKingdom() + "card\n");

            //ELENCO DELLE RISORSE IN OGNI CORNER:
            System.out.println("This card has " + this.goldVisible[i].getFrontCorners().length + " visible corners: \n");
            //scorro angoli FRONT
            for(int j=0; j < this.cards.get(i).getFrontCorners().length; j++) {
                //UL
                if (this.goldVisible[i] != null && i == 0) {
                    System.out.print("- UL: ");
                    if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)) {
                        System.out.println(this.consoleColors.TEXT_GREEN + "PLANT");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)) {
                        System.out.println(this.consoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)) {
                        System.out.println(this.consoleColors.TEXT_RED + "FUNGI");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)) {
                        System.out.println(this.consoleColors.TEXT_CYAN + "ANIMAL");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell)) {
                        System.out.println(this.consoleColors.TEXT_RESET + "INKWELL");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill)) {
                        System.out.println(this.consoleColors.TEXT_RESET + "QUILL");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript)) {
                        System.out.println(this.consoleColors.TEXT_RESET + "MANUSCRIPT");
                    } else
                        System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't exist \n");
                }
                //DL
                if (this.goldVisible[i] != null && i == 0) {
                    System.out.print("- DL: ");
                    if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)) {
                        System.out.println(this.consoleColors.TEXT_GREEN + "PLANT");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)) {
                        System.out.println(this.consoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)) {
                        System.out.println(this.consoleColors.TEXT_RED + "FUNGI");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)) {
                        System.out.println(this.consoleColors.TEXT_CYAN + "ANIMAL");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell)) {
                        System.out.println(this.consoleColors.TEXT_RESET + "INKWELL");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill)) {
                        System.out.println(this.consoleColors.TEXT_RESET + "QUILL");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript)) {
                        System.out.println(this.consoleColors.TEXT_RESET + "MANUSCRIPT");
                    } else
                        System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't exist \n");
                }
                //DL
                if (this.goldVisible[i] != null && i == 0) {
                    System.out.print("- DL: ");
                    if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)) {
                        System.out.println(this.consoleColors.TEXT_GREEN + "PLANT");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)) {
                        System.out.println(this.consoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)) {
                        System.out.println(this.consoleColors.TEXT_RED + "FUNGI");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)) {
                        System.out.println(this.consoleColors.TEXT_CYAN + "ANIMAL");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell)) {
                        System.out.println(this.consoleColors.TEXT_RESET + "INKWELL");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill)) {
                        System.out.println(this.consoleColors.TEXT_RESET + "QUILL");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript)) {
                        System.out.println(this.consoleColors.TEXT_RESET + "MANUSCRIPT");
                    } else
                        System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't exist \n");
                }
                //DR
                if (this.goldVisible[i] != null && i == 0) {
                    System.out.print("- DR: ");
                    if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)) {
                        System.out.println(this.consoleColors.TEXT_GREEN + "PLANT");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)) {
                        System.out.println(this.consoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)) {
                        System.out.println(this.consoleColors.TEXT_RED + "FUNGI");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)) {
                        System.out.println(this.consoleColors.TEXT_CYAN + "ANIMAL");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentObject().equals(SpecialObject.Inkwell)) {
                        System.out.println(this.consoleColors.TEXT_RESET + "INKWELL");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentObject().equals(SpecialObject.Quill)) {
                        System.out.println(this.consoleColors.TEXT_RESET + "QUILL");
                    } else if (this.goldVisible[i].getFrontCorners()[i].getContentObject().equals(SpecialObject.Manuscript)) {
                        System.out.println(this.consoleColors.TEXT_RESET + "MANUSCRIPT");
                    } else
                        System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't exist \n");
                }
            }
        }
    }

    public void viewResourceVisibleCards(){
        System.out.println("The resource cards that are now visible on the table are the following \n");
        for(int i=0; i< resourceVisible.length; i++){
            //KINGDOM GENERICO
            if(i==0){
                System.out.println("First card:" + resourceVisible[0].getCardKingdom() + "card\n");
            }else
                System.out.println("Second card:" + resourceVisible[1].getCardKingdom() + "card\n");

            //ELENCO DELLE RISORSE IN OGNI CORNER:
            System.out.println("This card has " + this.resourceVisible[i].getFrontCorners().length + " visible corners: \n");
            //scorro angoli FRONT
            for(int j=0; j < this.resourceVisible[i].getFrontCorners().length; j++) {
                //UL
                if (this.resourceVisible[i].getFrontCorners()[i] != null && i == 0) {
                    System.out.print("- UL: ");
                    if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)) {
                        System.out.println(this.consoleColors.TEXT_GREEN + "PLANT");
                    } else if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)) {
                        System.out.println(this.consoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    } else if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)) {
                        System.out.println(this.consoleColors.TEXT_RED + "FUNGI");
                    } else if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)) {
                        System.out.println(this.consoleColors.TEXT_CYAN + "ANIMAL");
                    }else
                        System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't have any resource\n");
                }
                //UR
                if (this.resourceVisible[i].getFrontCorners()[i] != null && i == 0) {
                    System.out.print("- UR: ");
                    if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)) {
                        System.out.println(this.consoleColors.TEXT_GREEN + "PLANT");
                    } else if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)) {
                        System.out.println(this.consoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    } else if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)) {
                        System.out.println(this.consoleColors.TEXT_RED + "FUNGI");
                    } else if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)) {
                        System.out.println(this.consoleColors.TEXT_CYAN + "ANIMAL");
                    }else
                        System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't have any resource\n");
                }
                //DL
                if (this.resourceVisible[i].getFrontCorners()[i] != null && i == 0) {
                    System.out.print("- DL: ");
                    if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)) {
                        System.out.println(this.consoleColors.TEXT_GREEN + "PLANT");
                    } else if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)) {
                        System.out.println(this.consoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    } else if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)) {
                        System.out.println(this.consoleColors.TEXT_RED + "FUNGI");
                    } else if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)) {
                        System.out.println(this.consoleColors.TEXT_CYAN + "ANIMAL");
                    }else
                        System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't have any resource\n");
                }
                //DR
                if (this.resourceVisible[i].getFrontCorners()[i] != null && i == 0) {
                    System.out.print("- DR: ");
                    if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Plant)) {
                        System.out.println(this.consoleColors.TEXT_GREEN + "PLANT");
                    } else if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Insect)) {
                        System.out.println(this.consoleColors.TEXT_PURPLE + "INSECT");
                        //sSystem.out.format("%s", this.consoleColors.TEXT_PURPLE);
                    } else if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Fungi)) {
                        System.out.println(this.consoleColors.TEXT_RED + "FUNGI");
                    } else if (this.resourceVisible[i].getFrontCorners()[i].getContentKingdom().equals(Kingdom.Animal)) {
                        System.out.println(this.consoleColors.TEXT_CYAN + "ANIMAL");
                    }else
                        System.out.println(this.consoleColors.TEXT_RESET + "The UL corner doesn't have any resource\n");
                }
            }
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
        System.out.print(this.consoleColors.TEXT_GREEN+"PLANT: ");
        System.out.println(this.consoleColors.TEXT_RESET + this.yourPlayerInfo.getStats().getNumberOfResources(Kingdom.Plant));
        System.out.print(this.consoleColors.TEXT_RESET + "- ");
        System.out.print(this.consoleColors.TEXT_PURPLE+"INSECT: ");
        System.out.println(this.consoleColors.TEXT_RESET + this.yourPlayerInfo.getStats().getNumberOfResources(Kingdom.Insect));
        System.out.print(this.consoleColors.TEXT_RESET + "- ");
        System.out.print(this.consoleColors.TEXT_RED+"FUNGI: ");
        System.out.println(this.consoleColors.TEXT_RESET + this.yourPlayerInfo.getStats().getNumberOfResources(Kingdom.Fungi));
        System.out.print(this.consoleColors.TEXT_RESET + "- ");
        System.out.print(this.consoleColors.TEXT_CYAN+"ANIMAL: ");
        System.out.println(this.consoleColors.TEXT_RESET + this.yourPlayerInfo.getStats().getNumberOfResources(Kingdom.Animal));
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
        System.out.print(this.consoleColors.TEXT_GREEN+"PLANT: ");
        System.out.println(this.consoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Plant));
        System.out.print(this.consoleColors.TEXT_RESET + "- ");
        System.out.print(this.consoleColors.TEXT_PURPLE+"INSECT: ");
        System.out.println(this.consoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Insect));
        System.out.print(this.consoleColors.TEXT_RESET + "- ");
        System.out.print(this.consoleColors.TEXT_RED+"FUNGI: ");
        System.out.println(this.consoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Fungi));
        System.out.print(this.consoleColors.TEXT_RESET + "- ");
        System.out.print(this.consoleColors.TEXT_CYAN+"ANIMAL: ");
        System.out.println(this.consoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Animal));
        System.out.println("- QUILL: "+ this.othersPlayerInfo.get(username).getStats().getNumberOfObjects(SpecialObject.Quill));
        System.out.println("- INKWELL: "+ this.othersPlayerInfo.get(username).getStats().getNumberOfObjects(SpecialObject.Inkwell));
        System.out.println("- MANUSCRIPT: "+ this.othersPlayerInfo.get(username).getStats().getNumberOfObjects(SpecialObject.Manuscript));

        System.out.println("The player has the follwoing board:");
        System.out.println(this.othersPlayerInfo.get(username).getMap().toString());

    }

    public void viewPlacement(){
        System.out.println("The placement has the following order: ");
        System.out.println("- " + this.nickname + " has " + this.yourPlayerInfo.getScore());
        for(String s : this.listOtherPlayer){
            System.out.println("- " + s + " has " + this.othersPlayerInfo.get(s));
        }

    }

    public void viewChatMessage(){

    }

//    public void viewBroadcastChatMessage(){
//
//    }








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
    }

    public static void main(String[] args) throws InterruptedException {
        TUI myTui = new TUI();
        myTui.printTitle();

        myTui.viewCommand();
        //myTui.waitSeconds(5);
        //myTui.clearTerminal(10);
        myTui.viewStarterCard();

    }

    StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }
    ResourceCard getExampleResourceCard(String id){
        ArrayList<PlayableCard> ResourceCard = XMLparser.parseResourceCards("resourceCards.xml");
        return (ResourceCard) ResourceCard.stream().filter(x->x.getID().equals("R15")).findAny().orElse(null);
    }
    CornerGoldCard getExampleCornerGoldCard(String id){
        ArrayList<PlayableCard> CornerGoldCard = XMLparser.parseGoldCards("goldCards.xml");
        return (CornerGoldCard) CornerGoldCard.stream().filter(x->x.getID().equals("G74")).findAny().orElse(null);
    }
    ObjectGoldCard getExampleObjectGoldCard(){
        ArrayList<PlayableCard> ObjectGoldCard = XMLparser.parseGoldCards("goldCards.xml");
        return (ObjectGoldCard) ObjectGoldCard.stream().filter(x->x.getID().equals("G42")).findAny().orElse(null);
    }


}
