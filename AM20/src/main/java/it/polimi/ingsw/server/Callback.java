package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.messages.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Callback extends Remote {
    void callChatMessage(ChatMessage message) throws RemoteException;
    void callStopGame(Message message) throws RemoteException;
    void callConnectionAckMessage(ConnectionAckMessage message) throws RemoteException;
    void callAcknowledgeMessage(AcknowledgeMessage message) throws RemoteException;
    void callStarterCardAckMessage(StarterCardAckMessage message) throws RemoteException;
    void callObjectiveAckMessage(ObjectiveAckMessage message) throws RemoteException;

}
