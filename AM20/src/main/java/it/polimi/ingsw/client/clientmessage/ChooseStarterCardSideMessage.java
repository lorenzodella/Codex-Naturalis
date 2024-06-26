package it.polimi.ingsw.client.clientmessage;
/**
 * Message from the client to the server as a formal request to choose the side of the starter card
 */
public class ChooseStarterCardSideMessage extends ClientMessage{
    /**
     * nickname of the player
     */
    String nickname;
    /**
     * Side of the starter card that the player just chose.
     * front 1
     * back 0
     */
    int side;

    public ChooseStarterCardSideMessage(String nickname, int side) {
        this.nickname = nickname;
        this.side = side;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public String getAction(){
        return ChooseStarterCardSideMessage.CHOOSE_STARTERCARD_SIDE;
    }
}
