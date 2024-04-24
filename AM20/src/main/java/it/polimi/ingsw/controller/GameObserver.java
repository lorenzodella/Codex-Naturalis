package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.HashMap;
import java.util.List;

public interface GameObserver {
    HashMap<String, ConnectionAckMessage> notifyDecksCreated(Deck resourceCardDeck, Deck goldCardDeck);
    HashMap<String, ConnectionAckMessage> notifyStarterCards(List<Player> players);
    HashMap<String, ConnectionAckMessage> notifyInitialCards(List<Player> players);
    HashMap<String, StarterCardAckMessage> notifyStarterCardSide(Player player);
    HashMap<String, StarterCardAckMessage> notifyObjectiveCards(ObjectiveCard[] commonObjectives, List<Player> players);
    HashMap<String, ObjectiveAckMessage> notifyChosenSecretObjective(Player player);
    HashMap<String, ObjectiveAckMessage> notifyGameStarted(Player first);
    HashMap<String, AcknowledgeMessage> notifyPlayerPlay(Player player);
    HashMap<String, AcknowledgeMessage> notifyPlayerPick(Player player);
    HashMap<String, AcknowledgeMessage> notifyDecksModified(Deck resourceCardDeck, Deck goldCardDeck);
    HashMap<String, AcknowledgeMessage> notifyNextTurn(Player player);
    HashMap<String, AcknowledgeMessage> notifyLastTurn();
    HashMap<String, AcknowledgeMessage> notifyPlayerObjectives(List<Player> players);
    HashMap<String, AcknowledgeMessage> notifyWin(Player winner);
}
