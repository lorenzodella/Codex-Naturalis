package it.polimi.ingsw.controller.messages;
/**
 * Message that needs to be sent, to all players, when all players have chosen their objective i order to inform them
 * that they can finally start playing the game
 */
public class StartPlayingMessage extends ObjectiveAckMessage{
    /**
     * the first player of the round
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
