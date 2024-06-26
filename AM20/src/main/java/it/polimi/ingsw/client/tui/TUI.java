package it.polimi.ingsw.client.tui;

import it.polimi.ingsw.client.tui.clientcard.*;
import it.polimi.ingsw.client.UIManager;
import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.controller.messages.ChatMessage;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.objective.*;
import it.polimi.ingsw.model.cards.playable.*;

import java.util.*;

/**
 * This class is the Text User Interface of the client side of the application.
 * It allows the user to interact with the game through the terminal.
 */
public class TUI implements UIManager {

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


    public TUI(){
        this.messages = new LinkedList<>();
        this.cards = new ArrayList<>();
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    public HashMap<String, PlayerInfo> getOthersPlayerInfo() {
        return othersPlayerInfo;
    }

    /**
     * This method allows to show the user that he is connected to the server
     */
    @Override
    public void showConnection() {
        System.out.println("Waiting for other players");
        this.showCommand();
    }

    /**
     * This method allows to show the user that the game is starting
     */
    @Override
    public void showStartGame() {
        System.out.println("The game is starting now");
    }

    /**
     * This method allows to show the user that someone is reconnected to the server
     */
    @Override
    public void showReconnection(String result, boolean isJoining) {
        System.out.println(result);
    }

    @Override
    public void showStarterCard() {

    }

    @Override
    public void showStartChoosingObjective() {

    }

    @Override
    public void showObjectiveMessage() {

    }

    @Override
    public void showStartPlaying() {

    }

    @Override
    public void showPickAck() {

    }

    @Override
    public void showPlayAck() {

    }


    /**
     * This method updates the cards in your hand and it calls a method to display the information about them
     * @param cards list of the card that the player can play
     */
    @Override
    public void updateCards(List<PlayableCard> cards) {
        if(cards!=null){
            this.cards = cards;
            viewHandCards();
        }

    }

    /**
     * This method prints to the terminal the receipt of a message. In addition, it calls a method to display the history of the chat with others players
     * @param msg message received by the server
     */
    @Override
    public void updateChatMessage(ChatMessage msg) {
        this.messages.add(msg);
        if(!msg.getSender().equals(nickname) && (msg.getRecipient()==null || msg.getRecipient().equals(nickname)))
            System.out.println("\nYou received a message");
        viewChat();
        this.showCommand();
    }

    /**
     * The first time this method receive an arrayList of two elements, then the player decide which one he prefers and the second time
     * the arrayList is made of a secretObjective (the one he had chosen). In addition, it calls a method to display to the user the information of the secretObjective
     * @param secretObjectives of the player
     */
    @Override
    public void updateSecretObjectives(ArrayList<ObjectiveCard> secretObjectives) {
        if(secretObjectives!=null) {
            this.secretObjectives = secretObjectives;
            viewSecretObjective();
        }
    }


    /**
     * This method updates the information about the gold deck
     * @param goldTop gold card on the top of the deck
     * @param goldVisible array of the two visible gold cards
     */
    @Override
    public void updateGold(PlayableCard goldTop, PlayableCard[] goldVisible) {

        this.goldTop = goldTop;
        this.goldVisible = goldVisible;
        //viewGold();

    }


    /**
     * This method updates the information about the resource deck
     * @param resourceTop resource card on the top of the deck
     * @param resourceVisible array of the two visible resource cards
     */
    @Override
    public void updateResource(PlayableCard resourceTop, PlayableCard[] resourceVisible) {
        this.resourceTop = resourceTop;
        this.resourceVisible = resourceVisible;
        //viewResource();
    }


    /**
     * This method updates the info of the player (color chosen, score, map of the player table and occurrences of every kingdom and objects)
     * and it prints on the terminal this info
     * @param yourPlayerInfo info of the player
     */
    @Override
    public void updateYourPlayerInfo(PlayerInfo yourPlayerInfo) {
        if(yourPlayerInfo!=null) {
            this.yourPlayerInfo = yourPlayerInfo;
            viewPlayerInfo();
        }
    }

    /**
     * This method allows to update the info of the players
     * @param otherPlayerInfo map where for each username of the other players, you can obtain the player's info
     *                        (color chosen, score, map of the player table and occurrences of every kingdom and objects)
     */
    @Override
    public void updateOtherPlayerInfo(HashMap<String, PlayerInfo> otherPlayerInfo) {
        if(otherPlayerInfo!=null) {
            if(othersPlayerInfo==null)
                othersPlayerInfo = otherPlayerInfo;
            else {
                for (String nickname : otherPlayerInfo.keySet())
                    othersPlayerInfo.put(nickname, otherPlayerInfo.get(nickname));
            }
        }
    }

    /**
     * This method allows to update the commonObjectives and to display them
     * @param commonObjectives array of the two common objective
     */
    @Override
    public void updateCommonObjectives(ObjectiveCard[] commonObjectives) {
        if(commonObjectives!=null){
            this.commonObjectives = commonObjectives;
            viewCommonObjective();
        }

    }

    /**
     * This method allows to update and to display the starter card of the player
     * @param starterCard starter card of the player
     */
    @Override
    public void updateStarterCard(StarterCard starterCard) {
        if(starterCard!=null){
            this.starterCard = starterCard;
            viewStarterCard();
        }

    }

    /**
     * This method allows to print the result of a received message
     * @param result string that inform about the result
     */
    @Override
    public void showResult(String result) {
        System.out.println(result);
    }

    /**
     * This method allows to print the important message of a received message
     * @param result string that inform about the result
     * @param importantMessage string that inform about the important message
     */
    @Override
    public void showImportantMessage(String result, String importantMessage) {
        if(importantMessage!=null) {
            //System.out.println(ConsoleColors.TEXT_BG_GREEN+result+ConsoleColors.TEXT_RESET);
            System.out.println(ConsoleColors.TEXT_BG_GREEN+importantMessage+ConsoleColors.TEXT_RESET);
        }
    }

    /**
     * This method allows to control if it's player turn or not. If it is so, the method show the actual information of the gold and resource deck
     * (both the top card and the two visible cards).
     * @param nextPlayer username of the next player that has to play
     */
    @Override
    public void showNextTurn(String nextPlayer){
        if(nextPlayer!=null) {
            currPlayer = nextPlayer;
            if (nextPlayer.equals(nickname)) {
                viewPlayerInfo();
                viewHandCards();

                System.out.println("Is your turn");
            } else {
                System.out.println("Is " + nextPlayer + "'s turn");
            }
        }
        else{
            updateStopGame();
        }
    }

    /**
     * This method allows the client to inform the user that the game is ended and that if he wants to play again,
     * he has to relaunch the application
     */
    @Override
    public void updateStopGame() {
        nickname = null;
        System.out.println(ConsoleColors.TEXT_BG_GREEN+"Game is over."+ConsoleColors.TEXT_RESET);
        System.out.println(ConsoleColors.TEXT_BG_GREEN+"You have to relaunch application to play again"+ConsoleColors.TEXT_RESET);
    }

    /**
     * This method says to the user that he has to pick a card
     */
    @Override
    public void showMustPick(){
        viewResource();
        viewGold();
        System.out.println("You have to pick a card");
    }

    /**
     * This method allows to print the content of the error message
     * @param error string that inform about the error
     */
    @Override
    public void showError(String error){
        System.err.println(error);
        showCommand();
    }

    /**
     * This method prints the current player that has to play/is playing
     */
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


    /**
     * This method allows to print the specific information of the player's starter card through the draw() method present in the
     * "copy" card (starterCardClient) of the "real" starterCard that is created in the server and received through the SKT/RMI
     */
    public void viewStarterCard(){
        StarterCardClient starterCardClient = new StarterCardClient(starterCard);
        starterCardClient.draw();
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("You have received the starter card and now you should choose the side");

    }

    /**
     * This method allows to print the specific information of the player's hand card calling the draw() method present
     * in the "copy" card (...Client) of the "real" card created in the server and received through the SKT/RMI
     */
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

    /**
     * This method allows to print the specific information of the common objectives calling the draw() method present
     * in the "copy" card (...Client) of the "real" card created in the server and received through the SKT/RMI
     */
    public int viewCommonObjective(){

        try {
            if(commonObjectives.length>0);
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


        }catch (NullPointerException e ){
           this.showError("The common objective have not been distributed yet ");
           return 0;
        }

    return 1;
    }

    /**
     * This method allows to print the specific information of the player's secret objective calling the draw() method present
     * in the "copy" card (...Client) of the "real" card created in the server and received through the SKT/RMI
     */
    public int viewSecretObjective(){
        try{
            if(secretObjectives.size()>0) {
                System.out.println(ConsoleColors.TEXT_BLUE + "SECRET OBJECTIVE: \n" + ConsoleColors.TEXT_RESET);
                //scorro array dei common objectives
                for (int i = 0; i < this.secretObjectives.size(); i++) {

                    try {
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

                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------\n");
            }
        }catch (NullPointerException e){
            this.showError("The secret objective have not been distributed yet ");
            return 0;
        }

        return 1;
    }

    /**
     * This method allows to print the specific information of the gold deck (both the top card and the visible cards)
     * using the draw() method as before
     */
    public void viewGold(){
        System.out.println(ConsoleColors.TEXT_BLUE+"GOLD DECK:"+ConsoleColors.TEXT_RESET);
        if(goldTop == null && goldVisible == null){
            System.out.println("There aren't any gold card available\n");
        }else {
            if(goldTop!=null){
                System.out.print("The card that's now on top of the gold deck is a ");
                //KINGDOM
                if(this.goldTop.getCardKingdom().equals(Kingdom.Fungi))
                    System.out.print(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
                else if(this.goldTop.getCardKingdom().equals(Kingdom.Insect))
                    System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT"+ConsoleColors.TEXT_RESET);
                else if(this.goldTop.getCardKingdom().equals(Kingdom.Plant))
                    System.out.print(ConsoleColors.TEXT_GREEN + "PLANT"+ConsoleColors.TEXT_RESET);
                else
                    System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL"+ConsoleColors.TEXT_RESET);
                System.out.println(ConsoleColors.TEXT_RESET + " card \n");

            }else {
                System.out.println("The gold deck is empty\n");
            }


            if(this.goldVisible !=null){
                System.out.println("The gold cards that are now visible on the table are the following: \n");
                for(int i=0; i < this.goldVisible.length; i++) {
                    if(this.goldVisible[i]!=null){
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
                    }else{
                        if(i == 0)
                            System.out.println("The first card is empty\n");
                        else
                            System.out.println("The second card is empty\n");
                    }
                }

            }else
                System.out.println("There are no visible gold cards \n");
        }
    }

    /**
     * This method allows to print the specific information of the resource deck (both the top card and the visible cards)
     * using the draw() method as before
     */
    public void viewResource(){
        System.out.println(ConsoleColors.TEXT_BLUE+"RESORUCE DECK" + ConsoleColors.TEXT_RESET);
        if(resourceTop == null && resourceVisible == null){
            System.out.println("There aren't any resource card available");
        }else {
            if(resourceTop != null){
                System.out.print("The card that's now on top of the resource deck is a ");
                if(this.resourceTop.getCardKingdom().equals(Kingdom.Fungi))
                    System.out.print(ConsoleColors.TEXT_RED + "FUNGI"+ ConsoleColors.TEXT_RESET);
                else if(this.resourceTop.getCardKingdom().equals(Kingdom.Insect))
                    System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT"+ConsoleColors.TEXT_RESET);
                else if(this.resourceTop.getCardKingdom().equals(Kingdom.Plant))
                    System.out.print(ConsoleColors.TEXT_GREEN + "PLANT"+ConsoleColors.TEXT_RESET);
                else
                    System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL"+ConsoleColors.TEXT_RESET);
                System.out.println(ConsoleColors.TEXT_RESET + " card \n");
            }else
                System.out.println("The recource deck is empty\n");



            if(this.resourceVisible!=null){
                System.out.println("The resource cards that are now visible on the table are the following: \n");

                for(int i=0; i < this.resourceVisible.length; i++) {
                    if(this.resourceVisible[i]!=null){
                        if (i == 0) {
                            System.out.print("The first card");
                        } else
                            System.out.print("The second card");

                        ResourceCardClient resourceCardClient = new ResourceCardClient((ResourceCard) this.resourceVisible[i]);
                        resourceCardClient.draw();
                    }else{
                        if(i == 0)
                            System.out.println("The first card is empty");
                        else
                            System.out.println("The second card is empty");
                    }

                }
            }else
                System.out.println("There are no visible resource cards \n");
        }
    }


    /**
     * This method refreshes the terminal, prints the title of the game and the information of the user (score, number of resources and objects
     * and the board)
     */
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
            System.out.println("The player has the following board:");
            CardPrinter.printMap(this.yourPlayerInfo.getMap());
        }

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------\n");
        //this.viewCommand();
    }

    /**
     * This method prints the information of the other player as for the user's player
     * @param username this is the username of the player that the user of the client wants to see the info
     */
    public void viewOtherPlayerInfo(String username){
//        this.printTitle();
        try {
            username = username.substring(0,1).toUpperCase() + username.substring(1);
            this.othersPlayerInfo.get(username).getScore();
            System.out.println("The following information are the one of " + username + " game");
            System.out.println(username + " has done " + this.othersPlayerInfo.get(username).getScore() + " points");

            //TODO
            //System.out.println("The common objective "); oppure chiamata al metodo viewCommonObjective
            System.out.println(username + " has:");
            System.out.print("- ");
            System.out.print(ConsoleColors.TEXT_GREEN + "PLANT: " + ConsoleColors.TEXT_RESET);
            System.out.println(ConsoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Plant));
            System.out.print(ConsoleColors.TEXT_RESET + "- ");
            System.out.print(ConsoleColors.TEXT_PURPLE + "INSECT: " + ConsoleColors.TEXT_RESET);
            System.out.println(ConsoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Insect));
            System.out.print(ConsoleColors.TEXT_RESET + "- ");
            System.out.print(ConsoleColors.TEXT_RED + "FUNGI: " + ConsoleColors.TEXT_RESET);
            System.out.println(ConsoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Fungi));
            System.out.print(ConsoleColors.TEXT_RESET + "- ");
            System.out.print(ConsoleColors.TEXT_CYAN + "ANIMAL: " + ConsoleColors.TEXT_RESET);
            System.out.println(ConsoleColors.TEXT_RESET + this.othersPlayerInfo.get(username).getStats().getNumberOfResources(Kingdom.Animal));
            System.out.println("- QUILL: " + this.othersPlayerInfo.get(username).getStats().getNumberOfObjects(SpecialObject.Quill));
            System.out.println("- INKWELL: " + this.othersPlayerInfo.get(username).getStats().getNumberOfObjects(SpecialObject.Inkwell));
            System.out.println("- MANUSCRIPT: " + this.othersPlayerInfo.get(username).getStats().getNumberOfObjects(SpecialObject.Manuscript));

            if (this.othersPlayerInfo.get(username).getMap() != null) {
                System.out.println("The player has the following board:");
                CardPrinter.printMap(this.othersPlayerInfo.get(username).getMap());
            }
        }catch (NullPointerException e){
            System.out.println("The player is not in the game");
        }

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------");
        //this.viewCommand();
    }

    /**
     * This method prints the score of each player
     */
    public void viewPlacement(){
        System.out.println("The placement has the following order: ");
        System.out.print(ConsoleColors.colorFromPawnColor(yourPlayerInfo.getColor()));
        System.out.print("- " + this.nickname + " has " + this.yourPlayerInfo.getScore() + " points");
        System.out.println(ConsoleColors.TEXT_RESET);
        for(String s : othersPlayerInfo.keySet()){
            System.out.print(ConsoleColors.colorFromPawnColor(othersPlayerInfo.get(s).getColor()));
            System.out.print("- " + s + " has " + this.othersPlayerInfo.get(s).getScore() + " points");
            System.out.println(ConsoleColors.TEXT_RESET);
        }

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------");
        //this.viewCommand();
    }

    /**
     * This method allows the user to see all the chronology of the chat (it says the sender and the message itself)
     */
    public void viewChat(){
        System.out.println("\nCHAT");
        if(messages.isEmpty()){
            System.out.println("There aren't any messages yet");
        }else {
            for(ChatMessage m : messages){
                if(m.getSender().equals(nickname)){
                    if(m.getRecipient()==null)
                        System.out.println("[to: everyone] " + m.getMessage());
                    else
                        System.out.println("[to: "+
                                ConsoleColors.colorFromPawnColor(othersPlayerInfo.get(m.getRecipient()).getColor())+
                                m.getRecipient()+
                                ConsoleColors.TEXT_RESET+
                                "] " + m.getMessage());
                }
                else if(m.getRecipient()==null || m.getRecipient().equals(nickname))
                    System.out.println("[from: "+
                            ConsoleColors.colorFromPawnColor(othersPlayerInfo.get(m.getSender()).getColor())+
                            m.getSender()+
                            ConsoleColors.TEXT_RESET+
                            "] " + m.getMessage());
            }

            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------");


        }
        //this.viewCommand();
    }

    /**
     * This method permits the user to understand that the command wrote is wrong (the type of the command or/and the parameters)
     */
    public void viewErrorCommand(){
        System.out.println("The command executed is wrong");
        showCommand();
    }


    /**
     * This method prints the ascii art of the game's title
     */
    public void printTitle(){
        System.out.println("   ____          _             _   _       _                   _ _     ");
        System.out.println("  / ___|___   __| | _____  __ | \\ | | __ _| |_ _   _ _ __ __ _| (_)___ ");
        System.out.println(" | |   / _ \\ / _` |/ _ \\ \\/ / |  \\| |/ _` | __| | | | '__/ _` | | / __|");
        System.out.println(" | |__| (_) | (_| |  __/>  <  | |\\  | (_| | |_| |_| | | | (_| | | \\__ \\");
        System.out.println("  \\____\\___/ \\__,_|\\___/_/\\_\\ |_| \\_|\\__,_|\\__|\\__,_|_|  \\__,_|_|_|___/");
        System.out.println("                                                                       ");
        System.out.println("\n");
    }

    /**
     * This method is used to say to the user that he is able to do an action
     */
    public void showCommand(){
        System.out.println(ConsoleColors.TEXT_YELLOW+ "\nFor obtaining the full list of command type /help while for obtaining the parameter of a specific action type /help [command]" + ConsoleColors.TEXT_RESET);
        System.out.println("Decide which command you want to do:");
    }

    /**
     * This method lists all the different action the user can do
     */
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
        System.out.println("/viewChat");
        System.out.println("/currPlayer");
        System.out.println("/viewHand \n");
        System.out.println("---------------------------------------------------------------------------------------------------");
    }

    /**
     * This method shows the user the parameters of the str command
     * @param str command that the user wants to know the parameters
     */
    public void viewCommandParam(String str){
        System.out.println("\nThe " + str + " command has the parameters:");
        switch (str){
            case "join":
                System.out.println("/join + username + color (red / blue / green / yellow)");
                break;
            case "newGame":
                System.out.println("/newGame + username + color (red / blue / green / yellow) + numPlayers");
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
                System.out.println("/chat + broadcast + message (you have to write the message between \"\")");
                System.out.println("/chat + username (username of the receiver) + message (you have to write the message between \"\")");
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
    }

}
