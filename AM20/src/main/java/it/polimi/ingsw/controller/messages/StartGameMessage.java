package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.HashMap;
import java.util.List;

public class StartGameMessage extends ConnectionAckMessage {

    private PlayableCard goldTop;
    private PlayableCard resourceTop;
    private PlayableCard[] goldVisible;
    private PlayableCard[] resourceVisible;
    private StarterCard starterCard;
    private List<PlayableCard> initialCards;


    @Override
    public boolean doesGameStarts() {
        return true;
    }

    public PlayableCard getGoldTop() {
        return goldTop;
    }

    public void setGoldTop(PlayableCard goldTop) {
        this.goldTop = goldTop;
    }

    public PlayableCard getResourceTop() {
        return resourceTop;
    }

    public void setResourceTop(PlayableCard resourceTop) {
        this.resourceTop = resourceTop;
    }

    public PlayableCard[] getGoldVisible() {
        return goldVisible;
    }

    public void setGoldVisible(PlayableCard[] goldVisible) {
        this.goldVisible = goldVisible;
    }

    public PlayableCard[] getResourceVisible() {
        return resourceVisible;
    }

    public void setResourceVisible(PlayableCard[] resourceVisible) {
        this.resourceVisible = resourceVisible;
    }

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public void setStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
    }

    public List<PlayableCard> getInitialCards() {
        return initialCards;
    }

    public void setInitialCards(List<PlayableCard> initialCards) {
        this.initialCards = initialCards;
    }

    public PlayerInfo getPlayerInfo() {
        return null;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {

    }

    @Override
    public HashMap<String, PlayerInfo> getOthersPlayerInfo() {
        return null;
    }

    @Override
    public void setOthersPlayerInfo(HashMap<String, PlayerInfo> othersPlayerInfo) {
    }

    @Override
    public String toString() {
        return "StartGameMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
