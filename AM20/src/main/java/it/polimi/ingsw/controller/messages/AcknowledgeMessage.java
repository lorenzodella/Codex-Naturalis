package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import java.util.HashMap;
import java.util.List;

public class AcknowledgeMessage extends Message {
    private String nextPlayer;
    private List<PlayableCard> cards;
    private int numOfConnectedPlayers;

    public static final String DISCONNECTION = "Disconnection";
    public static final String PLAY = "Play";
    public static final String PICK = "Pick";

    public String getAction(){
        return AcknowledgeMessage.DISCONNECTION;
    }

    public int getNumOfConnectedPlayers() {
        return numOfConnectedPlayers;
    }

    public void setNumOfConnectedPlayers(int numOfConnectedPlayers) {
        this.numOfConnectedPlayers = numOfConnectedPlayers;
    }

    public PlayerInfo getYourPlayerInfo() {
        return null;
    }

    public void setYourPlayerInfo(PlayerInfo yourPlayerInfo) {

    }

    public HashMap<String, PlayerInfo> getOthersPlayerInfo() {
        return null;
    }

    public void setOthersPlayerInfo(HashMap<String, PlayerInfo> othersPlayerInfo) {

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

    @Override
    public String toString() {
        return "AcknowledgeMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
