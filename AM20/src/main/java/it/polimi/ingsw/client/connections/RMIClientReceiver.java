package it.polimi.ingsw.client.connections;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.client.UIUpdater;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.server.Connection;

/**
 * This class is the RMIClientReceiver class of the client side of the application.
 * It allows the client to receive messages from the server.
 * It extends the UnicastRemoteObject class in order to be exported to the RMI registry abd to receive remote calls.
 */
public class RMIClientReceiver extends UnicastRemoteObject implements Connection {

    /**
     * The UIUpdater called to update the UI after receiving a message.
     */
    private UIUpdater uiUpdater;

    public RMIClientReceiver(UIUpdater updater) throws RemoteException {
        this.uiUpdater = updater;
    }

    /**
     * This method allows the client to receive a ChatMessage from the server.
     * @param message the ChatMessage received from the server
     * @throws RemoteException if the remote operation fails
     */
    @Override
    public void callChatMessage(ChatMessage message) throws RemoteException {
         this.uiUpdater.chatMessage(message);
    }

    /**
     * This method allows the client to receive a StopGameMessage from the server.
     * @param message the StopGameMessage received from the server
     * @throws RemoteException if the remote operation fails
     */
    @Override
    public void callStopGame(StopGameMessage message) throws RemoteException {
        this.uiUpdater.stopGame(message);
    }

/**
     * This method allows the client to receive a ConnectionAckMessage from the server.
     * @param message the ConnectionAckMessage received from the server
     * @throws RemoteException if the remote operation fails
     */
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

    /**
     * This method allows the client to receive an AcknowledgeMessage from the server.
     * @param message the AcknowledgeMessage received from the server
     * @throws RemoteException if the remote operation fails
     */
    @Override
    public void callAcknowledgeMessage(AcknowledgeMessage message) throws RemoteException {

        if(message.getAction().equals(AcknowledgeMessage.DISCONNECTION))
            this.uiUpdater.disconnectionAck((DisconnectionMessage) message);
        if(message.getAction().equals(AcknowledgeMessage.PLAY))
            this.uiUpdater.playAck((PlayAckMessage) message);
        if(message.getAction().equals(AcknowledgeMessage.PICK))
            this.uiUpdater.pickAck((PickAckMessage) message);

    }

    /**
     * This method allows the client to receive a StarterCardAckMessage from the server.
     * @param message the StarterCardAckMessage received from the server
     * @throws RemoteException if the remote operation fails
     */
    @Override
    public void callStarterCardAckMessage(StarterCardAckMessage message) throws RemoteException {

        if(message.shouldChooseObjective()) //true quindi è startChoosignObjectiveMessage
            this.uiUpdater.startChoosingObjective((StartChoosingObjectiveMessage) message);
        else
            this.uiUpdater.starterCard(message);

    }

    /**
     * This method allows the client to receive an ObjectiveAckMessage from the server.
     * @param message the ObjectiveAckMessage received from the server
     * @throws RemoteException if the remote operation fails
     */
    @Override
    public void callObjectiveAckMessage(ObjectiveAckMessage message) throws RemoteException {

        if(message.shouldStartPlaying())//true quindi è una StartPlayingMessage
            this.uiUpdater.startPlaying((StartPlayingMessage) message);
        else
            this.uiUpdater.objectiveMessage(message);
    }

    /**
     * This method allows the client to receive a Message from the server.
     * @param message the Message received from the server
     * @throws RemoteException if the remote operation fails
     */
    public void callMessage(Message message) throws RemoteException{
        this.uiUpdater.message(message);
    }

    /**
     * This method allows the client to receive an ErrorMessage from the server.
     * @param message the ErrorMessage received from the server
     */
    public void callErrorMessage(ErrorMessage message){
        this.uiUpdater.errorMessage(message);
    }

    /* se il messaggio viene inviato ritorna vero altrimenti manda exc */
    /**
     * This method allows the client to receive a PingMessage from the server.
     * @param message the PingMessage received from the server
     * @return true if the message is received, false otherwise
     * @throws RemoteException if the remote operation fails, this means that the client is not connected anymore
     */
    @Override
    public boolean callPingMessage(PingMessage message) throws RemoteException {
        return true;
    }


}