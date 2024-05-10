package it.polimi.ingsw.controller.messages;

public class StartPlayingMessage extends ObjectiveAckMessage{
    /**
     * the player that needs to start playing
     */
    private String firstPlayer;

    @Override
    public boolean shouldStartPlaying() {
        return true;
    }

    @Override
    public String getFirstPlayer() {
        return firstPlayer;
    }

    @Override
    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    @Override
    public String toString() {
        return "StartPlayingMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
