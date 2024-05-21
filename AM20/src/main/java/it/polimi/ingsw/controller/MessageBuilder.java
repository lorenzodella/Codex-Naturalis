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

    /**
     * this attribute stands for a map that, per each nickname (string), associates thier specific ConnectionAckMessage
     */
    private HashMap<String, ConnectionAckMessage> connectionAckMessages;
    /**
     * this attribute stands for a map that, per each nickname (string), associates thier specific starterCardAckMessage
     */
    private HashMap<String, StarterCardAckMessage> starterCardAckMessages;
    /**
     * this attribute stands for a map that, per each nickname (string), associates thier specific objectiveAckMessage
     */
    private HashMap<String, ObjectiveAckMessage> objectiveAckMessages;
    /**
     * this attribute stands for a map that, per each nickname (string), associates thier specific AcknowledgeMessage
     */
    private HashMap<String, AcknowledgeMessage> acknowledgeMessages;
    /**
     * this attribute is a set of all the connected players
     */
    private Set<String> connectedPlayerNicknames;

    //LISTA SOLO DEI CONNECTED PLAYERS DATO CHE DEVE MANDARE MESSAGGI SOLO AI PLAYER CHE SONO DAVVERO CONNESSI
    public MessageBuilder(Set<String> players) {
        this.connectedPlayerNicknames = players;
    }

    /**
     * Notifies all the connected players that a player has disconnected from the game
     * @param playerNickname the nickname of the player that disconnected
     * @return a map containing the messages to be sent to the connected players
     */
    @Override
    public HashMap<String, AcknowledgeMessage> notifyPlayerDisconnected(String playerNickname) {
        if(acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();

        for (String nickname : connectedPlayerNicknames) {
            if (acknowledgeMessages.get(nickname) == null)
                acknowledgeMessages.put(nickname, new AcknowledgeMessage());

            acknowledgeMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
            acknowledgeMessages.get(nickname).setResult(playerNickname+" disconnected from the game");
            acknowledgeMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
        }
        return acknowledgeMessages;
    }

    /**
     * Notifies all the connected players that a player has reconnected to the game
     * @param players the list of players
     * @param resourceCardDeck the resource card deck
     * @param goldCardDeck the gold card deck
     * @return a map containing the messages to be sent to the connected players
     */
    @Override
    public HashMap<String, ConnectionAckMessage> notifyPlayerReconnected(List<Player> players, Deck resourceCardDeck, Deck goldCardDeck) {
        if (connectionAckMessages == null)
            connectionAckMessages = new HashMap<>();

        Player player = players.get(0);

        for (String nickname : connectedPlayerNicknames) {
            if (connectionAckMessages.get(nickname) == null)
                connectionAckMessages.put(nickname, new StartGameMessage());

            connectionAckMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
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
        HashMap <String, PlayerInfo> otherPlayerUpdates = new HashMap<>();
        for(Player p: players.stream().filter(x->!x.equals(player)).collect(Collectors.toList())){
            PlayerInfo playerUpdates = new PlayerInfo();
            playerUpdates.setScore(p.getScore());
            otherPlayerUpdates.put(p.getNickname(), playerUpdates);
        }
        connectionAckMessages.get(player.getNickname()).setOthersPlayerInfo(otherPlayerUpdates);

        return connectionAckMessages;
    }

    /**
     * Notifies all the connected players that both decks have been created
     * @param resourceCardDeck deck of all resource cards
     * @param goldCardDeck deck of all gold cards
     * @return a map containing the messages that needs to be sent to all connected players
     */
    @Override
    public HashMap<String, ConnectionAckMessage> notifyDecksCreated(Deck resourceCardDeck, Deck goldCardDeck) {
        if (connectionAckMessages == null)
            connectionAckMessages = new HashMap<>();

        for (String nickname : connectedPlayerNicknames) {
            if (connectionAckMessages.get(nickname) == null)
                connectionAckMessages.put(nickname, new StartGameMessage());

            connectionAckMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
            connectionAckMessages.get(nickname).setGoldTop(goldCardDeck.getFirstCard());
            connectionAckMessages.get(nickname).setResourceTop(resourceCardDeck.getFirstCard());
            connectionAckMessages.get(nickname).setGoldVisible(goldCardDeck.getVisibleCards());
            connectionAckMessages.get(nickname).setResourceVisible(resourceCardDeck.getVisibleCards());
        }
        return connectionAckMessages;
    }

    /**
     * Notifies all the connected players that the starter cards have been created
     * @param players list of player
     * @return a map containing the messages that needs to be sent to all connected players
     */
    @Override
    public HashMap<String, ConnectionAckMessage> notifyStarterCards(List<Player> players) {
        if (connectionAckMessages == null)
            connectionAckMessages = new HashMap<>();

        for (Player p : players) {
            if(connectedPlayerNicknames.contains(p.getNickname())) {
                if (connectionAckMessages.get(p.getNickname()) == null)
                    connectionAckMessages.put(p.getNickname(), new StartGameMessage());

                connectionAckMessages.get(p.getNickname()).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
                connectionAckMessages.get(p.getNickname()).setStarterCard(p.getStarterCard());
            }
        }
        return connectionAckMessages;
    }

    /**
     * Notifies all the connected players that the initial cards have been created
     * @param players list of player
     * @return a map containing the messages that needs to be sent to all connected players
     */
    @Override
    public HashMap<String, ConnectionAckMessage> notifyInitialCards(List<Player> players) {
        if (connectionAckMessages == null)
            connectionAckMessages = new HashMap<>();

        for (Player p : players) {
            if(connectedPlayerNicknames.contains(p.getNickname())){
                if (connectionAckMessages.get(p.getNickname()) == null)
                    connectionAckMessages.put(p.getNickname(), new StartGameMessage());

                connectionAckMessages.get(p.getNickname()).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
                connectionAckMessages.get(p.getNickname()).setInitialCards(p.getCards());
            }
        }
        return connectionAckMessages;
    }

    @Override
    public HashMap<String, ConnectionAckMessage> notifyDefaultPlayerInfo(List<Player> players) {
        if (connectionAckMessages == null)
            connectionAckMessages = new HashMap<>();

        for (Player p : players) {
            if(connectedPlayerNicknames.contains(p.getNickname())){
                if (connectionAckMessages.get(p.getNickname()) == null)
                    connectionAckMessages.put(p.getNickname(), new StartGameMessage());

                connectionAckMessages.get(p.getNickname()).setPlayerInfo(new PlayerInfo());
                connectionAckMessages.get(p.getNickname()).setOthersPlayerInfo(
                        players.stream().filter(x -> !p.getNickname().equals(x.getNickname()))
                                        .collect(Collectors.toMap(
                                            Player::getNickname,
                                            x -> new PlayerInfo(),
                                            (x, y) -> y,
                                            HashMap::new))
                );
            }
        }
        return connectionAckMessages;
    }

    /**
     * Notifies to all the connected players that this specific player "player" has just chosen the side of their card
     * @param player the specific player that's just chosen the side of the starter card
     * @return a map containing the messages that needs to be sent to all connected players
     */
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
                starterCardAckMessages.get(nickname).setResult(player.getNickname()+" has chosen the side of the starter card");
            }
            starterCardAckMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
        }
        starterCardAckMessages.get(player.getNickname()).setPlayerInfo(playerUpdates);
        starterCardAckMessages.get(player.getNickname()).setResult("You chose the side of the starter card and waiting for all player to choose it");
        return starterCardAckMessages;
    }

    /**
     * Notifies to all the connected players that the 2 common objectives have been created
     * @param commonObjectives array of 2 item that contains the 2 common objectives
     * @param players list of players
     * @return a map containing the messages that needs to be sent to all connected players
     */
    @Override
    public HashMap<String, StarterCardAckMessage> notifyObjectiveCards(ObjectiveCard[] commonObjectives, List<Player> players) {
        if(starterCardAckMessages == null)
            starterCardAckMessages = new HashMap<>();

        for(Player p : players){
            if(connectedPlayerNicknames.contains(p.getNickname())){
                if (starterCardAckMessages.get(p.getNickname()) == null)
                    starterCardAckMessages.put(p.getNickname(), new StartChoosingObjectiveMessage());

                starterCardAckMessages.get(p.getNickname()).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
                starterCardAckMessages.get(p.getNickname()).setCommonObjectives(commonObjectives);
                starterCardAckMessages.get(p.getNickname()).setSecretObjectives(p.getSecretObjective());
            }
        }
        return starterCardAckMessages;
    }

    /**
     * Notifies to all the connected players that this specific player "player" has just chosen their secret objective card
     * @param player the specific player that's just chosen the objective card
     * @return a map containing the messages that needs to be sent to all connected players
     */
    @Override
    public HashMap<String, ObjectiveAckMessage> notifyChosenSecretObjective(Player player) {
        if(objectiveAckMessages == null)
            objectiveAckMessages = new HashMap<>();
        if(objectiveAckMessages.get(player.getNickname()) == null)
            objectiveAckMessages.put(player.getNickname(), new ObjectiveAckMessage());
        objectiveAckMessages.get(player.getNickname()).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
        objectiveAckMessages.get(player.getNickname()).setSecretObjectives(player.getSecretObjective());
        objectiveAckMessages.get(player.getNickname()).setResult("You chose your secret objective. Waiting for the other player");
        return objectiveAckMessages;
    }
    @Override
    public HashMap<String, ObjectiveAckMessage> notifyGameStarted(Player first) {
        if(objectiveAckMessages == null)
            objectiveAckMessages = new HashMap<>();

        for(String nickname : connectedPlayerNicknames){
            if(objectiveAckMessages.get(nickname) == null)
                objectiveAckMessages.put(nickname, new StartPlayingMessage());

            objectiveAckMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
            objectiveAckMessages.get(nickname).setFirstPlayer(first.getNickname());
        }
        return objectiveAckMessages;
    }

    /**
     * Notifies to all the connected players that this specific player has just played a card
     * @param player the specific player that's just played a card
     * @return a map containing the messages that needs to be sent to all connected players
     */
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
                acknowledgeMessages.get(nickname).setResult(player.getNickname() + " played a card");
                acknowledgeMessages.get(nickname).setMustPick(false);
            }

            acknowledgeMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
            acknowledgeMessages.get(nickname).setNextPlayer(player.getNickname());
        }
        acknowledgeMessages.get(player.getNickname()).setCards(player.getCards());
        acknowledgeMessages.get(player.getNickname()).setYourPlayerInfo(playerUpdates);

        acknowledgeMessages.get(player.getNickname()).setResult("You just played a card");
        acknowledgeMessages.get(player.getNickname()).setMustPick(true);
        return acknowledgeMessages;
    }
    /**
     * Notifies to all the connected players that this specific player has just picked a card
     * @param player the specific player that's just picked a card
     * @return a map containing the messages that needs to be sent to all connected players
     */
    @Override
    public HashMap<String, AcknowledgeMessage> notifyPlayerPick(Player player) {
        if(acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();
        //messaggio per tutti
        for(String nickname : connectedPlayerNicknames){
            if(acknowledgeMessages.get(nickname) == null)
                acknowledgeMessages.put(nickname, new PickAckMessage());

            acknowledgeMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
            acknowledgeMessages.get(nickname).setResult(player.getNickname()+ " just picked a card");
        }
        acknowledgeMessages.get(player.getNickname()).setCards(player.getCards());
        //messaggio al singolo
        acknowledgeMessages.get(player.getNickname()).setResult("You just picked a card");
        return acknowledgeMessages;
    }

    /**
     * Notifies to all connected players that the decks have changed because someone's just picked a card
     * @param resourceCardDeck the resource deck
     * @param goldCardDeck the gold deck
     * @return a map containing the messages that needs to be sent to all connected players
     */
    @Override
    public HashMap<String, AcknowledgeMessage> notifyDecksModified(Deck resourceCardDeck, Deck goldCardDeck) {
        if (acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();

        for (String nickname : connectedPlayerNicknames) {
            if (acknowledgeMessages.get(nickname) == null)
                acknowledgeMessages.put(nickname, new PickAckMessage());

            acknowledgeMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
            acknowledgeMessages.get(nickname).setGoldTop(goldCardDeck.getFirstCard());
            acknowledgeMessages.get(nickname).setResourceTop(resourceCardDeck.getFirstCard());
            acknowledgeMessages.get(nickname).setGoldVisible(goldCardDeck.getVisibleCards());
            acknowledgeMessages.get(nickname).setResourceVisible(resourceCardDeck.getVisibleCards());
        }
        return acknowledgeMessages;
    }

    /**
     * Notifies to all connected players that the next player is going to be "player"
     * @param player this specific player
     * @return a map containing the messages that needs to be sent to all connected players
     */
    @Override
    public HashMap<String, AcknowledgeMessage> notifyNextTurn(Player player) {
        if(acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();

        for(String nickname : connectedPlayerNicknames){
            if(acknowledgeMessages.get(nickname) == null)
                acknowledgeMessages.put(nickname, new AcknowledgeMessage());

            acknowledgeMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
            acknowledgeMessages.get(nickname).setNextPlayer(player.getNickname());
            acknowledgeMessages.get(nickname).setMustPick(false);
        }
        return acknowledgeMessages;
    }

    /**
     * Notifies to all connected players that the final turn's just started
     * @return a map containing the messages that needs to be sent to all connected players
     */
    @Override
    public HashMap<String, AcknowledgeMessage> notifyLastTurn() {
        if(acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();

        for(String nickname : connectedPlayerNicknames){
            if(acknowledgeMessages.get(nickname) == null)
                acknowledgeMessages.put(nickname, new AcknowledgeMessage());

            acknowledgeMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
            acknowledgeMessages.get(nickname).setResult("The game's almost done... The last turn starts now!");
        }
        return acknowledgeMessages;
    }
//TODO
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

            acknowledgeMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
            acknowledgeMessages.get(nickname).setYourPlayerInfo(otherPlayerUpdates.get(nickname));
            acknowledgeMessages.get(nickname).setOthersPlayerInfo(
                    otherPlayerUpdates.entrySet().stream()
                            .filter(e -> !e.getKey().equals(nickname))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, HashMap::new))
            );
        }
        return acknowledgeMessages;
    }

    /**
     * Notifies to all connected players that the winner of this game is the player "winner"
     * @param winner this specific player that's just won
     * @return a map containing the messages that needs to be sent to all connected players
     */
    @Override
    public HashMap<String, AcknowledgeMessage> notifyWin(Player winner) {
        if(acknowledgeMessages == null)
            acknowledgeMessages = new HashMap<>();

        for(String nickname : connectedPlayerNicknames){
            if(acknowledgeMessages.get(nickname) == null)
                acknowledgeMessages.put(nickname, new AcknowledgeMessage());
            if(!winner.getNickname().equals(nickname))
                acknowledgeMessages.get(nickname).setResult(winner.getNickname() + " wins the game!");

            acknowledgeMessages.get(nickname).setNumOfConnectedPlayers(connectedPlayerNicknames.size());
        }
        acknowledgeMessages.get(winner.getNickname()).setResult("You're the winner!");
        return acknowledgeMessages;
    }
}
