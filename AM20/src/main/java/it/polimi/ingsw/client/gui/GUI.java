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
    public void showStartGame() {
        GUIUtils.disposeMessage();
        startScreenFrame.dispose();
        gameFrame.pack();
        gameFrame.setVisible(true);
        starterCardDialog.setLocationRelativeTo(gameFrame);
        starterCardDialog.setVisible(true);

    }

    @Override
    public void showStarterCard() {
        starterCardDialog.dispose();
        GUIUtils.showMessage("You have chosen your starter card. Wait for the others");
    }

    @Override
    public void showStartChoosingObjective() {
        starterCardDialog.dispose();
        GUIUtils.disposeMessage();
        secretObjectiveDialog.setLocationRelativeTo(gameFrame);
        secretObjectiveDialog.setVisible(true);

    }

    @Override
    public void showObjectiveMessage() {
        secretObjectiveDialog.dispose();
        GUIUtils.showMessage("You have chosen your secret objective. Wait for the others");

    }

    @Override
    public void showStartPlaying() {
        secretObjectiveDialog.dispose();
        GUIUtils.disposeMessage();



    }

    @Override
    public void showPlayAck() {
        gameFrame.getYourCardsPanel().setCardsClickable(false);


    }

    @Override
    public void showPickAck() {
        gameFrame.getDeckPanel().setCardsClickable(false);
    }



    @Override
    public void updateCards(List<PlayableCard> cards) {
        gameFrame.getYourCardsPanel().update(cards);
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
                secretObjectiveDialog.update(secretObjectives.get(0), secretObjectives.get(1));
            }
            else{
                gameFrame.getSecretObjectivePanel().update(secretObjectives.get(0));
            }
        }
    }

    @Override
    public void updateGold(PlayableCard goldTop, PlayableCard[] goldVisible) {
        gameFrame.updateGoldDeckPanels(goldTop, goldVisible);
    }

    @Override
    public void updateResource(PlayableCard resourceTop, PlayableCard[] resourceVisible) {
        gameFrame.updateResourceCardsPanels(resourceTop, resourceVisible);
    }

    @Override
    public void updateYourPlayerInfo(PlayerInfo yourPlayerInfo) {
        gameFrame.updateYourInfo(yourPlayerInfo);
    }

    @Override
    public void updateOtherPlayerInfo(HashMap<String, PlayerInfo> otherPlayerInfo) {
        if(otherPlayerInfo!=null)
            gameFrame.updateOtherPlayers(otherPlayerInfo);
    }

    @Override
    public void updateCommonObjectives(ObjectiveCard[] commonObjectives) {
        gameFrame.updateCommonObjectivePanels(commonObjectives);
    }

    @Override
    public void updateStarterCard(StarterCard starterCard) {
        if(starterCard!=null) {
            starterCardDialog.update(starterCard);
        }

    }

    @Override
    public void showResult(String result) {
        log(result);
    }

    @Override
    public void showNextTurn(String nextPlayer) {
        if(nextPlayer!=null) {
            if (nextPlayer.equals(nickname)) {
                GUIUtils.showInfo("It's your turn");
                gameFrame.getYourCardsPanel().setCardsClickable(true);

            } else {
                log("It's "+ nextPlayer+"'s turn");
            }
        }

    }

    @Override
    public void showMustPick() {
        gameFrame.getDeckPanel().setCardsClickable(true);
        GUIUtils.showInfo("You have to pick a card");

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
        gameFrame.getTablePanel().setMapListener(mapListener);
    }

    public void addYourCardsListener(YourCardsListener yourCardsListener){
        gameFrame.getYourCardsPanel().setYourCardsListener(yourCardsListener);
    }

    public void addDeckListener(DeckCoveredListener deckCoveredListener, DeckVisibleListener deckVisibleListener){
        gameFrame.getDeckPanel().setDeckListener(deckVisibleListener, deckCoveredListener);
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
