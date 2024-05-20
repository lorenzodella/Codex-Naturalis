package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.UIManager;
import it.polimi.ingsw.client.gui.gameview.GameFrame;
import it.polimi.ingsw.client.gui.listeners.JoinGameListener;
import it.polimi.ingsw.client.gui.listeners.MapListener;
import it.polimi.ingsw.client.gui.listeners.NewGameListener;
import it.polimi.ingsw.client.gui.listeners.YourCardsListener;
import it.polimi.ingsw.client.gui.startscreen.StartScreenFrame;
import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.controller.messages.ChatMessage;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUI implements UIManager {

    GameFrame gameFrame;
    StartScreenFrame startScreenFrame;

    public GUI() {
        //crea schermata iniziale
        startScreenFrame = new StartScreenFrame();
    }

    @Override
    public void setNickname(String nickname) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void updateCards(List<PlayableCard> cards) {
    }

    @Override
    public void updateChatMessage(ChatMessage msg) {

    }

    @Override
    public void updateSecretObjectives(ArrayList<ObjectiveCard> secretObjectives) {

    }

    @Override
    public void updateGoldTop(PlayableCard goldTop) {

    }

    @Override
    public void updateResourceTop(PlayableCard resourceTop) {

    }

    @Override
    public void updateGoldVisible(PlayableCard[] goldVisible) {

    }

    @Override
    public void updateResourceVisible(PlayableCard[] resourceVisible) {

    }

    @Override
    public void updateYourPlayerInfo(PlayerInfo yourPlayerInfo) {

    }

    @Override
    public void updateOtherPlayerInfo(HashMap<String, PlayerInfo> otherPlayerInfo) {

    }

    @Override
    public void updateCommonObjectives(ObjectiveCard[] commonObjectives) {

    }

    @Override
    public void updateStarterCard(StarterCard starterCard) {

    }

    @Override
    public void showResult(String result) {

    }

    @Override
    public void showNextTurn(String nextPlayer) {

    }

    @Override
    public void showMustPick() {

    }

    @Override
    public void showError(String error) {

    }

    public void showStartScreen() {
        startScreenFrame.setVisible(true);
    }

    public void log(String log) {
        gameFrame.getLogPanel().log(log);
    }

    public void addNewGameListener(NewGameListener newGameListener){
        startScreenFrame.getStartPanel().setNewGameListener(newGameListener);
    }

    public void addJoinGameListener(JoinGameListener joinGameListener){
        startScreenFrame.getStartPanel().setJoinGameListener(joinGameListener);
    }

    public void addMapListener(MapListener mapListener){
        gameFrame.getTablePanel().setMapListener(mapListener);
    }

    public void addYourCardsListener(YourCardsListener yourCardsListener){
        gameFrame.getYourCardsPanel().setYourCardsListener(yourCardsListener);
    }

}
