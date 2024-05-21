package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.HashMap;
import java.util.List;

public class StartGameMessage extends ConnectionAckMessage {

    /**
     * This attribute stands for the card that's on the top of the gold deck
     */
    private PlayableCard goldTop;
    /**
     * This attribute stands for the card that's on the top of the resource deck
     */
    private PlayableCard resourceTop;
    /**
     * This attribute stands for the two gold visible cards
     */
    private PlayableCard[] goldVisible;
    /**
     * This attribute stands for the two resoruce visible cards
     */
    private PlayableCard[] resourceVisible;
    /**
     * This attribute stands for the player's starter card
     */
    private StarterCard starterCard;
    /**
     * This attribute stands for the player's initial cards
     */
    private List<PlayableCard> initialCards;
    /**
     * The player's playerinfo
     */
    private PlayerInfo playerInfo;
    /**
     * The map that, per each player, says the other players' info
     */
    private HashMap<String, PlayerInfo> othersPlayerInfo;


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
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    @Override
    public HashMap<String, PlayerInfo> getOthersPlayerInfo() {
        return othersPlayerInfo;
    }

    @Override
    public void setOthersPlayerInfo(HashMap<String, PlayerInfo> othersPlayerInfo) {
        this.othersPlayerInfo = othersPlayerInfo;
    }

    @Override
    public String toString() {
        return "StartGameMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
