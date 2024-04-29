package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.exceptions.NoOneIsConnectedException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.exceptions.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Loggable extends Remote {

    ConnectionAckMessage login(String client, Callback callback) throws RemoteException, CannotJoinGameException;
    Message starNewGame(String client, int numPlayers, Callback callback) throws RemoteException, InvalidArgumentException, InvalidPlayingException;
    StarterCardAckMessage chooseStarterCardSide(String nickname, int side) throws RemoteException, InvalidArgumentException, InvalidPlayingException;
    ObjectiveAckMessage chooseObjective(String nickname, int index) throws RemoteException, InvalidArgumentException, InvalidPlayingException;
    AcknowledgeMessage playCard(String playerNickname, int cardIndex, int angle, String targetID, int side) throws InvalidArgumentException, RequirementsNotRespectedException, InvalidPlayingException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, RemoteException;
    AcknowledgeMessage pickCard(String playerNickname, int deck) throws RemoteException, InvalidArgumentException, InvalidPlayingException, FinishedCardStackException;
    AcknowledgeMessage pickCard(String playerNickname, int deck, int index) throws RemoteException, InvalidArgumentException, InvalidPlayingException, FinishedCardStackException;
}