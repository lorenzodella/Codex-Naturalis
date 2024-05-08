package it.polimi.ingsw.clientmessage;

import it.polimi.ingsw.controller.messages.AcknowledgeMessage;

public class PickCardVisibleMessage extends ClientMessage{
    String playerNickname;
    int deck;
    int index;

    public PickCardVisibleMessage(String playerNickname, int deck, int index) {
        this.playerNickname = playerNickname;
        this.deck = deck;
        this.index = index;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public int getDeck() {
        return deck;
    }

    public void setDeck(int deck) {
        this.deck = deck;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAction(){
        return PickCardVisibleMessage.PICK_CARD_VISIBLE;
    }
}
