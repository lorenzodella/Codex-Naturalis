package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.messages.*;

import java.io.IOException;

public interface ClientManagerReceiver {
    void callChatMessage(ChatMessage message);
    void callStopGame(Message message);
    void callConnectionAckMessage(ConnectionAckMessage message);
    void callAcknowledgeMessage(AcknowledgeMessage message);
    void callStarterCardAckMessage(StarterCardAckMessage message);
    void callObjectiveAckMessage(ObjectiveAckMessage message);
    void callMessage(Message message);
    void callErrorMessage(ErrorMessage message) ;

}
