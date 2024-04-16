package it.polimi.ingsw.controller;

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
    private GameObserver virtualView;
    private List<String> players;
    private int numPlayers;
    private int missingTurns = -1;

    public GameObservable getGameModel() {
        return gameModel;
    }

    public GameObserver getVirtualView() {
        return virtualView;
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
        this.virtualView = new VirtualView(players);
        gameModel = new Game(players.stream().map(Player::new).collect(Collectors.toList()));
        gameModel.initDecks();
        virtualView.notifyDecks(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        gameModel.giveStarterCards();
        virtualView.notifyStarterCards(gameModel.getPlayers());
        gameModel.giveInitialCards();
        virtualView.notifyInitialCards(gameModel.getPlayers());
    }

    @Override
    public void chooseStarterCardSide(String playerNickname, int side) throws InvalidArgumentException {
        gameModel.chooseStarterCardSide(side, playerNickname);
        //check if someone has not played his starterCard yet
        for(Player p : gameModel.getPlayers()){
            if(p.getStarterCard().getOrder() < 0){
                return;
            }
        }
        gameModel.initObjectiveCards();
        virtualView.notifyObjectiveCards(gameModel.getCommonObjectives(), gameModel.getPlayers());
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
        virtualView.notifyGameStarted(first);
    }

    @Override
    public void playCard(int indexCard, int angle, String targetID, int side)
            throws InvalidArgumentException, TargetNotPresentException,
            InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException {
        Player p = gameModel.playCard(indexCard, angle, targetID, side);
        virtualView.notifyPlayerPlay(p);
        if(gameModel.areDeckFinished())
            checkEndGame();
    }

    @Override
    public void pickCard(int deck) throws InvalidArgumentException, FinishedCardStackException {
        Player p = gameModel.pickCard(deck);
        virtualView.notifyPlayerPick(p);
        virtualView.notifyDecks(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        checkEndGame();
    }

    @Override
    public void pickCard(int deck, int index) throws InvalidArgumentException, FinishedCardStackException {
        Player p = gameModel.pickCard(deck, index);
        virtualView.notifyPlayerPick(p);
        virtualView.notifyDecks(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        checkEndGame();
    }

    private void checkEndGame(){
        boolean isNewTurn = gameModel.nextTurn();
        //if game ended but last turn not started yet, check if last turn is starting now
        if(gameModel.checkTheEnd() && missingTurns ==-1) {
            missingTurns = 2;
            virtualView.notifyLastTurn();
        }
        if(missingTurns >0 && isNewTurn){
            missingTurns--;
        }
        if(missingTurns ==0 && isNewTurn){
            //if last turn is started and ended
            gameModel.computePlayerSecretObjectives();
            virtualView.notifyPlayerSecretObjectives(gameModel.getPlayers());
            gameModel.computeCommonObjectives();
            virtualView.notifyCommonObjectives(gameModel.getPlayers());
            Player winner = gameModel.checkWinner();
            virtualView.notifyWin(winner);
        }
        else{
            //otherwise simply notify next player to play
            virtualView.notifyNextTurn(gameModel.getCurrPlayer());
        }
    }
}
