package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.server.Connection;

import java.rmi.RemoteException;

public interface ClientManagerSender {
    void login(String client, Connection callback);
    void starNewGame(String client, int numPlayers, Connection callback) ;
    void chooseStarterCardSide(String nickname, int side);
    void chooseObjective(String nickname, int index);
    void playCard(String playerNickname, int cardIndex, int angle, String targetID, int side);
    void pickCard(String playerNickname, int deck);
    void pickCard(String playerNickname, int deck, int index);
    void sendChatMessage(String sender, String recipient, String message);
    void sendBroadcastChatMessage(String sender, String message);
}
