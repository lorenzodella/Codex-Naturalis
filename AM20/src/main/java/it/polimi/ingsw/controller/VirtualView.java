package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VirtualView implements GameObserver {

    private HashMap<String, ChangesMessage> changesMessageHashMap; //messaggi di tutti ibgiocatri per esempio quando fai playCard

    private List<String> playersNickname;

    public VirtualView(List<String> players){
        this.playersNickname = players;
    }

    @Override
    public void notifyDecks(Deck resourceCardDeck, Deck goldCardDeck) {
        if(changesMessageHashMap == null)
            changesMessageHashMap = new HashMap<>();

        for(String nickname : playersNickname){
            if(changesMessageHashMap.get(nickname) == null)
                changesMessageHashMap.put(nickname, new ChangesMessage());

            changesMessageHashMap.get(nickname).setGoldTop(goldCardDeck.getFirstCard());
            changesMessageHashMap.get(nickname).setResourceTop(resourceCardDeck.getFirstCard());
            changesMessageHashMap.get(nickname).setGoldVisible(goldCardDeck.getVisibleCards());
            changesMessageHashMap.get(nickname).setResourceVisible(resourceCardDeck.getVisibleCards());
        }

    }

    @Override
    public void notifyStarterCards(List<Player> players) {
        if(changesMessageHashMap == null)
            changesMessageHashMap = new HashMap<>();

        for(Player p : players){
            if(changesMessageHashMap.get(p.getNickname()) == null)
                changesMessageHashMap.put(p.getNickname(), new ChangesMessage());

            changesMessageHashMap.get(p.getNickname()).setStarterCard(p.getStarterCard());
        }
    }

    @Override
    public void notifyInitialCards(List<Player> players) {
        if(changesMessageHashMap == null)
            changesMessageHashMap = new HashMap<>();

        for(Player p : players){
            if(changesMessageHashMap.get(p.getNickname()) == null)
                changesMessageHashMap.put(p.getNickname(), new ChangesMessage());

            changesMessageHashMap.get(p.getNickname()).setCards(p.getCards());
        }

    }

    @Override
    public void notifyObjectiveCards(ObjectiveCard[] commonObjectives, List<Player> players) {
        if(changesMessageHashMap == null)
            changesMessageHashMap = new HashMap<>();

        for(Player p : players){
            if(changesMessageHashMap.get(p.getNickname()) == null)
                changesMessageHashMap.put(p.getNickname(), new ChangesMessage());

            changesMessageHashMap.get(p.getNickname()).setCommonObjective(commonObjectives);
            changesMessageHashMap.get(p.getNickname()).setSecretObjective(p.getSecretObjective());

        }

    }

    @Override
    public void notifyGameStarted(Player first) {
//        messageHashMap.put(first.getNickname(), new ChangesMessage());
//        messageHashMap.get(first.getNickname()).setResult("It's your turn");
//        if(changesMessageHashMap == null)
//            changesMessageHashMap = new HashMap<>();
//        if(changesMessageHashMap.get(first.getNickname()) == null)
//            changesMessageHashMap.put(first.getNickname(), new ChangesMessage());
//        changesMessageHashMap.get(first.getNickname()).setYourTurn(true);

        if(changesMessageHashMap == null)
            changesMessageHashMap = new HashMap<>();

        for(String nickname : playersNickname){
            if(changesMessageHashMap.get(nickname) == null)
                changesMessageHashMap.put(nickname, new ChangesMessage());

            changesMessageHashMap.get(nickname).setResult("The setup phase's finished and now the game can start");
        }

        changesMessageHashMap.get(first.getNickname()).setYourTurn(true);
        changesMessageHashMap.get(first.getNickname()).setResult("The setup phase's finished and it's your turn");
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
