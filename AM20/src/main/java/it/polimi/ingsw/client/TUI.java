package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.util.XMLparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    //TODO tia metodi per stampare le cose

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
                        System.out.println(this.consoleColors.TEXT_RESET+ "The UL is not coverable");
                    if(i==1)
                        System.out.println(this.consoleColors.TEXT_RESET+ "The UR is not coverable");
                    if(i==2)
                        System.out.println(this.consoleColors.TEXT_RESET+ "The DL is not coverable");
                    if(i==3)
                        System.out.println(this.consoleColors.TEXT_RESET+ "The DR is not coverable");

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


    public void viewCommonObjective(){


    }


    public void viewSecretObjective(){

    }


    public void viewHandCards(){

    }

    public void viewGoldTop(){

    }

    public void viewResourceTop(){

    }

    public void viewGoldVisible(){

    }

    public void viewResourceVisible(){

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



}
