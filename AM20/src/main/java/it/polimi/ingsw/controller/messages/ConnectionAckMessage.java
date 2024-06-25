package it.polimi.ingsw.controller.messages;

import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConnectionAckMessage extends Message{

    /**
     * This is the username of the player that connect or reconnect to the game
     */
    private String nickname;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean doesGameStarts() {
        return false;
    }


    public boolean isReconnection() {
        return false;
    }


    public ArrayList<ObjectiveCard> getSecretObjective() {
        return null;
    }

    public void setSecretObjective(ArrayList<ObjectiveCard> secretObjective) {
    }

    public ObjectiveCard[] getCommonObjectives() {
        return null;
    }

    public void setCommonObjectives(ObjectiveCard[] commonObjectives) {
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

    public HashMap<String, PlayerInfo> getOthersPlayerInfo() {
        return null;
    }

    public void setOthersPlayerInfo(HashMap<String, PlayerInfo> othersPlayerInfo) {
    }

    @Override
    public String getType() {
        return Message.CONNECTIONACK;
    }

    @Override
    public String toString() {
        return "ConnectionAckMessage{" +
                "result='" + getResult() + '\'' +
                '}';
    }
}
