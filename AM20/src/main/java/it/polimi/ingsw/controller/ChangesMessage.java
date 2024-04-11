package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.HashMap;
import java.util.List;

public class ChangesMessage extends  Message{
    private String result;
    //roba del campo da gioco
    private PlayableCard goldTop;
    private PlayableCard resourceTop;
    private PlayableCard[] goldVisible;
    private PlayableCard[] resourceVisible;
    private ObjectiveCard[] commonObjective;

    //roba del player singolo
    private StarterCard starterCard;
    private ObjectiveCard[] secretObjective;
    private List<PlayableCard> cards;
    private boolean isYourTurn;


    private PlayerInfo yourPlayerInfo;
    private HashMap<String, PlayerInfo> othersPlayerInfo;


    private String winnerNickname;


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

    public ObjectiveCard[] getCommonObjective() {
        return commonObjective;
    }

    public void setCommonObjective(ObjectiveCard[] commonObjective) {
        this.commonObjective = commonObjective;
    }

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public void setStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
    }

    public ObjectiveCard[] getSecretObjective() {
        return secretObjective;
    }

    public void setSecretObjective(ObjectiveCard[] secretObjective) {
        this.secretObjective = secretObjective;
    }

    public List<PlayableCard> getCards() {
        return cards;
    }

    public void setCards(List<PlayableCard> cards) {
        this.cards = cards;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }

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

    public String getWinnerNickname() {
        return winnerNickname;
    }

    public void setWinnerNickname(String winnerNickname) {
        this.winnerNickname = winnerNickname;
    }


}
