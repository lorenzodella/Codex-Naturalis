package it.polimi.ingsw.controller;

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
            startGame();
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
    }

    @Override
    public void chooseStarterCardSide(String playerNickname, int side) throws InvalidArgumentException {
        gameModel.chooseStarterCardSide(side, playerNickname);
        messageBuilder.notifyStarterCardSide(playerNickname);
        //check if someone has not played his starterCard yet
        for(Player p : gameModel.getPlayers()){
            if(p.getStarterCard().getOrder() < 0){
                return;
            }
        }
        List<Player> playersList = gameModel.initObjectiveCards();
        messageBuilder.notifyObjectiveCards(gameModel.getCommonObjectives(), playersList);
    }

    @Override
    public void chooseObjective(String playerNickname, int index) throws InvalidArgumentException {
        gameModel.chooseObjective(index, playerNickname);
        //check if someone has not chosen his objectiveCard yet
        for(Player p : gameModel.getPlayers()){
            if(p.getSecretObjective()[1] != null){
                return;
            }
        }
        Player first = gameModel.chooseFirstPlayer();
        messageBuilder.notifyGameStarted(first);
    }

    @Override
    public void playCard(int indexCard, int angle, String targetID, int side)
            throws InvalidArgumentException, TargetNotPresentException,
            InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException {
        Player p = gameModel.playCard(indexCard, angle, targetID, side);
        messageBuilder.notifyPlayerPlay(p);
        if(gameModel.areDeckFinished())
            checkEndGame();
    }

    @Override
    public void pickCard(int deck) throws InvalidArgumentException, FinishedCardStackException {
        Player p = gameModel.pickCard(deck);
        messageBuilder.notifyPlayerPick(p);
        messageBuilder.notifyDecks(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        checkEndGame();
    }

    @Override
    public void pickCard(int deck, int index) throws InvalidArgumentException, FinishedCardStackException {
        Player p = gameModel.pickCard(deck, index);
        messageBuilder.notifyPlayerPick(p);
        //non mi sembra che si possa modificare dato che gameModel.pickCard ritorna già il player
        //una possibile soluzione potrebbe essere quella di ritoranre il game e poi andare a prendere il singolo player
        //e per il metodo messageBuilder.notifyDecks() prendere i deck
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
