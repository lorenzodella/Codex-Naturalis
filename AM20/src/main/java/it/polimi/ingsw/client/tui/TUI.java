package it.polimi.ingsw.client.tui;

import it.polimi.ingsw.client.clientcard.CardPrinter;
import it.polimi.ingsw.client.UIManager;
import it.polimi.ingsw.client.clientcard.*;
import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.controller.messages.ChatMessage;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.objective.*;
import it.polimi.ingsw.model.cards.playable.*;
import it.polimi.ingsw.model.exceptions.InvalidAngleCoveredException;
import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.RequirementsNotRespectedException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;

import java.util.*;

public class TUI implements UIManager {
    /*TODO:
      - ATTRIBUTO PER CAPIRE DI CHE ANGOLO SI TRATTA --> PER ITERARE SU ANGOLI
    */
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
    private String currPlayer;

    private static String getKingdomColor(Kingdom k){
        if(k==null)
            return ConsoleColors.TEXT_RESET;
        switch(k){
            case Fungi:
                return ConsoleColors.TEXT_RED;
            case Animal:
                return ConsoleColors.TEXT_CYAN;
            case Plant:
                return ConsoleColors.TEXT_GREEN;
            case Insect:
                return ConsoleColors.TEXT_PURPLE;
            default:
                return ConsoleColors.TEXT_RESET;
        }
    }

