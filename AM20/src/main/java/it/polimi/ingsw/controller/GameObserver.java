package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.messages.AcknowledgeMessage;
import it.polimi.ingsw.controller.messages.StartChoosingObjectiveMessage;
import it.polimi.ingsw.controller.messages.StartGameMessage;
import it.polimi.ingsw.controller.messages.StartPlayingMessage;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.HashMap;
import java.util.List;

public interface GameObserver {
    HashMap<String, StartGameMessage> notifyDecksCreated(Deck resourceCardDeck, Deck goldCardDeck);
    HashMap<String, StartGameMessage> notifyStarterCards(List<Player> players);
    HashMap<String, StartGameMessage> notifyInitialCards(List<Player> players);
    HashMap<String, StartChoosingObjectiveMessage> notifyStarterCardSide(Player player);
    HashMap<String, StartChoosingObjectiveMessage> notifyObjectiveCards(ObjectiveCard[] commonObjectives, List<Player> players);
    HashMap<String, StartPlayingMessage> notifyChosenSecretObjective(Player player);
    HashMap<String, StartPlayingMessage> notifyGameStarted(Player first);
    HashMap<String, AcknowledgeMessage> notifyPlayerPlay(Player player);
    HashMap<String, AcknowledgeMessage> notifyPlayerPick(Player player);
    HashMap<String, AcknowledgeMessage> notifyDecksModified(Deck resourceCardDeck, Deck goldCardDeck);
    HashMap<String, AcknowledgeMessage> notifyNextTurn(Player player);
    HashMap<String, AcknowledgeMessage> notifyLastTurn();
    HashMap<String, AcknowledgeMessage> notifyPlayerObjectives(List<Player> players);
    HashMap<String, AcknowledgeMessage> notifyWin(Player winner);
}
