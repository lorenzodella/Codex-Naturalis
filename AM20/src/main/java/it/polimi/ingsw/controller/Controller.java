package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.InvalidPlayingException;
import it.polimi.ingsw.controller.messages.ConnectionAckMessage;
import it.polimi.ingsw.controller.exceptions.StopGameException;
import it.polimi.ingsw.controller.messages.AcknowledgeMessage;
import it.polimi.ingsw.controller.messages.Message;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.GameObservable;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Controller implements GameManager {
    private static final int NOGAME = -1;
    private static final int PRELIMINARY = 0;
    private static final int STARTER = 1;
    private static final int OBJECTIVES = 2;
    private static final int PLAY = 3;
    private static final int PICK = 4;
    private int phase;

    private GameObservable gameModel;
    private MessageBuilder messageBuilder;
    private List<String> players;
    private int numPlayers;
    private int missingTurns = -1;

    public Controller(){
        phase = NOGAME;
    }

    public GameObservable getGameModel() {
        return gameModel;
    }

    public GameObserver getMessageBuilder() {
        return messageBuilder;
    }

    public List<String> getPlayers() {
        return players;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getMissingTurns() {
        return missingTurns;
    }

    @Override
    public Message newGame(String playerNickname, int numPlayers) throws InvalidArgumentException, InvalidPlayingException {
        if(phase!=NOGAME)
            throw new InvalidPlayingException("A game already started");
        if(numPlayers<2 || numPlayers>4)
            throw new InvalidArgumentException("numPlayers", numPlayers);
        players = new ArrayList<>();
        players.add(playerNickname);
        this.numPlayers = numPlayers;
        Message tmp = new Message();
        tmp.setResult("You created a new game");
        phase = PRELIMINARY;
        return tmp;
    }

    /**
     * This method allows to disconnect a player from the game (for example because of network failure).
     * Return messages contain the number of remaining players: if there's only one, caller should start a timer in order to end game.
     * @param nickname player who disconnected
     * @return messages to be sent to connected players
     * @throws InvalidConnectionStateException if player is already disconnected
     * @throws InvalidArgumentException if player is not part of current game
     * @throws StopGameException game should stop if player disconnected during first phase of the game or if all players disconnected
     */
    @Override
    public HashMap<String, AcknowledgeMessage> disconnectPlayer(String nickname)
            throws InvalidConnectionStateException, InvalidArgumentException, StopGameException {
        //if you are not a player of current game
        if(!players.contains(nickname))
            throw new InvalidArgumentException("nickname", nickname);
        //if actual game not yet started
        if(phase<PLAY)
            throw new StopGameException("A player disconnected during preliminary phase of the game");

        gameModel.setPlayerConnection(nickname, false);
        Set<String> connectedPlayers = gameModel.getConnectedPlayers();
        if(connectedPlayers.isEmpty())
            throw new StopGameException("No one is connected");

        messageBuilder = new MessageBuilder(connectedPlayers);
        HashMap<String, AcknowledgeMessage> msg = messageBuilder.notifyPlayerDisconnected(nickname);

        //if you are the current player, pass turn to next player
        if(gameModel.getCurrPlayer().getNickname().equals(nickname))
            msg = checkEndGame();

        return msg;
    }

    /**
     * This method allows a player to reconnect to the game (if he was a disconnected player of current game)
     * @param nickname player who reconnected
     * @return messages to be sent to connected players
     * @throws CannotJoinGameException if player was already online, or it's not part of current game
     */
    private HashMap<String, ConnectionAckMessage> reconnectPlayer(String nickname) throws CannotJoinGameException {
        try {
            Player p = gameModel.setPlayerConnection(nickname, true);
            messageBuilder = new MessageBuilder(gameModel.getConnectedPlayers());
            return messageBuilder.notifyPlayerReconnected(p, gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        } catch (InvalidConnectionStateException e) {
            //if you are reconnecting but you were already connected
            throw new CannotJoinGameException("A player with that nickname is already playing");
        } catch (InvalidArgumentException e){
            throw new CannotJoinGameException("You are not part of current game");
        }
    }

    /**
     * It's the first method called by a player who connects and wants to join a game.
     * If a game is already created, he joins to that.
     * If no game is been created yet, an exception is thrown.
     * If he was already playing, he disconnected and the reconnected, his connection state is modified.
     * @param playerNickname nickname which the player wants to use during the game
     * @return messages to be sent to connected players
     * @throws CannotJoinGameException if there is no active game, if nickname is already used, if game is full, if he was already connected
     */
    @Override
    public HashMap<String, ConnectionAckMessage> joinGame(String playerNickname) throws CannotJoinGameException {
        if(phase==NOGAME)
            throw new CannotJoinGameException("No active game");
        //if you are connecting with an already used nickname
        else if(players.contains(playerNickname)){
            //if game started and you are reconnecting
            if(phase>PRELIMINARY)
                return reconnectPlayer(playerNickname);
            //if you are a new player
            else
                throw new CannotJoinGameException("Nickname's already been used");
        }
        //if you are connecting to a game already started
        else if(phase>PRELIMINARY)
            throw new CannotJoinGameException("Game is full");

        //if you are a new player with a new nickname
        players.add(playerNickname);
        HashMap<String, ConnectionAckMessage> tmp = null;
        //positivo
        if(players.size()==numPlayers)
            tmp = startGame();
        //negativo
        if(tmp==null) {
            tmp = new HashMap<>();
            for(String nickname: players){
                tmp.put(nickname, new ConnectionAckMessage());
                tmp.get(nickname).setResult("New player joined");
                tmp.get(nickname).setGameStarts(false);
            }
        }
        return tmp;
    }

    private HashMap<String, ConnectionAckMessage> startGame(){
        phase = STARTER;
        gameModel = new Game(players.stream().map(Player::new).collect(Collectors.toList()));

        this.messageBuilder = new MessageBuilder(gameModel.getConnectedPlayers());
        Deck[] tmp = gameModel.initDecks();
        messageBuilder.notifyDecksCreated(tmp[Deck.RESOURCE_CARDS], tmp[Deck.GOLD_CARDS]);
        List<Player> playerList = gameModel.giveStarterCards();
        messageBuilder.notifyStarterCards(playerList);
        List<Player> playerList2 =  gameModel.giveInitialCards();
        HashMap<String, ConnectionAckMessage> msg = messageBuilder.notifyInitialCards(playerList2);
        for(ConnectionAckMessage message: msg.values()){
            message.setResult("All players connected");
            message.setGameStarts(true);
        }
        return msg;
    }

    //TODO
    @Override
    public void chooseStarterCardSide(String playerNickname, int side) throws InvalidArgumentException, InvalidPlayingException {
        if(phase!=STARTER)
            throw new InvalidPlayingException("You can't position starter card now");
        Player player = gameModel.chooseStarterCardSide(side, playerNickname);
        messageBuilder.notifyStarterCardSide(player); //setta playerinfo
        //check if someone has not played his starterCard yet
        for(Player p : gameModel.getPlayers()){
            if(p.getStarterCard().getOrder() < 0){
                return; //negativo hashmap ha solo 1 messaggio
            }
        }
        List<Player> playersList = gameModel.initObjectiveCards();
        messageBuilder.notifyObjectiveCards(gameModel.getCommonObjectives(), playersList); //setta gli altri
        //positivo hashmap ha 4 messaggi

        phase = OBJECTIVES;
    }

    //TODO
    @Override
    public void chooseObjective(String playerNickname, int index) throws InvalidArgumentException, InvalidPlayingException {
        if(phase!=OBJECTIVES)
            throw new InvalidPlayingException("You can't choose objective now");
        Player player = gameModel.chooseObjective(index, playerNickname);
        messageBuilder.notifyChosenSecretObjective(player); //setti secretobjectives[]
        //check if someone has not chosen his objectiveCard yet
        for(Player p : gameModel.getPlayers()){
            if(p.getSecretObjective()[1] != null){
                return; //negativo hashmap ha solo 1 messaggio
            }
        }
        Player first = gameModel.chooseFirstPlayer();
        messageBuilder.notifyGameStarted(first);
        //positivo hashmap ha 4 messaggi

        phase = PLAY;
    }

    //TODO
    @Override
    public void playCard(String playerNickname, int indexCard, int angle, String targetID, int side)
            throws InvalidArgumentException, TargetNotPresentException,
            InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException,
            InvalidPlayingException {
        if(phase!=PLAY)
            throw new InvalidPlayingException("You can't play a card now");
        Set<String> connectedPlayers = gameModel.getConnectedPlayers();
        if(connectedPlayers.size()==1)
            throw new InvalidPlayingException("You are the only player, wait for the others to reconnect");
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");
        Player p = gameModel.playCard(indexCard, angle, targetID, side);
        messageBuilder.notifyPlayerPlay(p); // setta playerinfo
        if(gameModel.areDeckFinished())
            checkEndGame();
            // negativo
        else
            phase = PICK;
            // settare a true mustpick
            // positivo (setto AcknowledgeMessage)
    }

    //TODO
    @Override
    public void pickCard(String playerNickname, int deck) throws InvalidArgumentException, FinishedCardStackException,
            InvalidPlayingException {
        if(phase!=PICK)
            throw new InvalidPlayingException("You can't draw a card now");
        if(gameModel.getConnectedPlayers().size()==1)
            throw new InvalidPlayingException("You are the only player, wait for the others to reconnect");
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");
        Player p = gameModel.pickCard(deck);
        messageBuilder.notifyPlayerPick(p);
        messageBuilder.notifyDecksModified(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        phase = PLAY;
        checkEndGame();
        //
    }

    //TODO
    @Override
    public void pickCard(String playerNickname, int deck, int index) throws InvalidArgumentException, FinishedCardStackException,
            InvalidPlayingException {
        if(phase!=PICK)
            throw new InvalidPlayingException("You can't draw a card now");
        if(gameModel.getConnectedPlayers().size()==1)
            throw new InvalidPlayingException("You are the only player, wait for the others to reconnect");
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");
        Player p = gameModel.pickCard(deck, index);
        messageBuilder.notifyPlayerPick(p);
        messageBuilder.notifyDecksModified(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        phase = PLAY;
        checkEndGame();
        //
    }

    //TODO
    private HashMap<String, AcknowledgeMessage> checkEndGame(){
        boolean isNewTurn = gameModel.nextTurn();
        //if game ended but last turn not started yet, check if last turn is starting now
        if(gameModel.checkTheEnd() && missingTurns ==-1) {
            missingTurns = 2;
            messageBuilder.notifyLastTurn();
        }
        if(missingTurns >0 && isNewTurn){
            missingTurns--;
        }
        if(missingTurns ==0 && isNewTurn){
            //
            //if last turn is started and ended
            gameModel.computePlayerSecretObjectives();
            List<Player> playerList = gameModel.computeCommonObjectives();
            messageBuilder.notifyPlayerObjectives(playerList);
            Player winner = gameModel.checkWinner();
            messageBuilder.notifyWin(winner);
        }
        else{
            //otherwise simply notify next player to play
            messageBuilder.notifyNextTurn(gameModel.getCurrPlayer());
            //
        }
        return null;
    }
}