    public TUI(){
        this.messages = new LinkedList<>();
        this.cards = new ArrayList<>();
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void startGame() {
        System.out.println("The game is starting now");
    }

    @Override
    public void updateCards(List<PlayableCard> cards) {
        if(cards!=null){
            this.cards = cards;
            viewHandCards();
        }



    }

    @Override
    public void updateChatMessage(ChatMessage msg) {
        this.messages.add(msg);
        if(msg.getRecipient()!=null && msg.getRecipient().equals(nickname))
            System.out.println("\nYou received a message");
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
    public void updateGold(PlayableCard goldTop, PlayableCard[] goldVisible) {
        if(goldTop!=null && goldVisible !=null){
            this.goldTop = goldTop;
            this.goldVisible = goldVisible;
            viewGold();
        }



    }

    @Override
    public void updateResource(PlayableCard resourceTop, PlayableCard[] resourceVisible) {
        if(resourceTop != null && resourceVisible != null){
            this.resourceTop = resourceTop;
            this.resourceVisible = resourceVisible;
            viewResource();
        }

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
            /*for (String nickname : otherPlayerInfo.keySet())
                viewOtherPlayerInfo(nickname);*/
        }
    }

    @Override
    public void updateCommonObjectives(ObjectiveCard[] commonObjectives) {
        if(commonObjectives!=null){
            this.commonObjectives = commonObjectives;
            viewCommonObjective();
        }

    }

    @Override
    public void updateStarterCard(StarterCard starterCard) {
        if(starterCard!=null){
            this.starterCard = starterCard;
            viewStarterCard();
        }

    }

    @Override
    public void showResult(String result) {
        System.out.println(result);
    }

    @Override
    public void showNextTurn(String nextPlayer){
        if(nextPlayer!=null) {
            currPlayer = nextPlayer;
            if (nextPlayer.equals(nickname)) {
                viewGoldVisibleCards();
                viewResourceVisibleCards();
                viewGoldTop();
                viewResourceTop();
                System.out.println("Is your turn");
            } else {
                System.out.println("Is " + nextPlayer + "'s turn");
            }
        }
        else{
            System.out.println(ConsoleColors.TEXT_BG_GREEN+"Game is over!");
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

    public void viewCurrPlayer(){
        if(currPlayer!=null){
            if(currPlayer.equals(nickname))
                System.out.println("You are the current player\n");
            else
                System.out.println("Current player is "+currPlayer);
        }
        else{
            System.out.println("There is no current player yet\n");
        }
    }


    public void viewStarterCard(){
        //this.printTitle();
        //this.viewCommand();

        StarterCardClient starterCardClient = new StarterCardClient(starterCard);
        starterCardClient.draw();
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("You have received the starter card and now you should choose the side");

        //this.viewCommand();

    }

    public void viewHandCards(){
        System.out.println(ConsoleColors.TEXT_BLUE + "HAND CARD: \n" + ConsoleColors.TEXT_RESET);
        for(int i=0;i<this.cards.size();i++){
            if(this.cards.get(i) instanceof ResourceCard){
                ResourceCardClient resourceCardClient = new ResourceCardClient((ResourceCard) this.cards.get(i),i);
                resourceCardClient.draw();
            }else if(this.cards.get(i) instanceof CornerGoldCard){
                CornerGoldCardClient cornerGoldCardClient = new CornerGoldCardClient((CornerGoldCard) this.cards.get(i),i);
                cornerGoldCardClient.draw();
            }else if(this.cards.get(i) instanceof ObjectGoldCard){
                ObjectGoldCardClient objectGoldCardClient = new ObjectGoldCardClient((ObjectGoldCard) this.cards.get(i),i);
                objectGoldCardClient.draw();
            }else if(this.cards.get(i) instanceof PointsGoldCard){
                PointsGoldCardClient pointsGoldCardClient = new PointsGoldCardClient((PointsGoldCard) this.cards.get(i),i);
                pointsGoldCardClient.draw();
            }
        }

    }

    public void viewCommonObjective(){

        System.out.println(ConsoleColors.TEXT_BLUE + "COMMON OBJECTIVE: \n" + ConsoleColors.TEXT_RESET);
        //scorro array dei common objectives
        for(int i=0; i<this.commonObjectives.length; i++) {

            if (this.commonObjectives[i] instanceof DiagonalConfigurationObjectiveCard) {
                DiagonalConfigurationObjectiveCardClient diagonalConfigurationObjectiveCardClient = new DiagonalConfigurationObjectiveCardClient((DiagonalConfigurationObjectiveCard) this.commonObjectives[i]);
                diagonalConfigurationObjectiveCardClient.draw();
            } else if (this.commonObjectives[i] instanceof PairOfObjectsObjectiveCard) {
                PairOfObjectsObjectiveCardClient pairOfObjectsObjectiveCardClient = new PairOfObjectsObjectiveCardClient((PairOfObjectsObjectiveCard) this.commonObjectives[i]);
                pairOfObjectsObjectiveCardClient.draw();
            } else if (this.commonObjectives[i] instanceof TrioOfObjectsObjectiveCard) {
                TrioOfObjectsObjectiveCardClient trioOfObjectsObjectiveCardClient = new TrioOfObjectsObjectiveCardClient((TrioOfObjectsObjectiveCard) this.commonObjectives[i]);
                trioOfObjectsObjectiveCardClient.draw();
            } else if (this.commonObjectives[i] instanceof TrioOfResourcesObjectiveCard) {
                TrioOfResourcesObjectiveCardClient trioOfResourcesObjectiveCardClient = new TrioOfResourcesObjectiveCardClient((TrioOfResourcesObjectiveCard) this.commonObjectives[i]);
                trioOfResourcesObjectiveCardClient.draw();
            } else if (this.commonObjectives[i] instanceof VerticalConfigurationObjectiveCard) {
                VerticalConfigurationObjectiveCardClient verticalConfigurationObjectiveCardClient = new VerticalConfigurationObjectiveCardClient((VerticalConfigurationObjectiveCard) this.commonObjectives[i]);
                verticalConfigurationObjectiveCardClient.draw();
            }
        }
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------\n");
        //this.viewCommand();
    }

    public void viewSecretObjective(){
        System.out.println(ConsoleColors.TEXT_BLUE + "SECRET OBJECTIVE: \n"+ConsoleColors.TEXT_RESET);
        //scorro array dei common objectives
        for(int i=0; i<this.secretObjectives.size(); i++){

            try{
                if (this.secretObjectives.get(i) instanceof DiagonalConfigurationObjectiveCard) {
                    DiagonalConfigurationObjectiveCardClient diagonalConfigurationObjectiveCardClient = new DiagonalConfigurationObjectiveCardClient((DiagonalConfigurationObjectiveCard) this.secretObjectives.get(i));
                    diagonalConfigurationObjectiveCardClient.draw();
                } else if (this.secretObjectives.get(i) instanceof PairOfObjectsObjectiveCard) {
                    PairOfObjectsObjectiveCardClient pairOfObjectsObjectiveCardClient = new PairOfObjectsObjectiveCardClient((PairOfObjectsObjectiveCard) this.secretObjectives.get(i));
                    pairOfObjectsObjectiveCardClient.draw();
                } else if (this.secretObjectives.get(i) instanceof TrioOfObjectsObjectiveCard) {
                    TrioOfObjectsObjectiveCardClient trioOfObjectsObjectiveCardClient = new TrioOfObjectsObjectiveCardClient((TrioOfObjectsObjectiveCard) this.secretObjectives.get(i));
                    trioOfObjectsObjectiveCardClient.draw();
                } else if (this.secretObjectives.get(i) instanceof TrioOfResourcesObjectiveCard) {
                    TrioOfResourcesObjectiveCardClient trioOfResourcesObjectiveCardClient = new TrioOfResourcesObjectiveCardClient((TrioOfResourcesObjectiveCard) this.secretObjectives.get(i));
                    trioOfResourcesObjectiveCardClient.draw();
                } else if (this.secretObjectives.get(i) instanceof VerticalConfigurationObjectiveCard) {
                    VerticalConfigurationObjectiveCardClient verticalConfigurationObjectiveCardClient = new VerticalConfigurationObjectiveCardClient((VerticalConfigurationObjectiveCard) this.secretObjectives.get(i));
                    verticalConfigurationObjectiveCardClient.draw();
                }

            }catch (ClassCastException e){
                e.printStackTrace();
            }


        }
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------\n");
        //this.viewCommand();
    }

    //TODO
    public void viewGold(){

    }

    //TODO
    public void viewResource(){

    }

    public void viewGoldTop(){
        System.out.print("\nThe card that's now on top of the gold deck is a ");
        //KINGDOM
        if(this.goldTop.getCardKingdom().equals(Kingdom.Fungi))
            System.out.print(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
        else if(this.goldTop.getCardKingdom().equals(Kingdom.Insect))
            System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT"+ConsoleColors.TEXT_RESET);
        else if(this.goldTop.getCardKingdom().equals(Kingdom.Plant))
            System.out.print(ConsoleColors.TEXT_GREEN + "PLANT"+ConsoleColors.TEXT_RESET);
        else
            System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL"+ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_RESET + " card ");

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------\n");
        //this.viewCommand();
    }

    public void viewResourceTop(){
        System.out.print("The card that's now on top of the resource deck is a ");
        if(this.resourceTop.getCardKingdom().equals(Kingdom.Fungi))
            System.out.print(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
        else if(this.resourceTop.getCardKingdom().equals(Kingdom.Insect))
            System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT"+ConsoleColors.TEXT_RESET);
        else if(this.resourceTop.getCardKingdom().equals(Kingdom.Plant))
            System.out.print(ConsoleColors.TEXT_GREEN + "PLANT"+ConsoleColors.TEXT_RESET);
        else
            System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL"+ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_RESET + " card ");

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------\n");
        //this.viewCommand();
    }

    public void viewGoldVisibleCards(){
        //List<PlayableCard> listTest = new ArrayList<>();
        System.out.println("The gold cards that are now visible on the table are the following: \n");
        if(this.goldVisible !=null){
            for(int i=0; i < this.goldVisible.length; i++) {

                if (i == 0) {
                    System.out.print("The first card");
                } else
                    System.out.print("The second card");

                if(this.goldVisible[i] instanceof CornerGoldCard){
                    CornerGoldCardClient cornerGoldCardClient = new CornerGoldCardClient((CornerGoldCard) this.goldVisible[i]);
                    cornerGoldCardClient.draw();
                }else if(this.goldVisible[i] instanceof ObjectGoldCard){
                    ObjectGoldCardClient objectGoldCardClient = new ObjectGoldCardClient((ObjectGoldCard) this.goldVisible[i]);
                    objectGoldCardClient.draw();
                }else if(this.goldVisible[i] instanceof PointsGoldCard){
                    PointsGoldCardClient pointsGoldCardClient = new PointsGoldCardClient((PointsGoldCard) this.goldVisible[i]);
                    pointsGoldCardClient.draw();
                }
            }

        }
    }

    public void viewResourceVisibleCards(){
        System.out.println("The resource cards that are now visible on the table are the following \n");
        for(int i=0; i < this.resourceVisible.length; i++) {
            //KINGDOM GENERICO
            if (i == 0) {
                System.out.print("The first card");
            } else
                System.out.print("Second card is");

            ResourceCardClient resourceCardClient = new ResourceCardClient((ResourceCard) this.resourceVisible[i]);
            resourceCardClient.draw();
        }
    }

    public void viewPlayerInfo(){
        this.printTitle();
        System.out.println("These are your information");
        System.out.println("You have done "+ this.yourPlayerInfo.getScore() + " points");

        //TODO
        //System.out.println("The common objective "); oppure chaiamata al metodo viewCommonObjective
        System.out.println("You have: ");
        System.out.print("- ");
        System.out.print(ConsoleColors.TEXT_GREEN +"PLANT: "+ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_RESET + this.yourPlayerInfo.getStats().getNumberOfResources(Kingdom.Plant));
        System.out.print(ConsoleColors.TEXT_RESET + "- ");
        System.out.print(ConsoleColors.TEXT_PURPLE +"INSECT: "+ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_RESET + this.yourPlayerInfo.getStats().getNumberOfResources(Kingdom.Insect));
        System.out.print(ConsoleColors.TEXT_RESET + "- ");
        System.out.print(ConsoleColors.TEXT_RED +"FUNGI: "+ ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_RESET + this.yourPlayerInfo.getStats().getNumberOfResources(Kingdom.Fungi));
        System.out.print(ConsoleColors.TEXT_RESET + "- ");
        System.out.print(ConsoleColors.TEXT_CYAN +"ANIMAL: "+ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_RESET + this.yourPlayerInfo.getStats().getNumberOfResources(Kingdom.Animal));
        System.out.println("- QUILL: "+ this.yourPlayerInfo.getStats().getNumberOfObjects(SpecialObject.Quill));
        System.out.println("- INKWELL: "+ this.yourPlayerInfo.getStats().getNumberOfObjects(SpecialObject.Inkwell));
        System.out.println("- MANUSCRIPT: "+ this.yourPlayerInfo.getStats().getNumberOfObjects(SpecialObject.Manuscript));

        if(yourPlayerInfo.getMap()!=null) {
            System.out.println("The player has the follwoing board:");
            CardPrinter.printMap(this.yourPlayerInfo.getMap());
        }

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------\n");
        //this.viewCommand();
    }

    public void viewOtherPlayerInfo(String username){
        this.printTitle();
        System.out.println("The following information are the one of " + username+" game");
        System.out.println(username + " has done "+ this.othersPlayerInfo.get(username).getScore() + " points");

        //TODO
        //System.out.println("The common objective "); oppure chiamata al metodo viewCommonObjective
        System.out.println(username + " has:");
        System.out.print("- ");
        System.out.print(ConsoleColors.TEXT_GREEN +"PLANT: "+ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Plant));
        System.out.print(ConsoleColors.TEXT_RESET + "- ");
        System.out.print(ConsoleColors.TEXT_PURPLE +"INSECT: "+ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Insect));
        System.out.print(ConsoleColors.TEXT_RESET + "- ");
        System.out.print(ConsoleColors.TEXT_RED +"FUNGI: "+ ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Fungi));
        System.out.print(ConsoleColors.TEXT_RESET + "- ");
        System.out.print(ConsoleColors.TEXT_CYAN +"ANIMAL: "+ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Animal));
        System.out.println("- QUILL: "+ this.othersPlayerInfo.get(username).getStats().getNumberOfObjects(SpecialObject.Quill));
        System.out.println("- INKWELL: "+ this.othersPlayerInfo.get(username).getStats().getNumberOfObjects(SpecialObject.Inkwell));
        System.out.println("- MANUSCRIPT: "+ this.othersPlayerInfo.get(username).getStats().getNumberOfObjects(SpecialObject.Manuscript));

        if(this.othersPlayerInfo.get(username).getMap()!=null) {
            System.out.println("The player has the follwoing board:");
            CardPrinter.printMap(this.othersPlayerInfo.get(username).getMap());
        }

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------");
        //this.viewCommand();
    }

    public void viewPlacement(){
        System.out.println("The placement has the following order: ");
        System.out.println("- " + this.nickname + " has " + this.yourPlayerInfo.getScore() + " points");
        for(String s : othersPlayerInfo.keySet()){
            System.out.println("- " + s + " has " + this.othersPlayerInfo.get(s).getScore() + " points");
        }

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------");
        //this.viewCommand();
    }

    public void viewChat(){
        System.out.println("\nCHAT");
        for(ChatMessage m : messages){
            if(m.getSender().equals(nickname))
                System.out.println("[to: "+m.getRecipient()+"] " + m.getMessage());
            else if(m.getRecipient()==null)
                System.out.println("[to: everyone] " + m.getMessage());
            else if(m.getRecipient().equals(nickname))
                System.out.println("[from: "+m.getSender()+"] " + m.getMessage());
        }

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------");
        //this.viewCommand();
    }
    public void viewErrorCommand(){
        System.out.println("The command executed is wrong");

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------");
        //this.viewCommand();
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

    public void showCommand(){
        System.out.println(ConsoleColors.TEXT_YELLOW+ "\nFor obtaining the full list of command type /help while for obtaining the parameter of a specific action type /help [command]" + ConsoleColors.TEXT_RESET);
        System.out.println("Decide which command you want to do:\n");
    }

    public void viewCommand( ){
        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.println("The following lines explain the actions you can do: \n");
        System.out.println("/join");
        System.out.println("/newGame");
        System.out.println("/chooseObjective");
        System.out.println("/chooseStarterSide");
        System.out.println("/pickCardDeck");
        System.out.println("/pickCardVisible");
        System.out.println("/playCard");
        System.out.println("/chat");
        System.out.println("/myPlayerInfo");
        System.out.println("/playerInfo");
        System.out.println("/placement");
        System.out.println("/viewDeck");
        System.out.println("/viewCommonObjective");
        System.out.println("/viewSecretObjective");
        System.out.println("/viewStarterCard");
        System.out.println("/viewResourceVisibile");
        System.out.println("/viewGoldVisible");
        System.out.println("/viewChat");
        System.out.println("/currPlayer");
        System.out.println("/viewHand \n");
        System.out.println("---------------------------------------------------------------------------------------------------");
    }

    public void viewCommandParam(String str){
        System.out.println("\nThe " + str + " command has the parameters:");
        switch (str){
            case "join":
                System.out.println("/join + username");
                break;
            case "newGame":
                System.out.println("/newGame + username + numPlayers");
                break;
            case "chooseObjective":
                System.out.println("/chooseObjective + index");
                break;
            case "chooseStarterSide":
                System.out.println("/chooseStarterSide + side (0 for back / 1 for front)");
                break;
            case "pickCardDeck":
                System.out.println("/pickCardDeck +  deck (0 for gold deck / 1 for resource deck)");
                break;
            case "pickCardVisible":
                System.out.println("/pickCardVisible +  deck (0 for gold deck / 1 for resource deck) +  index  (0 for the left card and 1 for the right one)");
                break;
            case "playCard":
                System.out.println("/playCard + index (index of the card you want to play) +  angle (angel of the card you want to cover: 0 for UL / 1 for UR / 2 for DL /3  for DR)+  targetIDcard (ID of the card you want to cover) + side (0 for back / 1 for front)");
                break;
            case "chat":
                System.out.println("/chat + broadCast + message ");
                System.out.println("/chat + username (username of the receiver) + message");
                break;
            case "playerInfo":
                System.out.println("/playerInfo + username (username of the player you want to view)");
                break;
            case "viewDeck":
                System.out.println("/viewDeck + index (0 for the gold deck and 1 for resource deck)");
                break;
            default:
                System.out.println("The command executed hasn't any parameters");

                System.out.println("---------------------------------------------------------------------------------------------------");

        }
        /*
        System.out.println("/join + username");
        System.out.println("/newGame + username +  numPlayers");
        System.out.println("/chooseObjective +  index  ");
        System.out.println("/chooseStarterSide + side (0 for back / 1 for front)");
        System.out.println("/pickCardDeck +  deck (0 for gold deck / 1 for resource deck)");
        System.out.println("/pickCardVisible +  deck (0 for gold deck / 1 for resource deck) +  index  (0 for the left card and 1 for the right one)");
        System.out.println("/playCard + index (index of the card you want to play) +  angle (angel of the card you want to cover: 0 for UL / 1 for UR / 2 for DL /3  for DR)+  targetIDcard (ID of the card you want to cover) + side (0 for back / 1 for front)");
        System.out.println("/chat + broadCast + message ");
        System.out.println("/chat + username (username of the receiver) + message");
        System.out.println("/myPlayerInfo (to visualize your info)");
        System.out.println("/playerInfo + username (username of the player you want to view)");
        System.out.println("/placement (view the placement of the game)");
        System.out.println("/viewDeck + index (0 for the gold deck and 1 for resource deck)");
        System.out.println("/viewCommonObjective");
        System.out.println("/viewSecretObjective");
        System.out.println("/viewStarterCard");
        System.out.println("/viewResourceVisibile");
        System.out.println("/viewGoldVisible");
        System.out.println("/viewChat");
        System.out.println("/currPlayer");
        System.out.println("/viewHand \n");
        System.out.println("---------------------------------------------------------------------------------------------------");*/

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
