package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.controller.exceptions.InvalidPlayingException;
import it.polimi.ingsw.controller.messages.ConnectionAckMessage;
import it.polimi.ingsw.controller.exceptions.StopGameException;
import it.polimi.ingsw.controller.messages.AcknowledgeMessage;
import it.polimi.ingsw.controller.messages.Message;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;

import java.util.HashMap;

public interface GameManager {

    HashMap<String, AcknowledgeMessage> disconnectPlayer(String nickname) throws InvalidArgumentException, StopGameException, InvalidConnectionStateException;

    Message newGame(String playerNickname, int numPlayers) throws InvalidArgumentException, InvalidPlayingException;
    HashMap<String, ConnectionAckMessage> joinGame(String playerNickname) throws CannotJoinGameException;

    void chooseStarterCardSide(String playerNickname, int side) throws InvalidArgumentException, InvalidPlayingException;

    void chooseObjective(String playerNickname, int index) throws InvalidArgumentException, InvalidPlayingException;

    void playCard(String playerNickname, int indexCard, int angle, String targetID, int side)
            throws InvalidArgumentException, TargetNotPresentException,
            InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException,
            InvalidPlayingException;

    void pickCard(String playerNickname, int deck) throws InvalidArgumentException, FinishedCardStackException, InvalidPlayingException;

    void pickCard(String playerNickname, int deck, int index) throws InvalidArgumentException, FinishedCardStackException, InvalidPlayingException;
}
