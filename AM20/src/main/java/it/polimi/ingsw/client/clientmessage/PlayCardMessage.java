package it.polimi.ingsw.client.clientmessage;
/**
 * Message from the client to the server as a formal request to play a card
 */
public class PlayCardMessage extends ClientMessage{
    /**
     * the nickname of the player that wants to play a card
     */
    String playerNickname;
    /**
     * index of the card that the player wants to play.
     * could be 0,1, or 2 based on which card, of the 3 item array, teh player wants to play
     */
    int cardIndex;
    /**
     * angle that the player wants to cover by playing the card
     */
    int angle;
    /**
     * id of the card that the player wants to cover by playing the card
     */
    String targetID;
    /**
     * the side of teh card that the player wants to play with
     */
    int side;

    public PlayCardMessage(String playerNickname, int cardIndex, int angle, String targetID, int side) {
        this.playerNickname = playerNickname;
        this.cardIndex = cardIndex;
        this.angle = angle;
        this.targetID = targetID;
        this.side = side;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public String getTargetID() {
        return targetID;
    }

    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public String getAction(){
        return PLAY_CARD;
    }
}
