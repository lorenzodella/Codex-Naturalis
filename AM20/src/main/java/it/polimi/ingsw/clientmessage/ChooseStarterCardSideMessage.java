package it.polimi.ingsw.clientmessage;

import it.polimi.ingsw.controller.messages.AcknowledgeMessage;

public class ChooseStarterCardSideMessage extends ClientMessage{
    String nickname;
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
