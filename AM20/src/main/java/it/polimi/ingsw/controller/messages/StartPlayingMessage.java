package it.polimi.ingsw.controller.messages;

public class StartPlayingMessage extends ObjectiveAckMessage{
    private String firstPlayer;

    @Override
    public String getFirstPlayer() {
        return firstPlayer;
    }

    @Override
    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
}
