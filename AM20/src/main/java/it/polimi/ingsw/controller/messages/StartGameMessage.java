package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.List;

public class StartGameMessage extends Message {

    private boolean gameStarts;
    private PlayableCard goldTop;
    private PlayableCard resourceTop;
    private PlayableCard[] goldVisible;
    private PlayableCard[] resourceVisible;
    private StarterCard starterCard;
    private List<PlayableCard> initialCards;

    public boolean isGameStarts() {
        return gameStarts;
    }

    public void setGameStarts(boolean gameStarts) {
        this.gameStarts = gameStarts;
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
}
