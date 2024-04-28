package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.List;

public class ConnectionAckMessage extends Message{

    public boolean doesGameStarts() {
        return false;
    }


    public PlayableCard getGoldTop() {
        return null;
    }

    public void setGoldTop(PlayableCard goldTop) {

    }

    public PlayableCard getResourceTop() {
        return null;
    }

    public void setResourceTop(PlayableCard resourceTop) {

    }

    public PlayableCard[] getGoldVisible() {
        return new PlayableCard[0];
    }

    public void setGoldVisible(PlayableCard[] goldVisible) {

    }

    public PlayableCard[] getResourceVisible() {
        return new PlayableCard[0];
    }

    public void setResourceVisible(PlayableCard[] resourceVisible) {

    }

    public StarterCard getStarterCard() {
        return null;
    }

    public void setStarterCard(StarterCard starterCard) {

    }

    public List<PlayableCard> getInitialCards() {
        return null;
    }

    public void setInitialCards(List<PlayableCard> initialCards) {

    }

    public PlayerInfo getPlayerInfo() {
        return null;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {

    }

    @Override
    public String toString() {
        return "ConnectionAckMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
