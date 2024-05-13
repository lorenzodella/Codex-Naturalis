package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.messages.*;

public class UIUpdater  {

    private UIManager manager;

    public UIUpdater(UIManager manager){
        this.manager = manager;
    }

    public void connectionAck(ConnectionAckMessage msg){
        manager.showResult(msg.getResult());
    }

    public void startGame(StartGameMessage msg){
        manager.updateGoldTop(msg.getGoldTop());
        manager.updateResourceTop(msg.getResourceTop());
        manager.updateGoldVisible(msg.getGoldVisible());
        manager.updateResourceVisible(msg.getResourceVisible());
        manager.updateStarterCard(msg.getStarterCard());
        manager.showResult(msg.getResult());
    }

    public void acknowledge(AcknowledgeMessage msg){
        manager.updateCards(msg.getCards());
        manager.showNextTurn(msg.getNextPlayer());
        manager.showResult(msg.getResult());
    }

    public void pickAck(PickAckMessage msg){
        manager.updateGoldTop(msg.getGoldTop());
        manager.updateResourceTop(msg.getResourceTop());
        manager.updateGoldVisible(msg.getGoldVisible());
        manager.updateResourceVisible(msg.getResourceVisible());
        manager.showNextTurn(msg.getNextPlayer());
        manager.updateCards(msg.getCards());
        manager.showResult(msg.getResult());


    }

    public void playAck(PlayAckMessage msg){
        manager.updateYourPlayerInfo(msg.getYourPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        if(msg.mustPick()){
            manager.showMustPick();
        }
        else{
            manager.showNextTurn(msg.getNextPlayer());
        }
        manager.updateCards(msg.getCards());
        manager.showResult(msg.getResult());
    }

    public void starterCard(StarterCardAckMessage msg){
        manager.updateYourPlayerInfo(msg.getPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.showResult(msg.getResult());
    }

    public void startChoosingObjective(StartChoosingObjectiveMessage msg){
        manager.updateCommonObjectives(msg.getCommonObjectives());
        manager.updateSecretObjectives(msg.getSecretObjectives());
        manager.showResult(msg.getResult());
    }

    public void chatMessage(ChatMessage msg){
        manager.updateChatMessage(msg.getSender(), msg.getMessage());
    }

    public void objectiveMessage(ObjectiveAckMessage msg){
        manager.updateSecretObjectives(msg.getSecretObjectives());
        manager.showResult(msg.getResult());
    }

    public void startPlaying(StartPlayingMessage msg){
        manager.showNextTurn(msg.getFirstPlayer());
        manager.showResult(msg.getResult());
    }

    public void message(Message msg){
        manager.showResult(msg.getResult());
    }

    public void errorMessage(ErrorMessage msg){
        manager.showError(msg.getError());
    }

}
