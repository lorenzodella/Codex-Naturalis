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
    }

    public void startGame(StartGameMessage msg){
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
        manager.showStartGame();
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

    public void acknowledge(AcknowledgeMessage msg){
        manager.showNextTurn(msg.getNextPlayer());
        manager.showResult(msg.getResult());
        manager.showCommand();
    }

    public void pickAck(PickAckMessage msg){
        manager.updateGold(msg.getGoldTop(), msg.getGoldVisible());
        manager.updateResource(msg.getResourceTop(),msg.getResourceVisible());
        manager.updateCards(msg.getCards());
        manager.showNextTurn(msg.getNextPlayer());
        manager.showResult(msg.getResult());
        manager.showPickAck();
        manager.showCommand();
    }

    public void playAck(PlayAckMessage msg){
        manager.updateYourPlayerInfo(msg.getYourPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.updateCards(msg.getCards());
        if(msg.mustPick()){
            manager.showMustPick();
        }
        manager.showNextTurn(msg.getNextPlayer());
        manager.showResult(msg.getResult());
        manager.showPlayAck();
        manager.showCommand();
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
