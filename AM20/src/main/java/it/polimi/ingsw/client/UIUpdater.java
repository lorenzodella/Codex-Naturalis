package it.polimi.ingsw.client;

import it.polimi.ingsw.client.tui.ConsoleColors;
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
        System.out.println();
        manager.showResult(msg.getResult());
        manager.updateGoldTop(msg.getGoldTop());
        manager.updateResourceTop(msg.getResourceTop());
        manager.updateGoldVisible(msg.getGoldVisible());
        manager.updateResourceVisible(msg.getResourceVisible());
        manager.updateYourPlayerInfo(msg.getPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.updateCards(msg.getInitialCards());
        manager.updateStarterCard(msg.getStarterCard());
        System.out.println(ConsoleColors.TEXT_YELLOW+ "For obtaining the parameters of the command and the full list of command type /help" + ConsoleColors.TEXT_RESET);

        System.out.println("Decide which command you want to do:");

    }

    public void acknowledge(AcknowledgeMessage msg){
        manager.showNextTurn(msg.getNextPlayer());
        manager.showResult(msg.getResult());
        System.out.println(ConsoleColors.TEXT_YELLOW+ "For obtaining the parameters of the command and the full list of command type /help" + ConsoleColors.TEXT_RESET);

        System.out.println("Decide which command you want to do:");

    }

    public void pickAck(PickAckMessage msg){

        manager.updateGoldTop(msg.getGoldTop());
        manager.updateResourceTop(msg.getResourceTop());
        manager.updateGoldVisible(msg.getGoldVisible());
        manager.updateResourceVisible(msg.getResourceVisible());
        manager.updateCards(msg.getCards());
        manager.showNextTurn(msg.getNextPlayer());
        manager.showResult(msg.getResult());
        System.out.println(ConsoleColors.TEXT_YELLOW+ "For obtaining the parameters of the command and the full list of command type /help" + ConsoleColors.TEXT_RESET);

        System.out.println("Decide which command you want to do:");



    }

    public void playAck(PlayAckMessage msg){

        manager.updateYourPlayerInfo(msg.getYourPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.updateCards(msg.getCards());
        if(msg.mustPick()){
            manager.showMustPick();
        }
        else if(msg.getNextPlayer()!=null){
            manager.showNextTurn(msg.getNextPlayer());
        }

        manager.showResult(msg.getResult());
        System.out.println(ConsoleColors.TEXT_YELLOW+ "For obtaining the parameters of the command and the full list of command type /help" + ConsoleColors.TEXT_RESET);

        System.out.println("Decide which command you want to do:");

    }

    public void starterCard(StarterCardAckMessage msg){

        manager.updateYourPlayerInfo(msg.getPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.showResult(msg.getResult());
        System.out.println(ConsoleColors.TEXT_YELLOW+ "\nFor obtaining the parameters of the command and the full list of command type /help" + ConsoleColors.TEXT_RESET);
        System.out.println("Decide which command you want to do:");


    }

    public void startChoosingObjective(StartChoosingObjectiveMessage msg){
        manager.updateYourPlayerInfo(msg.getPlayerInfo());
        manager.updateOtherPlayerInfo(msg.getOthersPlayerInfo());
        manager.updateCommonObjectives(msg.getCommonObjectives());
        manager.updateSecretObjectives(msg.getSecretObjectives());
        manager.showResult(msg.getResult());
        System.out.println(ConsoleColors.TEXT_YELLOW+ "For obtaining the parameters of the command and the full list of command type /help" + ConsoleColors.TEXT_RESET);
        System.out.println("Decide which command you want to do:");



    }

    public void objectiveMessage(ObjectiveAckMessage msg){
        manager.updateSecretObjectives(msg.getSecretObjectives());
        manager.showResult(msg.getResult());
        System.out.println(ConsoleColors.TEXT_YELLOW+ "For obtaining the parameters of the command and the full list of command type /help" + ConsoleColors.TEXT_RESET);
        System.out.println("Decide which command you want to do:");


    }

    public void startPlaying(StartPlayingMessage msg){
        manager.updateSecretObjectives(msg.getSecretObjectives());
        manager.showResult(msg.getResult());
        manager.showNextTurn(msg.getFirstPlayer());
        System.out.println(ConsoleColors.TEXT_YELLOW+ "For obtaining the parameters of the command and the full list of command type /help" + ConsoleColors.TEXT_RESET);
        System.out.println("Decide which command you want to do:");


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
