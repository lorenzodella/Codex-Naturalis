package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.ArrayList;
import java.util.List;

public class VirtualView implements GameObserver {
    GameManager observer;

    public VirtualView(){
        parseCommand("JOIN{name: 'p1'}");
    }

    public void setObserver(GameManager observer){
        this.observer = observer;
    }

    public void parseCommand(String string){
        try {
            if(string.contains("JOIN"))
                observer.joinGame(string.split("'")[1]);
        } catch (CannotJoinGameException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void notifyDecks(Deck resourceCardDeck, Deck goldCardDeck) {

    }

    @Override
    public void notifyStarterCards(List<Player> players) {

    }

    @Override
    public void notifyInitialCards(List<Player> players) {

    }

    @Override
    public void notifyObjectiveCards(ObjectiveCard[] commonObjectives, List<Player> players) {

    }

    @Override
    public void notifyGameStarted(Player first) {

    }

    @Override
    public void notifyPlayerPlay(Player player) {
    }

    @Override
    public void notifyPlayerPick(Player player) {

    }

    @Override
    public void notifyNextTurn(Player player) {

    }

    @Override
    public void notifyLastTurn() {

    }

    @Override
    public void notifyPlayerSecretObjectives(List<Player> players) {

    }

    @Override
    public void notifyCommonObjectives(List<Player> players) {

    }

    @Override
    public void notifyWin(Player winner) {

    }
}
