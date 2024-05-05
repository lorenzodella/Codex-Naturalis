package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.messages.*;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Connection extends Remote {
    void callChatMessage(ChatMessage message) throws IOException;
    void callStopGame(Message message) throws IOException;
    void callConnectionAckMessage(ConnectionAckMessage message) throws IOException;
    void callAcknowledgeMessage(AcknowledgeMessage message) throws IOException;
    void callStarterCardAckMessage(StarterCardAckMessage message) throws IOException;
    void callObjectiveAckMessage(ObjectiveAckMessage message) throws IOException;
    void callMessage(Message message) throws IOException;
    void callErrorMessage(ErrorMessage message) throws IOException;
    boolean callPingMessage(Message message) throws IOException;

}
