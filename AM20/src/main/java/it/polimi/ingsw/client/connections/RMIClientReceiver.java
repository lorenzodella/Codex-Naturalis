package it.polimi.ingsw.client.connections;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.client.UIUpdater;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.server.Connection;

public class RMIClientReceiver extends UnicastRemoteObject implements Connection {

    private UIUpdater uiUpdater;

    public RMIClientReceiver(UIUpdater updater) throws RemoteException {
        this.uiUpdater = updater;
    }

    @Override
    public void callChatMessage(ChatMessage message) throws RemoteException {
         this.uiUpdater.chatMessage(message);
    }

    @Override
    public void callStopGame(StopGameMessage message) throws RemoteException {
        this.uiUpdater.stopGame(message);
    }

    @Override
    public void callConnectionAckMessage(ConnectionAckMessage message) throws RemoteException {
        if(!message.doesGameStarts()){ //false è ConnectionAckMessage
            this.uiUpdater.connectionAck(message);
        }else if(message.isReconnection()){ //true è ReconnectionMessage
            this.uiUpdater.reconnection((ReconnectionMessage) message);
        }else if(message.doesGameStarts()){ //true è StartGameMessage
            this.uiUpdater.startGame((StartGameMessage) message);
        }
    }

    @Override
    public void callAcknowledgeMessage(AcknowledgeMessage message) throws RemoteException {

        if(message.getAction().equals(AcknowledgeMessage.DISCONNECTION))
            this.uiUpdater.disconnectionAck((DisconnectionMessage) message);
        if(message.getAction().equals(AcknowledgeMessage.PLAY))
            this.uiUpdater.playAck((PlayAckMessage) message);
        if(message.getAction().equals(AcknowledgeMessage.PICK))
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
            this.uiUpdater.objectiveMessage(message);
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
    public boolean callPingMessage(PingMessage message) throws RemoteException {
        return true;
    }


}