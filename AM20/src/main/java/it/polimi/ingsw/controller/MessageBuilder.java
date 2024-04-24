package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageBuilder implements GameObserver {

    private HashMap<String, ConnectionAckMessage> playerStartGameMessages;
    private HashMap<String, StarterCardAckMessage> playerStartChoosingObjectiveMessages;
    private HashMap<String, ObjectiveAckMessage> playerStartPlayingMessages;
    private HashMap<String, AcknowledgeMessage> playerAcknowledgeMessages;


    private List<String> playersNickname;

    public MessageBuilder(List<String> players) {
        this.playersNickname = players;
    }

    @Override
    public HashMap<String, ConnectionAckMessage> notifyDecksCreated(Deck resourceCardDeck, Deck goldCardDeck) {
        if (playerStartGameMessages == null)
            playerStartGameMessages = new HashMap<>();

        for (String nickname : playersNickname) {
            if (playerStartGameMessages.get(nickname) == null)
                playerStartGameMessages.put(nickname, new StartGameMessage());

            playerStartGameMessages.get(nickname).setGoldTop(goldCardDeck.getFirstCard());
            playerStartGameMessages.get(nickname).setResourceTop(resourceCardDeck.getFirstCard());
            playerStartGameMessages.get(nickname).setGoldVisible(goldCardDeck.getVisibleCards());
            playerStartGameMessages.get(nickname).setResourceVisible(resourceCardDeck.getVisibleCards());
        }
        return playerStartGameMessages;
    }

    @Override
    public HashMap<String, ConnectionAckMessage> notifyStarterCards(List<Player> players) {
        if (playerStartGameMessages == null)
            playerStartGameMessages = new HashMap<>();

        for (Player p : players) {
            if (playerStartGameMessages.get(p.getNickname()) == null)
                playerStartGameMessages.put(p.getNickname(), new StartGameMessage());

            playerStartGameMessages.get(p.getNickname()).setStarterCard(p.getStarterCard());
        }
        return playerStartGameMessages;
    }

    @Override
    public HashMap<String, ConnectionAckMessage> notifyInitialCards(List<Player> players) {
        if (playerStartGameMessages == null)
            playerStartGameMessages = new HashMap<>();

        for (Player p : players) {
            if (playerStartGameMessages.get(p.getNickname()) == null)
                playerStartGameMessages.put(p.getNickname(), new StartGameMessage());

            playerStartGameMessages.get(p.getNickname()).setInitialCards(p.getCards());
        }
        return playerStartGameMessages;
    }

    public HashMap<String, StarterCardAckMessage> notifyStarterCardSide(Player player){
        if(playerStartChoosingObjectiveMessages == null)
            playerStartChoosingObjectiveMessages = new HashMap<>();

        PlayerInfo playerUpdates = new PlayerInfo();
        playerUpdates.setMap(player.getTable().getMap());
        playerUpdates.setStats(player.getTable().getStats());

        HashMap <String, PlayerInfo> otherPlayerUpdates = new HashMap<>();
        otherPlayerUpdates.put(player.getNickname(), playerUpdates);

        for(String nickname : playersNickname){
            if(playerStartChoosingObjectiveMessages.get(nickname) == null)
                playerStartChoosingObjectiveMessages.put(nickname, new StarterCardAckMessage());
            if(!nickname.equals(player.getNickname())) {
                playerStartChoosingObjectiveMessages.get(nickname).setOthersPlayerInfo(otherPlayerUpdates);
            }
        }
        playerStartChoosingObjectiveMessages.get(player.getNickname()).setPlayerInfo(playerUpdates);
        return playerStartChoosingObjectiveMessages;
    }

    @Override
    public HashMap<String, StarterCardAckMessage> notifyObjectiveCards(ObjectiveCard[] commonObjectives, List<Player> players) {
        if(playerStartChoosingObjectiveMessages == null)
            playerStartChoosingObjectiveMessages = new HashMap<>();

        for(Player p : players){
            if(playerStartChoosingObjectiveMessages.get(p.getNickname()) == null)
                playerStartChoosingObjectiveMessages.put(p.getNickname(), new StartChoosingObjectiveMessage());

            playerStartChoosingObjectiveMessages.get(p.getNickname()).setCommonObjectives(commonObjectives);
            playerStartChoosingObjectiveMessages.get(p.getNickname()).setSecretObjectives(p.getSecretObjective());
        }
        return playerStartChoosingObjectiveMessages;
    }

    @Override
    public HashMap<String, ObjectiveAckMessage> notifyChosenSecretObjective(Player player) {
        if(playerStartPlayingMessages == null)
            playerStartPlayingMessages = new HashMap<>();
        if(playerStartPlayingMessages.get(player.getNickname()) == null)
            playerStartPlayingMessages.put(player.getNickname(), new ObjectiveAckMessage());
        playerStartPlayingMessages.get(player.getNickname()).setSecretObjectives(player.getSecretObjective());
        return playerStartPlayingMessages;
    }
    @Override
    public HashMap<String, ObjectiveAckMessage> notifyGameStarted(Player first) {
        if(playerStartPlayingMessages == null)
            playerStartPlayingMessages = new HashMap<>();

        for(String nickname : playersNickname){
            if(playerStartPlayingMessages.get(nickname) == null)
                playerStartPlayingMessages.put(nickname, new StartPlayingMessage());

            playerStartPlayingMessages.get(nickname).setResult("The setup phase's finished and now the game can start");
            playerStartPlayingMessages.get(nickname).setFirstPlayer(first.getNickname());
        }
        playerStartPlayingMessages.get(first.getNickname()).setResult("The setup phase's finished and it's your turn");
        return playerStartPlayingMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyPlayerPlay(Player player) {
        if(playerAcknowledgeMessages == null)
            playerAcknowledgeMessages = new HashMap<>();

        PlayerInfo playerUpdates = new PlayerInfo();
        playerUpdates.setScore(player.getScore());
        playerUpdates.setMap(player.getTable().getMap());
        playerUpdates.setStats(player.getTable().getStats());

        HashMap <String, PlayerInfo> otherPlayerUpdates = new HashMap<>();
        otherPlayerUpdates.put(player.getNickname(), playerUpdates);

        for(String nickname : playersNickname){
            if(playerAcknowledgeMessages.get(nickname) == null)
                playerAcknowledgeMessages.put(nickname, new PlayAckMessage());
            if(!nickname.equals(player.getNickname())) {
                playerAcknowledgeMessages.get(nickname).setOthersPlayerInfo(otherPlayerUpdates);
            }
        }
        playerAcknowledgeMessages.get(player.getNickname()).setCards(player.getCards());
        playerAcknowledgeMessages.get(player.getNickname()).setYourPlayerInfo(playerUpdates);
        //messagge??
        return playerAcknowledgeMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyPlayerPick(Player player) {
        if(playerAcknowledgeMessages == null)
            playerAcknowledgeMessages = new HashMap<>();
        if(playerAcknowledgeMessages.get(player.getNickname()) == null)
            playerAcknowledgeMessages.put(player.getNickname(), new PickAckMessage());
        playerAcknowledgeMessages.get(player.getNickname()).setCards(player.getCards());
        //messagge??
        return playerAcknowledgeMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyDecksModified(Deck resourceCardDeck, Deck goldCardDeck) {
        if (playerAcknowledgeMessages == null)
            playerAcknowledgeMessages = new HashMap<>();

        for (String nickname : playersNickname) {
            if (playerAcknowledgeMessages.get(nickname) == null)
                playerAcknowledgeMessages.put(nickname, new PickAckMessage());

            playerAcknowledgeMessages.get(nickname).setGoldTop(goldCardDeck.getFirstCard());
            playerAcknowledgeMessages.get(nickname).setResourceTop(resourceCardDeck.getFirstCard());
            playerAcknowledgeMessages.get(nickname).setGoldVisible(goldCardDeck.getVisibleCards());
            playerAcknowledgeMessages.get(nickname).setResourceVisible(resourceCardDeck.getVisibleCards());
        }
        return playerAcknowledgeMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyNextTurn(Player player) {
        if(playerAcknowledgeMessages == null)
            playerAcknowledgeMessages = new HashMap<>();

        //va cambiato perché non usiamo piu il boolean isyourturn ma settiamo il nickanme del possimo player
        for(String nickname : playersNickname){
            if(playerAcknowledgeMessages.get(nickname) == null)
                playerAcknowledgeMessages.put(nickname, new AcknowledgeMessage());
            if(!nickname.equals(player.getNickname())) {
                playerAcknowledgeMessages.get(nickname).setNextPlayer(player.getNickname());
            }
        }
        return playerAcknowledgeMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyLastTurn() {
        if(playerAcknowledgeMessages == null)
            playerAcknowledgeMessages = new HashMap<>();

        for(String nickname : playersNickname){
            if(playerAcknowledgeMessages.get(nickname) == null)
                playerAcknowledgeMessages.put(nickname, new AcknowledgeMessage());
            playerAcknowledgeMessages.get(nickname).setResult("The game's almost done... The last turn starts now!");
        }
        return playerAcknowledgeMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyPlayerObjectives(List<Player> players) {
        if(playerAcknowledgeMessages == null)
            playerAcknowledgeMessages = new HashMap<>();

        HashMap <String, PlayerInfo> otherPlayerUpdates = new HashMap<>();
        for(Player player: players){
            PlayerInfo playerUpdates = new PlayerInfo();
            playerUpdates.setScore(player.getScore());
            otherPlayerUpdates.put(player.getNickname(), playerUpdates);
        }

        for(String nickname : playersNickname){
            if(playerAcknowledgeMessages.get(nickname) == null)
                playerAcknowledgeMessages.put(nickname, new AcknowledgeMessage());

            playerAcknowledgeMessages.get(nickname).setYourPlayerInfo(otherPlayerUpdates.get(nickname));
            playerAcknowledgeMessages.get(nickname).setOthersPlayerInfo(
                    otherPlayerUpdates.entrySet().stream()
                            .filter(e -> !e.getKey().equals(nickname))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, HashMap::new))
            );
        }
        return playerAcknowledgeMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyWin(Player winner) {
        if(playerAcknowledgeMessages == null)
            playerAcknowledgeMessages = new HashMap<>();

        for(String nickname : playersNickname){
            if(playerAcknowledgeMessages.get(nickname) == null)
                playerAcknowledgeMessages.put(nickname, new AcknowledgeMessage());
            if(!winner.getNickname().equals(nickname))
                playerAcknowledgeMessages.get(nickname).setResult(winner.getNickname() + " wins the game!");
        }
        playerAcknowledgeMessages.get(winner.getNickname()).setResult("You're the winner!");
        return playerAcknowledgeMessages;
    }



}
