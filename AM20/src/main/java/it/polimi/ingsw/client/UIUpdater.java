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
        manager.showResult(msg.getResult());
        manager.updateGoldTop(msg.getGoldTop());
        manager.updateResourceTop(msg.getResourceTop());
        manager.updateGoldVisible(msg.getGoldVisible());
        manager.updateResourceVisible(msg.getResourceVisible());
        manager.updateStarterCard(msg.getStarterCard());
        manager.updateYourPlayerInfo(msg.getPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.updateCards(msg.getInitialCards());
    }

    public void acknowledge(AcknowledgeMessage msg){
        manager.showResult(msg.getResult());
        manager.showNextTurn(msg.getNextPlayer());
    }

    public void pickAck(PickAckMessage msg){
        manager.showResult(msg.getResult());
        manager.updateGoldTop(msg.getGoldTop());
        manager.updateResourceTop(msg.getResourceTop());
        manager.updateGoldVisible(msg.getGoldVisible());
        manager.updateResourceVisible(msg.getResourceVisible());
        manager.updateCards(msg.getCards());
        manager.showNextTurn(msg.getNextPlayer());


    }

    public void playAck(PlayAckMessage msg){
        manager.showResult(msg.getResult());
        manager.updateYourPlayerInfo(msg.getYourPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.updateCards(msg.getCards());
        if(msg.mustPick()){
            manager.showMustPick();
        }
        else if(msg.getNextPlayer()!=null){
            manager.showNextTurn(msg.getNextPlayer());
        }
    }

    public void starterCard(StarterCardAckMessage msg){
        manager.showResult(msg.getResult());
        manager.updateYourPlayerInfo(msg.getPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
    }

    public void startChoosingObjective(StartChoosingObjectiveMessage msg){
        manager.showResult(msg.getResult());
        manager.updateYourPlayerInfo(msg.getPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.updateCommonObjectives(msg.getCommonObjectives());
        manager.updateSecretObjectives(msg.getSecretObjectives());
    }

    public void objectiveMessage(ObjectiveAckMessage msg){
        manager.showResult(msg.getResult());
        manager.updateSecretObjectives(msg.getSecretObjectives());
    }

    public void startPlaying(StartPlayingMessage msg){
        manager.showResult(msg.getResult());
        manager.updateSecretObjectives(msg.getSecretObjectives());
        manager.showNextTurn(msg.getFirstPlayer());
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
