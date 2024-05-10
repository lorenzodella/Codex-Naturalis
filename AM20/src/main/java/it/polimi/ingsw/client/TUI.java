package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.HashMap;
import java.util.List;

public class TUI implements UIManager {
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

    public TUI(){
        this.mapMsg = new HashMap<>();
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
    }

    @Override
    public void updateCommonObjectives(ObjectiveCard[] commonObjectives) {
        this.commonObjectives = commonObjectives;
    }

    @Override
    public void updateStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
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

    public static void writeCommandParam( ){
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

        writeCommandParam();
        myTui.waitSeconds(5);
        myTui.clearTerminal(10);

    }

    public void  viewPlayerInfo(){

    }

    public void viewOtherPlayerInfo(String username){

    }

    public void displayPlacement(){

    }
}
