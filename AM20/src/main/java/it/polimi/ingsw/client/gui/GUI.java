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
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUI implements UIManager {
    /**
     * nickname of the player
     */
    String nickname;
    /**
     * nickname of the current player
     */
    String currPlayer;
    /**
     * window of the game
     */
    GameFrame gameFrame;
    /**
     * initial window
     */
    StartScreenFrame startScreenFrame;
    /**
     * pop up that allows the player to choose the starter card side
     */
    StarterCardDialog starterCardDialog;
    /**
     * pop up that allows the player to choose the secret objective
     */
    SecretObjectiveDialog secretObjectiveDialog;

    public GUI() {
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

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


    /**
     * This method allows to show the user that he is connected to the server
     */
    @Override
    public void showConnection() {
        GUIUtils.disposeDialog();
        GUIUtils.showMessage(gameFrame,"Waiting for other players");
    }
    /**
     * This method allows to show the user that the game is starting
     */
    @Override
    public void showStartGame() {
        GUIUtils.disposeDialog();
        startScreenFrame.dispose();
        gameFrame.pack();
        gameFrame.setVisible(true);
        starterCardDialog.setLocationRelativeTo(gameFrame);
        starterCardDialog.setVisible(true);

    }
    /**
     * This method allows to show the user that someone is reconnected to the server
     */
    @Override
    public void showReconnection(String result, boolean isJoining) {
        if(isJoining) {
            GUIUtils.disposeDialog();
            startScreenFrame.dispose();
            gameFrame.pack();
            gameFrame.setVisible(true);
        }
        GUIUtils.showInfo(gameFrame, result);
    }

    @Override
    public void showStarterCard() {
        starterCardDialog.dispose();
        GUIUtils.showMessage(gameFrame, "You have chosen your starter card. Wait for the others");
    }

    @Override
    public void showStartChoosingObjective() {
        starterCardDialog.dispose();
        GUIUtils.disposeDialog();
        secretObjectiveDialog.setLocationRelativeTo(gameFrame);
        secretObjectiveDialog.setVisible(true);

    }

    @Override
    public void showObjectiveMessage() {
        secretObjectiveDialog.dispose();
        GUIUtils.showMessage(gameFrame, "You have chosen your secret objective. Wait for the others");

    }

    @Override
    public void showStartPlaying() {
        secretObjectiveDialog.dispose();
        GUIUtils.disposeDialog();



    }

    @Override
    public void showPlayAck() {
        gameFrame.getYourCardsPanel().setCardsClickable(false);


    }

    @Override
    public void showPickAck() {
        gameFrame.getDeckPanel().setCardsClickable(false);
    }


    /**
     * This method updates the cards in your hand, in the panel
     * @param cards list of the card that the player can play
     */
    @Override
    public void updateCards(List<PlayableCard> cards) {
        if(cards!=null){
            gameFrame.getYourCardsPanel().update(cards);
        }
    }
    /**
     * This method updates the player's chat with the new message that he just received
     * @param msg message received by the server
     */
    @Override
    public void updateChatMessage(ChatMessage msg) {
        if(msg.getRecipient()==null || msg.getRecipient().equals(nickname)) {
            gameFrame.getChat().receiveMessage(msg.getMessage(), msg.getSender());
            if(!gameFrame.getChat().isVisible())
                GUIUtils.showChatMessage(gameFrame, gameFrame.getChat());
        }
    }
    /**
     * The first time this method receives an arrayList of two elements, then the player decides which one he prefers.
     * The second time the arrayList is made of a secretObjective (the one he had chosen).
     * This method allows to update the panel that contains the choosen secret objective.
     * @param secretObjectives of the player
     */
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
    /**
     * This method updates the panel that shows the gold deck and the two gold visible cards.
     * @param goldTop gold card on the top of the deck
     * @param goldVisible array of the two visible gold cards
     */
    @Override
    public void updateGold(PlayableCard goldTop, PlayableCard[] goldVisible) {
        gameFrame.updateGoldDeckPanels(goldTop, goldVisible);
    }
    /**
     * This method updates the panel that shows the resource deck and the two resource visible cards.
     * @param resourceTop resource card on the top of the deck
     * @param resourceVisible array of the two visible resource cards
     */
    @Override
    public void updateResource(PlayableCard resourceTop, PlayableCard[] resourceVisible) {
        gameFrame.updateResourceCardsPanels(resourceTop, resourceVisible);
    }
    /**
     * This method updates the panel that shows the player info
     * (color chosen, score, map of the player table and occurrences of every kingdom and objects)
     * @param yourPlayerInfo info of the player
     */
    @Override
    public void updateYourPlayerInfo(PlayerInfo yourPlayerInfo) {
        if(yourPlayerInfo!=null)
            gameFrame.updateYourInfo(yourPlayerInfo);
    }
    /**
     * This method updates the panel that shows all other players info
     * @param otherPlayerInfo map where for each username of the other players, you can obtain the their info
     *                        (color chosen, score, map of the player table and occurrences of every kingdom and objects)
     */
    @Override
    public void updateOtherPlayerInfo(HashMap<String, PlayerInfo> otherPlayerInfo) {
        if(otherPlayerInfo!=null)
            gameFrame.updateOtherPlayers(otherPlayerInfo);
    }
    /**
     * This method updates the panel that shows the common objectives
     * @param commonObjectives array of the two common objective
     */
    @Override
    public void updateCommonObjectives(ObjectiveCard[] commonObjectives) {
        gameFrame.updateCommonObjectivePanels(commonObjectives);
    }
    /**
     * This method shows a pop up that allows the player to choose the side of the starter card and
     * it shows every change.
     * @param starterCard starter card of the player
     */
    @Override
    public void updateStarterCard(StarterCard starterCard) {
        if(starterCard!=null) {
            starterCardDialog.update(starterCard);
        }

    }
    /**
     * This method shows a new received message into the log area
     * @param result string that inform about the result
     */
    @Override
    public void showResult(String result) {
        log(result);
    }
    /**
     * This method shows a new received message into the log area and it shows a pop up that contains the message.
     * @param result string that inform about the result
     * @param importantMessage string that inform about the important message
     */
    @Override
    public void showImportantMessage(String result, String importantMessage) {
        if(importantMessage!=null) {
            log(importantMessage);
            GUIUtils.showImportantInfo(gameFrame, result+"\n"+importantMessage);
        }
    }
    /**
     * This method allows to check if it's player turn or not.
     * If it is so, the method shows a pop up and a message into the log area saying "it's your turn"
     * Otherwise the method shows a pop up and a message into the log area saying "it's ... turn"
     * @param nextPlayer username of the next player that has to play
     */
    @Override
    public void showNextTurn(String nextPlayer) {
        if(nextPlayer!=null) {
            if (nextPlayer.equals(nickname)) {
                GUIUtils.showInfo(gameFrame, "It's your turn");
                log("It's your turn");
                gameFrame.getYourCardsPanel().setCardsClickable(true);
                currPlayer = nextPlayer;
            } else {
                if(currPlayer==null || !currPlayer.equals(nextPlayer))
                    GUIUtils.showInfo(gameFrame, "It's "+ nextPlayer+"'s turn");
                log("It's "+ nextPlayer+"'s turn");
                currPlayer = nextPlayer;
            }
        }
        else{
            log("Game is over!");
            updateStopGame();
        }

    }
    /**
     * This method allows the client to inform the user that the game is ended and that if he wants to play again,
     * he has to relaunch the application.
     * It also blocks all buttons of the gui.
     */
    @Override
    public void updateStopGame() {
        nickname = null;
        GUIUtils.disposeDialog();
        starterCardDialog.dispose();
        secretObjectiveDialog.dispose();
        gameFrame.getLogPanel().disableChat();
        gameFrame.getDeckPanel().setCardsClickable(false);
        gameFrame.getYourCardsPanel().setCardsClickable(false);
        GUIUtils.showInfo(gameFrame, "Game is over. You have to relaunch application to play again");
    }
    /**
     * This method shows a pop up saying "you need to pick a card" and it makes the
     * deck panel bottoms clickable
     */
    @Override
    public void showMustPick() {
        gameFrame.getDeckPanel().setCardsClickable(true);
        GUIUtils.showInfo(gameFrame, "You have to pick a card");

    }
    /**
     * This method allows to print the content of the error message into a pop up
     * @param error string that inform about the error
     */
    @Override
    public void showError(String error){
        GUIUtils.showError(gameFrame, error);
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
