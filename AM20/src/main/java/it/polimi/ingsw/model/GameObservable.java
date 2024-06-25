package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidPlayingException;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.exceptions.*;

import java.util.List;
import java.util.Set;

public interface GameObservable {


    //getters
    Set<String> getConnectedPlayers();
    List<Player> getPlayers();
    Player getCurrPlayer();
    Deck getResourceCardDeck();
    Deck getGoldCardDeck();
    ObjectiveCard[] getCommonObjectives();

    //modifiers
    List<Player> setPlayerConnection(String nickname, boolean isOnline) throws InvalidArgumentException, InvalidConnectionStateException;
    Deck[] initDecks();
    List<Player> giveStarterCards();
    List<Player> giveInitialCards();
    Player chooseStarterCardSide(int side, String playerNickname) throws InvalidArgumentException, InvalidPlayingException;
    List<Player> initObjectiveCards();
    Player chooseObjective(int index, String playerNickname) throws InvalidArgumentException, InvalidPlayingException;
    Player chooseFirstPlayer();
    Player playCard(int indexCard, int angle, String targetID, int side) throws InvalidArgumentException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException;
    boolean areDeckFinished();
    Player pickCard(int deck, int visibleCardIndex) throws FinishedCardStackException, InvalidArgumentException;
    Player pickCard(int deck) throws FinishedCardStackException, InvalidArgumentException;
    boolean nextTurn() throws InvalidPlayingException;
    boolean checkEndPhase();
    List<Player> computePlayerSecretObjectives();
    List<Player> computeCommonObjectives();
    Player checkWinner() throws DrawMatchException;
}
