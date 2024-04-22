package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.InvalidPlayingException;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.GameObservable;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.*;

import java.util.ArrayList;
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
    public void newGame(String playerNickname, int numPlayers) throws InvalidArgumentException {
        if(numPlayers<2 || numPlayers>4)
            throw new InvalidArgumentException("numPlayers", numPlayers);
        players = new ArrayList<>();
        players.add(playerNickname);
        this.numPlayers = numPlayers;
        // return Message
    }

    @Override
    public void joinGame(String playerNickname) throws CannotJoinGameException {
        if(players==null)
            throw new CannotJoinGameException("no active game");
        if(players.size()==numPlayers)
            throw new CannotJoinGameException("game is full");
        if(players.contains(playerNickname))
            throw new CannotJoinGameException("nickname's already been used");
        players.add(playerNickname);
        if(players.size()==numPlayers)
            //Hashmap tmp = startGame(); (positivo)
        //if(tmp==null)
            //tmp = new HashMap .... crei IL messaggio per dire ok aspetto gli altri  (negativo)
        // return HashMap<StartGameMessage> tmp
    }

    private void startGame(){
        this.messageBuilder = new MessageBuilder(players);
        gameModel = new Game(players.stream().map(Player::new).collect(Collectors.toList()));
        Deck[] tmp = gameModel.initDecks();
        messageBuilder.notifyDecks(tmp[Deck.RESOURCE_CARDS], tmp[Deck.GOLD_CARDS]);
        List<Player> playerList = gameModel.giveStarterCards();
        messageBuilder.notifyStarterCards(playerList);
        List<Player> playerList2 =  gameModel.giveInitialCards();
        messageBuilder.notifyInitialCards(playerList2);
        //hanno joinato tutti ora si inizia a giocare (setto tutti gli attributi)
        // mando 4 messaggi!!!
        // return HashMap<StartGameMessage>
    }

    @Override
    public void chooseStarterCardSide(String playerNickname, int side) throws InvalidArgumentException {
        Player player = gameModel.chooseStarterCardSide(side, playerNickname);
        messageBuilder.notifyStarterCardSide(player); //setta playerinfo
        //check if someone has not played his starterCard yet
        for(Player p : gameModel.getPlayers()){
            if(p.getStarterCard().getOrder() < 0){
                return; //negativo
            }
        }
        List<Player> playersList = gameModel.initObjectiveCards();
        messageBuilder.notifyObjectiveCards(gameModel.getCommonObjectives(), playersList); //setta gli altri
        //positivo
    }

    @Override
    public void chooseObjective(String playerNickname, int index) throws InvalidArgumentException {
        Player player = gameModel.chooseObjective(index, playerNickname);
        messageBuilder.notifyChosenSecretObjective(player); //setti playerInfo
        //check if someone has not chosen his objectiveCard yet
        for(Player p : gameModel.getPlayers()){
            if(p.getSecretObjective()[1] != null){
                return; //negativo
            }
        }
        Player first = gameModel.chooseFirstPlayer();
        messageBuilder.notifyGameStarted(first);

        //positivo
    }

    @Override
    public void playCard(String playerNickname, int indexCard, int angle, String targetID, int side)
            throws InvalidArgumentException, TargetNotPresentException,
            InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException,
            InvalidPlayingException {
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");
        Player p = gameModel.playCard(indexCard, angle, targetID, side);
        messageBuilder.notifyPlayerPlay(p);
        if(gameModel.areDeckFinished())
            checkEndGame();
        else
            currPlayerMustDraw = true;
    }

    @Override
    public void pickCard(String playerNickname, int deck) throws InvalidArgumentException, FinishedCardStackException, InvalidPlayingException {
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");
        if(!currPlayerMustDraw)
            throw new InvalidPlayingException("You can't draw a card now");
        Player p = gameModel.pickCard(deck);
        messageBuilder.notifyPlayerPick(p);
        messageBuilder.notifyDecks(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        currPlayerMustDraw = false;
        checkEndGame();
    }

    @Override
    public void pickCard(String playerNickname, int deck, int index) throws InvalidArgumentException, FinishedCardStackException, InvalidPlayingException {
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");
        if(!currPlayerMustDraw)
            throw new InvalidPlayingException("You can't draw a card now");
        Player p = gameModel.pickCard(deck, index);
        messageBuilder.notifyPlayerPick(p);
        messageBuilder.notifyDecks(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        checkEndGame();
    }

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
            //if last turn is started and ended
            List<Player> player = gameModel.computePlayerSecretObjectives();
            messageBuilder.notifyPlayerSecretObjectives(player);
            List<Player> playerList = gameModel.computeCommonObjectives();
            messageBuilder.notifyCommonObjectives(playerList);
            Player winner = gameModel.checkWinner();
            messageBuilder.notifyWin(winner);
        }
        else{
            //otherwise simply notify next player to play
            messageBuilder.notifyNextTurn(gameModel.getCurrPlayer());
        }
    }
}
