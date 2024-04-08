package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.ArrayList;
import java.util.List;

public interface GameObserver {
    void notifyDecks(Deck resourceCardDeck, Deck goldCardDeck);
    void notifyStarterCards(List<Player> players);
    void notifyInitialCards(List<Player> players);
    void notifyObjectiveCards(ObjectiveCard[] commonObjectives, List<Player> players);
    void notifyGameStarted(Player first);
    void notifyPlayerPlay(Player player);
    void notifyPlayerPick(Player player);
    void notifyNextTurn(Player player);
    void notifyLastTurn();
    void notifyPlayerSecretObjectives(List<Player> players);
    void notifyCommonObjectives(List<Player> players);
    void notifyWin(Player winner);
}
