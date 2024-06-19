package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.messages.*;

public class UIUpdater  {

    private UIManager manager;

    public UIUpdater(UIManager manager){
        this.manager = manager;
    }

    public void connectionAck(ConnectionAckMessage msg){
        if(msg.getNickname()!=null)
            manager.setNickname(msg.getNickname());
        manager.showResult(msg.getResult());
        manager.showConnection();
    }

    public void startGame(StartGameMessage msg){
        initGame(msg);
        manager.showStartGame();
        manager.showCommand();
    }

    public void reconnection(ReconnectionMessage msg){
        initGame(msg);
        if(msg.getNickname()!=null) {
            manager.updateCommonObjectives(msg.getCommonObjectives());
            manager.updateSecretObjectives(msg.getSecretObjective());
        }
        manager.showReconnection(msg.getResult(), msg.getNickname()!=null);
        manager.showCommand();
    }

    private void initGame(StartGameMessage msg){
        System.out.println();
        if(msg.getNickname()!=null)
            manager.setNickname(msg.getNickname());
        manager.showResult(msg.getResult());
        manager.updateYourPlayerInfo(msg.getPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.updateGold(msg.getGoldTop(), msg.getGoldVisible());
        manager.updateResource(msg.getResourceTop(),msg.getResourceVisible());
        manager.updateCards(msg.getInitialCards());
        manager.updateStarterCard(msg.getStarterCard());
        manager.showCommand();
    }

    public void starterCard(StarterCardAckMessage msg){
        manager.updateYourPlayerInfo(msg.getPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.showResult(msg.getResult());
        if(msg.getPlayerInfo()!=null)
            manager.showStarterCard();
        manager.showCommand();
    }

    public void startChoosingObjective(StartChoosingObjectiveMessage msg){
        manager.updateYourPlayerInfo(msg.getPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.updateCommonObjectives(msg.getCommonObjectives());
        manager.updateSecretObjectives(msg.getSecretObjectives());
        manager.showResult(msg.getResult());
        manager.showStartChoosingObjective();
        manager.showCommand();
    }

    public void objectiveMessage(ObjectiveAckMessage msg){
        manager.updateSecretObjectives(msg.getSecretObjectives());
        manager.showResult(msg.getResult());
        manager.showObjectiveMessage();
        manager.showCommand();
    }

    public void startPlaying(StartPlayingMessage msg){
        manager.updateSecretObjectives(msg.getSecretObjectives());
        manager.showResult(msg.getResult());
        manager.showStartPlaying();
        manager.showNextTurn(msg.getFirstPlayer());
        manager.showCommand();
    }

    public void disconnectionAck(DisconnectionMessage msg){
        if(msg.areDecksModified()){
            manager.updateGold(msg.getGoldTop(), msg.getGoldVisible());
            manager.updateResource(msg.getResourceTop(),msg.getResourceVisible());
        }
        if(msg.getNextPlayer()!=null) {
            manager.showNextTurn(msg.getNextPlayer());
        }
        manager.showResult(msg.getResult());
        manager.showImportantMessage(msg.getResult(), msg.getImportantMessage());
        manager.showCommand();
    }

    public void playAck(PlayAckMessage msg){
        manager.updateYourPlayerInfo(msg.getYourPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.updateCards(msg.getCards());
        manager.showResult(msg.getResult());
        if(msg.mustPick())
            manager.showMustPick();
        else
            manager.showNextTurn(msg.getNextPlayer());
        manager.showPlayAck();
        manager.showImportantMessage(msg.getResult(), msg.getImportantMessage());
        manager.showCommand();
    }

    public void pickAck(PickAckMessage msg){
        manager.updateGold(msg.getGoldTop(), msg.getGoldVisible());
        manager.updateResource(msg.getResourceTop(),msg.getResourceVisible());
        manager.updateYourPlayerInfo(msg.getYourPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.updateCards(msg.getCards());
        manager.showResult(msg.getResult());
        manager.showNextTurn(msg.getNextPlayer());
        manager.showPickAck();
        manager.showImportantMessage(msg.getResult(), msg.getImportantMessage());
        manager.showCommand();
    }

    public void stopGame(StopGameMessage msg){
        manager.updateStopGame();
        manager.showImportantMessage("Game stopped!", msg.getResult());
    }

    public void chatMessage(ChatMessage msg){
        manager.updateChatMessage(msg);
    }

    public void message(Message msg){
        manager.showResult(msg.getResult());
    }

    public void errorMessage(ErrorMessage msg){
        manager.showError(msg.getError());
    }

}
