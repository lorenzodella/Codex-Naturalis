package it.polimi.ingsw.controller.messages;
/**
 * Message that needs to be sent, to all players, when a player takes an action (such as picking or playing a card),
 * to inform all others of the action that has just occurred
 */

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import java.util.HashMap;
import java.util.List;

public class AcknowledgeMessage extends Message {
    /**
     * This attribute stands for the nickname of the player whose turn is about to begin
     */
    private String nextPlayer;
    /**
     * Cards' list of the player that just picked or played a card
     */
    private List<PlayableCard> cards;
    /**
     * This attribute stands for the player's info after they played a card
     */
    private PlayerInfo yourPlayerInfo;
    /**
     * This attribute is a map that, per each player, says the player info of all other players
     */
    private HashMap<String, PlayerInfo> othersPlayerInfo;
    private String importantMessage;


    public static final String SIMPLE = "Simple";
    public static final String PLAY = "Play";
    public static final String PICK = "Pick";
    public static final String DISCONNECTION = "Disconnection";

    public String getAction(){
        return AcknowledgeMessage.SIMPLE;
    }

    public void setDecksModified(boolean decksModified) {
    }

    public boolean areDecksModified() {
        return false;
    }

    public String getImportantMessage() {
        return importantMessage;
    }

    public void appendImportantMessage(String importantMessage) {
        if(this.importantMessage==null)
            this.importantMessage = importantMessage;
        else
            this.importantMessage += "\n" + importantMessage;
    }

    public PlayerInfo getYourPlayerInfo() {
        return yourPlayerInfo;
    }

    public void setYourPlayerInfo(PlayerInfo yourPlayerInfo) {
        this.yourPlayerInfo = yourPlayerInfo;
    }

    public HashMap<String, PlayerInfo> getOthersPlayerInfo() {
        return othersPlayerInfo;
    }

    public void setOthersPlayerInfo(HashMap<String, PlayerInfo> othersPlayerInfo) {
        this.othersPlayerInfo = othersPlayerInfo;
    }

    public PlayableCard getGoldTop() {
        return null;
    }

    public void setGoldTop(PlayableCard goldTop) {

    }

    public PlayableCard getResourceTop() {
        return null;
    }

    public void setResourceTop(PlayableCard resourceTop) {

    }

    public PlayableCard[] getGoldVisible() {
        return null;
    }

    public void setGoldVisible(PlayableCard[] goldVisible) {

    }

    public PlayableCard[] getResourceVisible() {
        return null;
    }

    public void setResourceVisible(PlayableCard[] resourceVisible) {

    }

    public List<PlayableCard> getCards() {
        return cards;
    }

    public void setCards(List<PlayableCard> cards) {
        this.cards = cards;
    }

    public String getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(String nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public boolean mustPick() {
        return false;
    }

    public void setMustPick(boolean mustPick) {

    }

    public String getType(){
        return Message.ACKNOWLEDGE;
    }

    @Override
    public String toString() {
        return "AcknowledgeMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
