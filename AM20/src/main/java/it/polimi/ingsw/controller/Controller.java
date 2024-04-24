package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.InvalidPlayingException;
import it.polimi.ingsw.controller.messages.ConnectionAckMessage;
import it.polimi.ingsw.controller.messages.Message;
import it.polimi.ingsw.controller.messages.StartGameMessage;
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
import java.util.stream.Collectors;

public class Controller implements GameManager {
    private GameObservable gameModel;
    private GameObserver messageBuilder;
    private List<String> players;
    private int numPlayers;
    private int missingTurns = -1;
    private boolean currPlayerMustDraw = false;

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
    public Message newGame(String playerNickname, int numPlayers) throws InvalidArgumentException {
        if(numPlayers<2 || numPlayers>4)
            throw new InvalidArgumentException("numPlayers", numPlayers);
        players = new ArrayList<>();
        players.add(playerNickname);
        this.numPlayers = numPlayers;
        Message tmp = new Message();
        tmp.setResult("You created a new game");
        return tmp;
    }

    @Override
    public HashMap<String, ConnectionAckMessage> joinGame(String playerNickname) throws CannotJoinGameException {
        if(players==null)
            throw new CannotJoinGameException("no active game");
        if(players.size()==numPlayers)
            throw new CannotJoinGameException("game is full");
        if(players.contains(playerNickname))
            throw new CannotJoinGameException("nickname's already been used");
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
                tmp.get(nickname).setGameStarts(false);
            }
        }
        return tmp;
    }

    private HashMap<String, ConnectionAckMessage> startGame(){
        this.messageBuilder = new MessageBuilder(players);
        gameModel = new Game(players.stream().map(Player::new).collect(Collectors.toList()));
        Deck[] tmp = gameModel.initDecks();
        messageBuilder.notifyDecksCreated(tmp[Deck.RESOURCE_CARDS], tmp[Deck.GOLD_CARDS]);
        List<Player> playerList = gameModel.giveStarterCards();
        messageBuilder.notifyStarterCards(playerList);
        List<Player> playerList2 =  gameModel.giveInitialCards();
        HashMap<String, ConnectionAckMessage> msg = messageBuilder.notifyInitialCards(playerList2);
        for(ConnectionAckMessage message: msg.values()){
            message.setGameStarts(true);
        }
        return msg;
    }

    //TODO
    @Override
    public void chooseStarterCardSide(String playerNickname, int side) throws InvalidArgumentException {
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
    }
    //TODO
    @Override
    public void chooseObjective(String playerNickname, int index) throws InvalidArgumentException {
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
    }
    //TODO
    @Override
    public void playCard(String playerNickname, int indexCard, int angle, String targetID, int side)
            throws InvalidArgumentException, TargetNotPresentException,
            InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException,
            InvalidPlayingException {
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");
        Player p = gameModel.playCard(indexCard, angle, targetID, side);
        messageBuilder.notifyPlayerPlay(p); // setta playerinfo
        if(gameModel.areDeckFinished())
            checkEndGame();
            // negativo
        else
            currPlayerMustDraw = true;
            // settare a true mustpick
            // positivo (setto AcknowledgeMessage)
    }
    //TODO
    @Override
    public void pickCard(String playerNickname, int deck) throws InvalidArgumentException, FinishedCardStackException, InvalidPlayingException {
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");
        if(!currPlayerMustDraw)
            throw new InvalidPlayingException("You can't draw a card now");
        Player p = gameModel.pickCard(deck);
        messageBuilder.notifyPlayerPick(p);
        messageBuilder.notifyDecksModified(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        currPlayerMustDraw = false;
        checkEndGame();
        //
    }
    //TODO
    @Override
    public void pickCard(String playerNickname, int deck, int index) throws InvalidArgumentException, FinishedCardStackException, InvalidPlayingException {
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");
        if(!currPlayerMustDraw)
            throw new InvalidPlayingException("You can't draw a card now");
        Player p = gameModel.pickCard(deck, index);
        messageBuilder.notifyPlayerPick(p);
        messageBuilder.notifyDecksModified(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        currPlayerMustDraw = false;
        checkEndGame();
        //
    }
    //TODO
    private void checkEndGame(){
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
    }
}
