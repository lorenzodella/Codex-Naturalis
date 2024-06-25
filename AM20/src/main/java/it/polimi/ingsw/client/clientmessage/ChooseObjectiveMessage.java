package it.polimi.ingsw.client.clientmessage;

public class ChooseObjectiveMessage extends ClientMessage{
    /**
     * nickname of the player
     */
    String nickname;
    /**
     * index of the chosen secret objective that the player just chose (could be 0 or 1 since
     * the player needs to decide between two different objectives which are found in a 2 item array)
     */
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
