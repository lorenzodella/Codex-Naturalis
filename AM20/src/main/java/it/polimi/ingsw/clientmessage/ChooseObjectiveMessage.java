package it.polimi.ingsw.clientmessage;

import it.polimi.ingsw.controller.messages.AcknowledgeMessage;

public class ChooseObjectiveMessage extends ClientMessage{
    String nickname;
    int index;

    public ChooseObjectiveMessage(String nickname, int index) {
        this.nickname = nickname;
        this.index = index;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAction(){
        return ChooseObjectiveMessage.CHOOSE_OBJECTIVE;
    }
}
