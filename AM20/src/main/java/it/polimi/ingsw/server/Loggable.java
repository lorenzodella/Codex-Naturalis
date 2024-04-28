package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.exceptions.StopGameException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.exceptions.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface Loggable extends Remote {

    /*boolean login(String cli, Callback callback) throws RemoteException, CannotJoinGameException;
    boolean loginFirstPlayer(String clientNick, int numPlayer, Callback callback) throws RemoteException, CannotJoinGameException, InvalidArgumentException;
    boolean chooseStarterSide() throws RemoteException;
    void logout(String nick) throws RemoteException;
    */

    ConnectionAckMessage login(String client, Callback callback) throws RemoteException, CannotJoinGameException;
    Message starNewGame(String client, int numPlayers, Callback callback) throws RemoteException, InvalidArgumentException, InvalidPlayingException;
    AcknowledgeMessage disconnectPlayer(String nickname) throws RemoteException, InvalidArgumentException, StopGameException, InvalidConnectionStateException;
    StarterCardAckMessage chooseStarterCardSide(String nickname, int side) throws RemoteException, InvalidArgumentException, InvalidPlayingException;
    ObjectiveAckMessage chooseObjective(String nickname, int index) throws RemoteException, InvalidArgumentException, InvalidPlayingException;
    AcknowledgeMessage playCard(String playerNickname, int cardIndex, int angle, String targetID, int side) throws InvalidArgumentException, RequirementsNotRespectedException, InvalidPlayingException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, RemoteException, StopGameException;
    AcknowledgeMessage pickCard(String playerNickname, int deck) throws RemoteException, InvalidArgumentException, InvalidPlayingException, FinishedCardStackException, StopGameException;
    AcknowledgeMessage pickCard(String playerNickname, int deck, int index) throws RemoteException, InvalidArgumentException, InvalidPlayingException, FinishedCardStackException, StopGameException;
}