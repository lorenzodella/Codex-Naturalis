package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.HashMap;
import java.util.List;

public class MessageBuilder implements GameObserver {

    private HashMap<String, ChangesMessage> changesMessageHashMap; //messaggi di tutti ibgiocatri per esempio quando fai playCard

    private List<String> playersNickname;

    public MessageBuilder(List<String> players){
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
        if(changesMessageHashMap == null)
            changesMessageHashMap = new HashMap<>();

        PlayerInfo playerUpdates = new PlayerInfo();
        playerUpdates.setScore(player.getScore());
        playerUpdates.setMap(player.getTable().getMap());
        playerUpdates.setStats(player.getTable().getStats());

        HashMap <String, PlayerInfo> otherPlayerUpdates = new HashMap<>();
        otherPlayerUpdates.put(player.getNickname(), playerUpdates);

        for(String nickname : playersNickname){
            if(changesMessageHashMap.get(nickname) == null)
                changesMessageHashMap.put(nickname, new ChangesMessage());
            if(!nickname.equals(player.getNickname())) {
                changesMessageHashMap.get(nickname).setOthersPlayerInfo(otherPlayerUpdates);
            }
        }
        changesMessageHashMap.get(player.getNickname()).setCards(player.getCards());
        changesMessageHashMap.get(player.getNickname()).setYourPlayerInfo(playerUpdates);
        //messagge??
    }

    @Override
    public void notifyPlayerPick(Player player) {
        if(changesMessageHashMap == null)
            changesMessageHashMap = new HashMap<>();
        if(changesMessageHashMap.get(player.getNickname()) == null)
            changesMessageHashMap.put(player.getNickname(), new ChangesMessage());
        changesMessageHashMap.get(player.getNickname()).setCards(player.getCards());
        //messagge??
    }

    @Override
    public void notifyNextTurn(Player player) {
        if(changesMessageHashMap == null)
            changesMessageHashMap = new HashMap<>();

        for(String nickname : playersNickname){
            if(changesMessageHashMap.get(nickname) == null)
                changesMessageHashMap.put(nickname, new ChangesMessage());
            if(!nickname.equals(player.getNickname())) {
                changesMessageHashMap.get(nickname).setYourTurn(false);
            }
        }

        changesMessageHashMap.get(player.getNickname()).setYourTurn(true);
        //message??
    }

    @Override
    public void notifyLastTurn() {
        if(changesMessageHashMap == null)
            changesMessageHashMap = new HashMap<>();

        for(String nickname : playersNickname){
            if(changesMessageHashMap.get(nickname) == null)
                changesMessageHashMap.put(nickname, new ChangesMessage());
            changesMessageHashMap.get(nickname).setResult("The game's almost done... The last turn starts now!");
        }
    }

    @Override
    public void notifyPlayerSecretObjectives(List<Player> players) {

    }

    @Override
    public void notifyCommonObjectives(List<Player> players) {

    }

    @Override
    public void notifyWin(Player winner) {
        if(changesMessageHashMap == null)
            changesMessageHashMap = new HashMap<>();

        for(String nickname : playersNickname){
            if(changesMessageHashMap.get(nickname) == null)
                changesMessageHashMap.put(nickname, new ChangesMessage());
            if(!winner.getNickname().equals(nickname))
                changesMessageHashMap.get(nickname).setResult(winner.getNickname() + " wins the game!");
        }
        changesMessageHashMap.get(winner.getNickname()).setResult("You're the winner!");
    }

    //TODO
    public void notifyStarterCardSide(String playerNickname){

    }
}
