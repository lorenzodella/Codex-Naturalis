package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import java.util.HashMap;
import java.util.List;

public class AcknowledgeMessage extends Message {
    private PlayerInfo yourPlayerInfo;
    private HashMap<String, PlayerInfo> othersPlayerInfo;

    private PlayableCard goldTop;
    private PlayableCard resourceTop;
    private PlayableCard[] goldVisible;
    private PlayableCard[] resourceVisible;
    private List<PlayableCard> cards;

    private String nextPlayer;
    private boolean mustPick;

    public PlayerInfo getYourPlayerInfo() {
        return yourPlayerInfo;
    }

    public void setYourPlayerInfo(PlayerInfo yourPlayerInfo) {
        this.yourPlayerInfo = yourPlayerInfo;
    }

    public HashMap<String, PlayerInfo> getOthersPlayerInfo() {
        return othersPlayerInfo;
    }

    public void setOthersPlayerInfo(HashMap<String, PlayerInfo> othersPlayerInfo) {
        this.othersPlayerInfo = othersPlayerInfo;
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

    public List<PlayableCard> getCards() {
        return cards;
    }

    public void setCards(List<PlayableCard> cards) {
        this.cards = cards;
    }

    public String getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(String nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public boolean isMustPick() {
        return mustPick;
    }

    public void setMustPick(boolean mustPick) {
        this.mustPick = mustPick;
    }
}
