package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.InvalidPlayingException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.controller.exceptions.StopGameException;
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
        //SE UN PLAYER SI DISCONNETTE DURING PRELIMINARY --> CHIUDO TUTTO
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
        //CREO NUOVO MESSAGE BUILDER PASSANDOGLI LA NUOVA LISTA DEI CONNECTED PLAYERS
        this.messageBuilder = new MessageBuilder(gameModel.getConnectedPlayers());
        Deck[] tmp = gameModel.initDecks();
        messageBuilder.notifyDecksCreated(tmp[Deck.RESOURCE_CARDS], tmp[Deck.GOLD_CARDS]);
        List<Player> playerList = gameModel.giveStarterCards();
        messageBuilder.notifyStarterCards(playerList);
        List<Player> playerList2 =  gameModel.giveInitialCards();
        //QUESTO RITORNA DEGLI STARTGAMEMESSAGE
        HashMap<String, ConnectionAckMessage> msg = messageBuilder.notifyInitialCards(playerList2);
        for(ConnectionAckMessage message: msg.values()){
            message.setResult("All players connected");
            message.setGameStarts(true);
        }
        return msg;
    }

    @Override
    public HashMap<String, StarterCardAckMessage> chooseStarterCardSide(String playerNickname, int side) throws InvalidArgumentException, InvalidPlayingException {
        if(phase!=STARTER)
            throw new InvalidPlayingException("You can't position starter card now");

        //RIINIZIALIZZO LISTA DEI CONNECTED PLAYERS E MANDO AL MESSAGEBUILDER
        this.messageBuilder = new MessageBuilder(gameModel.getConnectedPlayers());

        Player player = gameModel.chooseStarterCardSide(side, playerNickname);
        HashMap<String, StarterCardAckMessage> msg = messageBuilder.notifyStarterCardSide(player); //setta playerinfo

        for(Player p : gameModel.getPlayers()){
            //check if someone has not played his starterCard yet
            if(p.getStarterCard().getOrder() < 0){
                //negativo
                for(StarterCardAckMessage message: msg.values()){
                    message.setChooseObjective(false);
                    message.setResult("You chose the side of the starter card");
                }
                return msg;
            }
        }
        //positivo
        List<Player> playersList = gameModel.initObjectiveCards();
        HashMap<String, StarterCardAckMessage> msg1 = messageBuilder.notifyObjectiveCards(gameModel.getCommonObjectives(), playersList); //setta gli altri
        for(StarterCardAckMessage message: msg1.values()){
            message.setChooseObjective(true);
            message.setResult("Everyone's chosen the side of the starter card");
        }
        phase = OBJECTIVES;
        return msg1;
    }

    @Override
    public HashMap<String, ObjectiveAckMessage> chooseObjective(String playerNickname, int index) throws InvalidArgumentException, InvalidPlayingException {
        if(phase!=OBJECTIVES)
            throw new InvalidPlayingException("You can't choose objective now");

        //RIINIZIALIZZO LISTA DEI CONNECTED PLAYERS E MANDO AL MESSAGEBUILDER
        this.messageBuilder = new MessageBuilder(gameModel.getConnectedPlayers());

        Player player = gameModel.chooseObjective(index, playerNickname);
        HashMap<String, ObjectiveAckMessage> msg = messageBuilder.notifyChosenSecretObjective(player); //setti secretobjectives[]

        for(Player p : gameModel.getPlayers()){
            //check if someone has not chosen his objectiveCard yet
            //negativo
            if(p.getSecretObjective()[1] != null){
                for(ObjectiveAckMessage message: msg.values()){
                    message.setStartPlaying(false);
                    message.setResult("You chose your secret objective");
                }
                return msg;
            }
        }
        Player first = gameModel.chooseFirstPlayer();
        //positivo
        HashMap<String, ObjectiveAckMessage> msg1 = messageBuilder.notifyGameStarted(first);
        for(ObjectiveAckMessage message: msg1.values()){
            message.setStartPlaying(true);
        }
        phase = PLAY;
        return msg1;
    }

    @Override
    public HashMap<String, AcknowledgeMessage> playCard(String playerNickname, int indexCard, int angle, String targetID, int side)
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

        //RIINIZIALIZZO LISTA DEI CONNECTED PLAYERS E MANDO AL MESSAGEBUILDER
        this.messageBuilder = new MessageBuilder(gameModel.getConnectedPlayers());

        HashMap<String, AcknowledgeMessage> msg = messageBuilder.notifyPlayerPlay(p);
        if(gameModel.areDeckFinished()) {
            HashMap<String, AcknowledgeMessage> msg1 = checkEndGame();
            //negativo
            return msg1;
        }
        else {
            phase = PICK;
            //positivo
            return msg;
        }

    }

    @Override
    public HashMap<String, AcknowledgeMessage> pickCard(String playerNickname, int deck) throws InvalidArgumentException, FinishedCardStackException,
            InvalidPlayingException {
        if(phase!=PICK)
            throw new InvalidPlayingException("You can't draw a card now");
        if(gameModel.getConnectedPlayers().size()==1)
            throw new InvalidPlayingException("You are the only player, wait for the others to reconnect");
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");

        //RIINIZIALIZZO LISTA DEI CONNECTED PLAYERS E MANDO AL MESSAGEBUILDER
        this.messageBuilder = new MessageBuilder(gameModel.getConnectedPlayers());

        Player p = gameModel.pickCard(deck);
        messageBuilder.notifyPlayerPick(p);
        messageBuilder.notifyDecksModified(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        phase = PLAY;
        HashMap<String, AcknowledgeMessage> msg = checkEndGame();
        return msg;
    }
    
    @Override
    public HashMap<String, AcknowledgeMessage> pickCard(String playerNickname, int deck, int index) throws InvalidArgumentException, FinishedCardStackException,
            InvalidPlayingException {
        if(phase!=PICK)
            throw new InvalidPlayingException("You can't draw a card now");
        if(gameModel.getConnectedPlayers().size()==1)
            throw new InvalidPlayingException("You are the only player, wait for the others to reconnect");
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");

        //RIINIZIALIZZO LISTA DEI CONNECTED PLAYERS E MANDO AL MESSAGEBUILDER
        this.messageBuilder = new MessageBuilder(gameModel.getConnectedPlayers());

        Player p = gameModel.pickCard(deck, index);
        messageBuilder.notifyPlayerPick(p);
        messageBuilder.notifyDecksModified(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        phase = PLAY;
        HashMap<String, AcknowledgeMessage> msg = checkEndGame();
        return msg;
    }

    private HashMap<String, AcknowledgeMessage> checkEndGame(){
        boolean isNewTurn = gameModel.nextTurn();
        HashMap<String, AcknowledgeMessage> msg = null;
        //if game ended but last turn not started yet, check if last turn is starting now
        if(gameModel.checkTheEnd() && missingTurns ==-1) {
            missingTurns = 2;
            msg = messageBuilder.notifyLastTurn();
        }
        if(missingTurns >0 && isNewTurn){
            missingTurns--;
        }
        if(missingTurns ==0 && isNewTurn){
            //if last turn is started and ended
            gameModel.computePlayerSecretObjectives();
            List<Player> playerList = gameModel.computeCommonObjectives();
            messageBuilder.notifyPlayerObjectives(playerList);
            Player winner = gameModel.checkWinner();
            msg = messageBuilder.notifyWin(winner);
        }
        else{
            //otherwise simply notify next player to play
            msg = messageBuilder.notifyNextTurn(gameModel.getCurrPlayer());
        }
        return msg;
    }
}
