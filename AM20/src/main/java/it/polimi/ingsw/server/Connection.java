package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.messages.*;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * This interface is the Connection interface of the server side of the application.
 * It allows the server to send messages to the client based on the connection type (socket or RMI).
 */
public interface Connection extends Remote {
    void callChatMessage(ChatMessage message) throws IOException;
    void callStopGame(StopGameMessage message) throws IOException;
    void callConnectionAckMessage(ConnectionAckMessage message) throws IOException;
    void callAcknowledgeMessage(AcknowledgeMessage message) throws IOException;
    void callStarterCardAckMessage(StarterCardAckMessage message) throws IOException;
    void callObjectiveAckMessage(ObjectiveAckMessage message) throws IOException;
    void callMessage(Message message) throws IOException;
    void callErrorMessage(ErrorMessage message) throws IOException;
    boolean callPingMessage(PingMessage message) throws IOException;

}
