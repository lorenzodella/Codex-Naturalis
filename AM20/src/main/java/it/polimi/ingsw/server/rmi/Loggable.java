package it.polimi.ingsw.server.rmi;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.server.Connection;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * INTERFACE THAT SHOWS ALL THE RMI METHODS THAT CAN BE INVOKED BY THE CLIENT.
 * EACH METHOD REPRESENTS A SPECIFIC GAME ACTION (SUCH AS LOGIN, STARTGAME...)
 */
public interface Loggable extends Remote {
    //metodi che vengono chiamati dal client sul server rmi
    ConnectionAckMessage login(String client, PawnColor color, Connection callback) throws RemoteException, CannotJoinGameException;
    ConnectionAckMessage startNewGame(String client, PawnColor color, int numPlayers, Connection callback) throws RemoteException, InvalidArgumentException, InvalidPlayingException;
    StarterCardAckMessage chooseStarterCardSide(String nickname, int side) throws RemoteException, InvalidArgumentException, InvalidPlayingException;
    ObjectiveAckMessage chooseObjective(String nickname, int index) throws RemoteException, InvalidArgumentException, InvalidPlayingException;
    AcknowledgeMessage playCard(String playerNickname, int cardIndex, int angle, String targetID, int side) throws InvalidArgumentException, RequirementsNotRespectedException, InvalidPlayingException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, RemoteException;
    AcknowledgeMessage pickCard(String playerNickname, int deck) throws RemoteException, InvalidArgumentException, InvalidPlayingException, FinishedCardStackException;
    AcknowledgeMessage pickCard(String playerNickname, int deck, int index) throws RemoteException, InvalidArgumentException, InvalidPlayingException, FinishedCardStackException;
    Message sendChatMessage(String sender, String recipient, String message) throws RemoteException;
    Message sendBroadcastChatMessage(String sender, String message) throws RemoteException;

    void ping() throws RemoteException;
}