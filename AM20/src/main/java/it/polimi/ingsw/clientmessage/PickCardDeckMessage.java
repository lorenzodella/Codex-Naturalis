package it.polimi.ingsw.clientmessage;

import it.polimi.ingsw.controller.messages.AcknowledgeMessage;

public class PickCardDeckMessage extends ClientMessage{
    /**
     * nickname of the player that needs to pick a card now
     */
    String playerNickname;
    /**
     * The specific deck where the player needs to pick a card from.
     * Could be 0 or 1.
     * resource card deck : 0
     * gold card deck: 1
     */
    int deck;

    public PickCardDeckMessage(String playerNickname, int deck) {
        this.playerNickname = playerNickname;
        this.deck = deck;
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

    public String getAction(){
        return PickCardDeckMessage.PICK_CARD_DECK;
    }
}
