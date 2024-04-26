package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageBuilder implements GameObserver {

    private HashMap<String, ConnectionAckMessage> connectionAckMessages;
    private HashMap<String, StarterCardAckMessage> starterCardAckMessages;
    private HashMap<String, ObjectiveAckMessage> objectiveAckMessages;
    private HashMap<String, AcknowledgeMessage> acknowledgeMessages;
    private Set<String> connectedPlayerNicknames;

    public MessageBuilder(Set<String> players) {
        this.connectedPlayerNicknames = players;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyPlayerDisconnected(String playerNickname) {
        if(acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();

        for (String nickname : connectedPlayerNicknames) {
            if (acknowledgeMessages.get(nickname) == null)
                acknowledgeMessages.put(nickname, new AcknowledgeMessage());

            acknowledgeMessages.get(nickname).setResult(playerNickname+" disconnected from the game");
            acknowledgeMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
        }
        return acknowledgeMessages;
    }

    @Override
    public HashMap<String, ConnectionAckMessage> notifyPlayerReconnected(Player player, Deck resourceCardDeck, Deck goldCardDeck) {
        if (connectionAckMessages == null)
            connectionAckMessages = new HashMap<>();

        for (String nickname : connectedPlayerNicknames) {
            if (connectionAckMessages.get(nickname) == null)
                connectionAckMessages.put(nickname, new StartGameMessage());

            connectionAckMessages.get(nickname).setResult(player.getNickname()+" reconnected to the game");
        }

        connectionAckMessages.get(player.getNickname()).setResult("You reconnected to the game");
        connectionAckMessages.get(player.getNickname()).setGoldTop(goldCardDeck.getFirstCard());
        connectionAckMessages.get(player.getNickname()).setResourceTop(resourceCardDeck.getFirstCard());
        connectionAckMessages.get(player.getNickname()).setGoldVisible(goldCardDeck.getVisibleCards());
        connectionAckMessages.get(player.getNickname()).setResourceVisible(resourceCardDeck.getVisibleCards());
        connectionAckMessages.get(player.getNickname()).setStarterCard(player.getStarterCard());
        connectionAckMessages.get(player.getNickname()).setInitialCards(player.getCards());
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setMap(player.getTable().getMap());
        playerInfo.setStats(player.getTable().getStats());
        connectionAckMessages.get(player.getNickname()).setPlayerInfo(playerInfo);

        return connectionAckMessages;
    }

    @Override
    public HashMap<String, ConnectionAckMessage> notifyDecksCreated(Deck resourceCardDeck, Deck goldCardDeck) {
        if (connectionAckMessages == null)
            connectionAckMessages = new HashMap<>();

        for (String nickname : connectedPlayerNicknames) {
            if (connectionAckMessages.get(nickname) == null)
                connectionAckMessages.put(nickname, new StartGameMessage());

            connectionAckMessages.get(nickname).setGoldTop(goldCardDeck.getFirstCard());
            connectionAckMessages.get(nickname).setResourceTop(resourceCardDeck.getFirstCard());
            connectionAckMessages.get(nickname).setGoldVisible(goldCardDeck.getVisibleCards());
            connectionAckMessages.get(nickname).setResourceVisible(resourceCardDeck.getVisibleCards());
        }
        return connectionAckMessages;
    }

    @Override
    public HashMap<String, ConnectionAckMessage> notifyStarterCards(List<Player> players) {
        if (connectionAckMessages == null)
            connectionAckMessages = new HashMap<>();

        for (Player p : players) {
            if(connectedPlayerNicknames.contains(p.getNickname())) {
                if (connectionAckMessages.get(p.getNickname()) == null)
                    connectionAckMessages.put(p.getNickname(), new StartGameMessage());

                connectionAckMessages.get(p.getNickname()).setStarterCard(p.getStarterCard());
            }
        }
        return connectionAckMessages;
    }

    @Override
    public HashMap<String, ConnectionAckMessage> notifyInitialCards(List<Player> players) {
        if (connectionAckMessages == null)
            connectionAckMessages = new HashMap<>();

        for (Player p : players) {
            if(connectedPlayerNicknames.contains(p.getNickname())){
                if (connectionAckMessages.get(p.getNickname()) == null)
                    connectionAckMessages.put(p.getNickname(), new StartGameMessage());

                connectionAckMessages.get(p.getNickname()).setInitialCards(p.getCards());
            }
        }
        return connectionAckMessages;
    }

    public HashMap<String, StarterCardAckMessage> notifyStarterCardSide(Player player){
        if(starterCardAckMessages == null)
            starterCardAckMessages = new HashMap<>();

        PlayerInfo playerUpdates = new PlayerInfo();
        playerUpdates.setMap(player.getTable().getMap());
        playerUpdates.setStats(player.getTable().getStats());

        HashMap <String, PlayerInfo> otherPlayerUpdates = new HashMap<>();
        otherPlayerUpdates.put(player.getNickname(), playerUpdates);

        for(String nickname : connectedPlayerNicknames){
            if(starterCardAckMessages.get(nickname) == null)
                starterCardAckMessages.put(nickname, new StarterCardAckMessage());
            if(!nickname.equals(player.getNickname())) {
                starterCardAckMessages.get(nickname).setOthersPlayerInfo(otherPlayerUpdates);
            }
        }
        starterCardAckMessages.get(player.getNickname()).setPlayerInfo(playerUpdates);
        return starterCardAckMessages;
    }

    @Override
    public HashMap<String, StarterCardAckMessage> notifyObjectiveCards(ObjectiveCard[] commonObjectives, List<Player> players) {
        if(starterCardAckMessages == null)
            starterCardAckMessages = new HashMap<>();

        for(Player p : players){
            if(connectedPlayerNicknames.contains(p.getNickname())){
                if (starterCardAckMessages.get(p.getNickname()) == null)
                    starterCardAckMessages.put(p.getNickname(), new StartChoosingObjectiveMessage());

                starterCardAckMessages.get(p.getNickname()).setCommonObjectives(commonObjectives);
                starterCardAckMessages.get(p.getNickname()).setSecretObjectives(p.getSecretObjective());
            }
        }
        return starterCardAckMessages;
    }

    @Override
    public HashMap<String, ObjectiveAckMessage> notifyChosenSecretObjective(Player player) {
        if(objectiveAckMessages == null)
            objectiveAckMessages = new HashMap<>();
        if(objectiveAckMessages.get(player.getNickname()) == null)
            objectiveAckMessages.put(player.getNickname(), new ObjectiveAckMessage());
        objectiveAckMessages.get(player.getNickname()).setSecretObjectives(player.getSecretObjective());
        return objectiveAckMessages;
    }
    @Override
    public HashMap<String, ObjectiveAckMessage> notifyGameStarted(Player first) {
        if(objectiveAckMessages == null)
            objectiveAckMessages = new HashMap<>();

        for(String nickname : connectedPlayerNicknames){
            if(objectiveAckMessages.get(nickname) == null)
                objectiveAckMessages.put(nickname, new StartPlayingMessage());

            objectiveAckMessages.get(nickname).setResult("The setup phase's finished and now the game can start");
            objectiveAckMessages.get(nickname).setFirstPlayer(first.getNickname());
        }
        objectiveAckMessages.get(first.getNickname()).setResult("The setup phase's finished and it's your turn");
        return objectiveAckMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyPlayerPlay(Player player) {
        if(acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();

        PlayerInfo playerUpdates = new PlayerInfo();
        playerUpdates.setScore(player.getScore());
        playerUpdates.setMap(player.getTable().getMap());
        playerUpdates.setStats(player.getTable().getStats());

        HashMap <String, PlayerInfo> otherPlayerUpdates = new HashMap<>();
        otherPlayerUpdates.put(player.getNickname(), playerUpdates);

        for(String nickname : connectedPlayerNicknames){
            if(acknowledgeMessages.get(nickname) == null)
                acknowledgeMessages.put(nickname, new PlayAckMessage());
            if(!nickname.equals(player.getNickname())) {
                acknowledgeMessages.get(nickname).setOthersPlayerInfo(otherPlayerUpdates);
            }
        }
        acknowledgeMessages.get(player.getNickname()).setCards(player.getCards());
        acknowledgeMessages.get(player.getNickname()).setYourPlayerInfo(playerUpdates);
        //messagge??
        return acknowledgeMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyPlayerPick(Player player) {
        if(acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();
        if(acknowledgeMessages.get(player.getNickname()) == null)
            acknowledgeMessages.put(player.getNickname(), new PickAckMessage());
        acknowledgeMessages.get(player.getNickname()).setCards(player.getCards());
        //messagge??
        return acknowledgeMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyDecksModified(Deck resourceCardDeck, Deck goldCardDeck) {
        if (acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();

        for (String nickname : connectedPlayerNicknames) {
            if (acknowledgeMessages.get(nickname) == null)
                acknowledgeMessages.put(nickname, new PickAckMessage());

            acknowledgeMessages.get(nickname).setGoldTop(goldCardDeck.getFirstCard());
            acknowledgeMessages.get(nickname).setResourceTop(resourceCardDeck.getFirstCard());
            acknowledgeMessages.get(nickname).setGoldVisible(goldCardDeck.getVisibleCards());
            acknowledgeMessages.get(nickname).setResourceVisible(resourceCardDeck.getVisibleCards());
        }
        return acknowledgeMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyNextTurn(Player player) {
        if(acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();

        for(String nickname : connectedPlayerNicknames){
            if(acknowledgeMessages.get(nickname) == null)
                acknowledgeMessages.put(nickname, new AcknowledgeMessage());
            acknowledgeMessages.get(nickname).setNextPlayer(player.getNickname());
        }
        return acknowledgeMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyLastTurn() {
        if(acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();

        for(String nickname : connectedPlayerNicknames){
            if(acknowledgeMessages.get(nickname) == null)
                acknowledgeMessages.put(nickname, new AcknowledgeMessage());
            acknowledgeMessages.get(nickname).setResult("The game's almost done... The last turn starts now!");
        }
        return acknowledgeMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyPlayerObjectives(List<Player> players) {
        if(acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();

        HashMap <String, PlayerInfo> otherPlayerUpdates = new HashMap<>();
        for(Player player: players){
            PlayerInfo playerUpdates = new PlayerInfo();
            playerUpdates.setScore(player.getScore());
            otherPlayerUpdates.put(player.getNickname(), playerUpdates);
        }

        for(String nickname : connectedPlayerNicknames){
            if(acknowledgeMessages.get(nickname) == null)
                acknowledgeMessages.put(nickname, new AcknowledgeMessage());

            acknowledgeMessages.get(nickname).setYourPlayerInfo(otherPlayerUpdates.get(nickname));
            acknowledgeMessages.get(nickname).setOthersPlayerInfo(
                    otherPlayerUpdates.entrySet().stream()
                            .filter(e -> !e.getKey().equals(nickname))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, HashMap::new))
            );
        }
        return acknowledgeMessages;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> notifyWin(Player winner) {
        if(acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();

        for(String nickname : connectedPlayerNicknames){
            if(acknowledgeMessages.get(nickname) == null)
                acknowledgeMessages.put(nickname, new AcknowledgeMessage());
            if(!winner.getNickname().equals(nickname))
                acknowledgeMessages.get(nickname).setResult(winner.getNickname() + " wins the game!");
        }
        acknowledgeMessages.get(winner.getNickname()).setResult("You're the winner!");
        return acknowledgeMessages;
    }



}
