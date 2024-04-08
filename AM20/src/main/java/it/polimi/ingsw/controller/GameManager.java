package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;

public interface GameManager {

    public void newGame(String playerNickname, int numPlayers) throws InvalidArgumentException;
    public void joinGame(String playerNickname) throws CannotJoinGameException;

    public void chooseStarterCardSide(String playerNickname, int side) throws InvalidArgumentException;

    public void chooseObjective(String playerNickname, int index) throws InvalidArgumentException;

    public void playCard(int indexCard, int angle, String targetID, int side) throws InvalidArgumentException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException;

    public void pickCard(int deck) throws InvalidArgumentException, FinishedCardStackException;

    void pickCard(int deck, int index) throws InvalidArgumentException, FinishedCardStackException;
}
