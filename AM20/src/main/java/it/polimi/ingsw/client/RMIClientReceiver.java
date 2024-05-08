package it.polimi.ingsw.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import it.polimi.ingsw.model.exceptions.InvalidPlayingException;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Loggable;

public class RMIClientReceiver extends UnicastRemoteObject implements Connection {

    private UIUpdater uiUpdater;

    public RMIClientReceiver() throws RemoteException {
        this.uiUpdater = new UIUpdater();
    }

    @Override
    public void callChatMessage(ChatMessage message) throws RemoteException {
         this.uiUpdater.chatMessage(message);
    }

    @Override
    public void callStopGame(Message message) throws RemoteException {
        this.uiUpdater.message(message);
    }

    @Override
    public void callConnectionAckMessage(ConnectionAckMessage message) throws RemoteException {

        if(!message.doesGameStarts()){ //false è ConnectionAckMessage
            this.uiUpdater.connectionAck(message);
        }else if(message.doesGameStarts()){ //true è StartGameMessage
            this.uiUpdater.startGame((StartGameMessage) message);
        }else {
            this.uiUpdater.restartGame((RestartGameMessage) message);
        }
    }

    @Override
    public void callAcknowledgeMessage(AcknowledgeMessage message) throws RemoteException {

        if(message.getAction().equals("Disconnection"))
            this.uiUpdater.acknowledge(message);
        if(message.getAction().equals("Play"))
            this.uiUpdater.playAck((PlayAckMessage) message);
        if(message.getAction().equals("Pick"))
            this.uiUpdater.pickAck((PickAckMessage) message);

    }

    @Override
    public void callStarterCardAckMessage(StarterCardAckMessage message) throws RemoteException {

        if(message.shouldChooseObjective()) //true quindi è startChoosignObjectiveMessage
            this.uiUpdater.startChoosingObjective((StartChoosingObjectiveMessage) message);
        else
            this.uiUpdater.starterCard(message);

    }

    @Override
    public void callObjectiveAckMessage(ObjectiveAckMessage message) throws RemoteException {

        if(message.shouldStartPlaying())//true quindi è una StartPlayingMessage
            this.uiUpdater.startPlaying((StartPlayingMessage) message);
        else
            this.uiUpdater.objectivemessage(message);
    }

    //TODO: da sistemare
    public void callMessage(Message message){
        this.uiUpdater.message(message);
    }

    //TODO: da sistemare
    public void callErrorMessage(ErrorMessage message){
        this.uiUpdater.errorMessage(message);
    }

    /* se il messaggio viene inviato ritorna vero altrimenti manda exc */
    @Override
    public boolean callPingMessage(Message message) throws RemoteException {
        return true;
    }


}