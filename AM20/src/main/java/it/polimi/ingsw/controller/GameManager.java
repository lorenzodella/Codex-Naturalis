package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.exceptions.InvalidDisconnectionException;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.exceptions.InvalidPlayingException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.controller.exceptions.NoOneIsConnectedException;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;

import java.util.HashMap;

//TODO: ELEONORA

public interface GameManager {

    HashMap<String, AcknowledgeMessage> disconnectPlayer(String nickname) throws InvalidArgumentException, NoOneIsConnectedException, InvalidConnectionStateException, InvalidDisconnectionException;

    ConnectionAckMessage newGame(String playerNickname, PawnColor color, int numPlayers) throws InvalidArgumentException, InvalidPlayingException;
    HashMap<String, ConnectionAckMessage> joinGame(String playerNickname, PawnColor color) throws CannotJoinGameException;

    HashMap<String, StarterCardAckMessage> chooseStarterCardSide(String playerNickname, int side) throws InvalidArgumentException, InvalidPlayingException;

    HashMap<String, ObjectiveAckMessage> chooseObjective(String playerNickname, int index) throws InvalidArgumentException, InvalidPlayingException;

    HashMap<String, AcknowledgeMessage> playCard(String playerNickname, int indexCard, int angle, String targetID, int side)
            throws InvalidArgumentException, TargetNotPresentException,
            InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException,
            InvalidPlayingException, NoOneIsConnectedException;

    HashMap<String, AcknowledgeMessage> pickCard(String playerNickname, int deck) throws InvalidArgumentException, FinishedCardStackException, InvalidPlayingException, NoOneIsConnectedException;

    HashMap<String, AcknowledgeMessage> pickCard(String playerNickname, int deck, int index) throws InvalidArgumentException, FinishedCardStackException, InvalidPlayingException, NoOneIsConnectedException;
}
