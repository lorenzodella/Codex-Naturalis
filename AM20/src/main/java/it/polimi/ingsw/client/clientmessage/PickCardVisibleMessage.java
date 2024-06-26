package it.polimi.ingsw.client.clientmessage;
/**
 * Message from the client to the server as a formal request to draw a visible card
 */
public class PickCardVisibleMessage extends ClientMessage{
    /**
     * the nickname of the player that wants to pick one of the two visible cards
     */
    String playerNickname;
    /**
     * deck of the visible card that the player wants to pick from.
     * could be 1 or 0
     * resource card deck : 0
     * gold card deck: 1
     */
    int deck;
    /**
     * index of one of the two visible cards that player wants to pick
     */
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
        return PICK_CARD_VISIBLE;
    }
}
