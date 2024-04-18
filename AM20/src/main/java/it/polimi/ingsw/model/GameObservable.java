package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.*;

import java.util.HashMap;
import java.util.List;

public interface GameObservable {

    List<Player> getPlayers();
    Player getCurrPlayer();
    Deck getResourceCardDeck();
    Deck getGoldCardDeck();

    Deck[] initDecks();

    List<Player> giveStarterCards();

    List<Player> giveInitialCards();

    Player chooseStarterCardSide(int side, String playerNickname) throws InvalidArgumentException;

    List<Player> initObjectiveCards();

    void chooseObjective(int index, String playerNickname) throws InvalidArgumentException;

    Player chooseFirstPlayer();

    Player playCard(int indexCard, int angle, String targetID, int side) throws InvalidArgumentException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException;

    boolean areDeckFinished();

    Player pickCard(int deck, int visibleCardIndex) throws FinishedCardStackException, InvalidArgumentException;

    Player pickCard(int deck) throws FinishedCardStackException, InvalidArgumentException;

    boolean nextTurn();

    boolean checkTheEnd();

    List<Player> computePlayerSecretObjectives();

    List<Player> computeCommonObjectives();

    Player checkWinner();

    ObjectiveCard[] getCommonObjectives();
}
