package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.UIManager;
import it.polimi.ingsw.client.gui.gameview.GameFrame;
import it.polimi.ingsw.client.gui.gameview.SecretObjectiveDialog;
import it.polimi.ingsw.client.gui.gameview.StarterCardDialog;
import it.polimi.ingsw.client.gui.listeners.*;
import it.polimi.ingsw.client.gui.startscreen.StartScreenFrame;
import it.polimi.ingsw.controller.PlayerInfo;
import it.polimi.ingsw.controller.messages.ChatMessage;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.GoldCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUI implements UIManager {

    String nickname;
    public GameFrame gameFrame;
    StartScreenFrame startScreenFrame;
    StarterCardDialog starterCardDialog;
    SecretObjectiveDialog secretObjectiveDialog;

    public GUI() {
        //le schermate che ho bisogno di mostrare
        startScreenFrame = new StartScreenFrame();
        gameFrame = new GameFrame();
        starterCardDialog = new StarterCardDialog(gameFrame);
        secretObjectiveDialog = new SecretObjectiveDialog(gameFrame);
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
        gameFrame.setNickname(nickname);
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void startGame() {
        GUIUtils.disposeMessage();
        startScreenFrame.dispose();
        gameFrame.pack();
        gameFrame.setVisible(true);
    }

    @Override
    public void updateCards(List<PlayableCard> cards) {
        gameFrame.updateYourCards(cards);
    }

    @Override
    public void updateChatMessage(ChatMessage msg) {
        if(msg.getRecipient()==null || msg.getRecipient().equals(nickname))
            gameFrame.getChat().receiveMessage(msg.getMessage(), msg.getSender());
    }

    @Override
    public void updateSecretObjectives(ArrayList<ObjectiveCard> secretObjectives) {
        if(secretObjectives!=null) {
            if (secretObjectives.size() > 1) {
                starterCardDialog.dispose();
                secretObjectiveDialog.update(secretObjectives.get(0), secretObjectives.get(1));
                secretObjectiveDialog.setVisible(true);
            }
        }
    }

    @Override
    public void updateGold(PlayableCard goldTop, PlayableCard[] goldVisible) {

    }

    @Override
    public void updateResource(PlayableCard resourceTop, PlayableCard[] resourceVisible) {

    }

    @Override
    public void updateYourPlayerInfo(PlayerInfo yourPlayerInfo) {

    }

    @Override
    public void updateOtherPlayerInfo(HashMap<String, PlayerInfo> otherPlayerInfo) {
        if(otherPlayerInfo!=null)
            gameFrame.updateOtherPlayers(otherPlayerInfo);
    }

    @Override
    public void updateCommonObjectives(ObjectiveCard[] commonObjectives) {

    }

    @Override
    public void updateStarterCard(StarterCard starterCard) {
        if(starterCard!=null) {
            starterCardDialog.update(starterCard);
            starterCardDialog.setVisible(true);
        }

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
    public void showError(String error){
        GUIUtils.showError(error);
    }

    @Override
    public void showCommand() {

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
        gameFrame.setMapListener(mapListener);
    }

    public void addYourCardsListener(YourCardsListener yourCardsListener){
        gameFrame.setYourCardsListener(yourCardsListener);
    }

    public void addDeckListener(DeckCoveredListener deckCoveredListener, DeckVisibleListener deckVisibleListener){
        gameFrame.setDeckListener(deckCoveredListener, deckVisibleListener);
    }

    public void addChatListener(ChatListener chatListener){
        gameFrame.getChat().setChatListener(chatListener);
    }

    public void addStarterCardListener(StarterCardListener starterCardListener){
        starterCardDialog.setStarterCardListener(starterCardListener);
    }
    public void addSecretObjectiveListener(SecretObjectiveListener secretObjectiveListener){
        secretObjectiveDialog.setObjectiveListener(secretObjectiveListener);
    }
}
