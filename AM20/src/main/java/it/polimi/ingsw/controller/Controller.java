package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.InvalidDisconnectionException;
import it.polimi.ingsw.model.exceptions.InvalidPlayingException;
import it.polimi.ingsw.controller.messages.*;
import it.polimi.ingsw.controller.exceptions.NoOneIsConnectedException;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.GameObservable;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import it.polimi.ingsw.controller.exceptions.CannotJoinGameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.*;

import java.util.*;
import java.util.stream.Collectors;

public class Controller implements GameManager {
    /**
     * these following attributes stay for the game's phase:
     * NOGAME: stays for the first game's phase, where there's no active game and the controller is just waiting for
     *         some player to activate a new game.
     * PRELIMINARY: stays for the phase where the controller waits for all players to join the game (the number of
     *              players that have to join is numplayer which is decided in the NOGAME phase) and the finally
     *              create the new game session
     * STARTER: stays for the game's phase where all players choose for their starter card's side
     * OBJECTIVES: stays for the game's phase where all players choose for their secret objective
     * PLAY: stays for the game's phase where a player plays a card down to the player table
     * PICK: stays for the game's phase where, the same player that has just played a card, now draws a card from
     *       the deck or draws one of the two visible cards (gold or resource)
     */
    private static final int NOGAME = -1;
    private static final int PRELIMINARY = 0;
    private static final int STARTER = 1;
    private static final int OBJECTIVES = 2;
    private static final int PLAY = 3;
    private static final int PICK = 4;
    private static final int END = 5;
    private int phase;

    private GameObservable gameModel;
    private GameObserver messageBuilder;

    /**
     * This attribute stays for the list of all players that are playing the game at the moment
     */
    private List<String> players;
    /**
     * This attribute stays for the number of player that are playing the game at the moment
     */
    private int numPlayers;
    /**
     * This attribute says if the players are playing the final turn (where there's no cards left in the decks or
     * if someone has reached 20 points) and especially it says how many rounds are left until the end of the game
     * ..................................
     */
    private int missingRounds = -1;

    public Controller(){
        players = new ArrayList<>();
        phase = NOGAME;
    }

    protected GameObservable getGameModel() {
        return gameModel;
    }

    protected GameObserver getMessageBuilder() {
        return messageBuilder;
    }

    protected List<String> getPlayers() {
        return players;
    }

    protected int getNumPlayers() {
        return numPlayers;
    }

    public Set<String> getConnectedPlayers(){
        if(gameModel!=null)
            return gameModel.getConnectedPlayers();
        else
            return new HashSet<>(players);
    }

    /**
     * This method is one of the NOGAME pahse methods and it allows to have a list that contains all the players'
     * nicknames.
     * @param playerNickname : the first player nickname
     * @param numPlayers : the number of player that the first player wants to play with
     * @return a message of success --> you created a new game
     * @throws InvalidArgumentException if the numplayers is incorrect (given by the rules)
     * @throws InvalidPlayingException if the phase is not the NOGAME phase
     */
    @Override
    public synchronized ConnectionAckMessage newGame(String playerNickname, int numPlayers) throws InvalidArgumentException, InvalidPlayingException {
        if(phase!=NOGAME)
            throw new InvalidPlayingException("A game already started");
        if(numPlayers<2 || numPlayers>4)
            throw new InvalidArgumentException("numPlayers", numPlayers);
        gameModel = null;
        players = new ArrayList<>();
        players.add(playerNickname);
        this.numPlayers = numPlayers;
        ConnectionAckMessage tmp = new ConnectionAckMessage();
        tmp.setResult("You created a new game and waiting for all player to connect");
        tmp.setNickname(playerNickname);
        phase = PRELIMINARY;
        return tmp;
    }

    /**
     * This method allows to disconnect a player from the game (for example because of network failure).
     * Return messages containing the number of remaining players: if there's only one, caller should start a timer in order to end game.
     * @param nickname player who disconnected
     * @return messages to be sent to connected players
     * @throws InvalidConnectionStateException if player is already disconnected
     * @throws InvalidArgumentException if player is not part of current game
     * @throws NoOneIsConnectedException game should stop if player disconnected during first phase of the game or if all players disconnected
     */
    @Override
    public synchronized HashMap<String, AcknowledgeMessage> disconnectPlayer(String nickname)
            throws InvalidConnectionStateException, InvalidArgumentException, NoOneIsConnectedException, InvalidDisconnectionException {
        //if you are not a player of current game
        if(!players.contains(nickname))
            throw new InvalidArgumentException("nickname", nickname);
        //if actual game not yet started
        //SE UN PLAYER SI DISCONNETTE DURING PRELIMINARY --> CHIUDO TUTTO
        if(phase<PLAY) {
            phase = NOGAME;
            throw new InvalidDisconnectionException();
        }

        gameModel.setPlayerConnection(nickname, false);
        Set<String> connectedPlayers = gameModel.getConnectedPlayers();
        if(connectedPlayers.isEmpty()) {
            phase = NOGAME;
            throw new NoOneIsConnectedException();
        }

        messageBuilder = new MessageBuilder(connectedPlayers);
        HashMap<String, AcknowledgeMessage> msg = messageBuilder.notifyPlayerDisconnected(nickname);

        //if you are the current player, pass turn to next player
        if(gameModel.getCurrPlayer().getNickname().equals(nickname)) {
            if(phase==PICK)
                phase=PLAY;
            msg = checkEndGame();
        }
        return msg;
    }

    /**
     * This method allows a player to reconnect to the game (if he was a disconnected player of current game)
     * @param nickname player who reconnected
     * @return messages to be sent to connected players
     * @throws CannotJoinGameException if player was already online, or it's not part of current game
     */
    private synchronized HashMap<String, ConnectionAckMessage> reconnectPlayer(String nickname) throws CannotJoinGameException {
        try {
            List<Player> p = gameModel.setPlayerConnection(nickname, true);
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
    public synchronized HashMap<String, ConnectionAckMessage> joinGame(String playerNickname) throws CannotJoinGameException {
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
            }
            tmp.get(playerNickname).setResult("You joined the game");
            tmp.get(playerNickname).setNickname(playerNickname);
        }
        return tmp;
    }

    /**
     * This method allows the players to actually start a game and it's a PRELIMINARY phase method.
     * This method is called directly by the joinGame method and, given the list of the connected players, it allows to
     * initialize the resource and gold decks, it gives the starter cards and the 3 intial cards to all players.
     * @return a StartGameMessage that says that all players have just joined and it also gives all the cards and decks
     * that every player now needs to be able to actually start playing
     */
    private synchronized HashMap<String, ConnectionAckMessage> startGame(){
        phase = STARTER;
        gameModel = new Game(players.stream().map(Player::new).collect(Collectors.toList()));

        //CREO NUOVO MESSAGE BUILDER PASSANDOGLI LA NUOVA LISTA DEI CONNECTED PLAYERS
        this.messageBuilder = new MessageBuilder(gameModel.getConnectedPlayers());

        gameModel.initDecks();
        List<Player> playerList = gameModel.giveStarterCards();
        messageBuilder.notifyStarterCards(playerList);
        List<Player> playerList2 =  gameModel.giveInitialCards();
        messageBuilder.notifyInitialCards(playerList2);
        messageBuilder.notifyDecksCreated(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());

        //QUESTO RITORNA DEGLI STARTGAMEMESSAGE
        HashMap<String, ConnectionAckMessage> msg = messageBuilder.notifyDefaultPlayerInfo(playerList2);
        for(ConnectionAckMessage message: msg.values()){
            message.setResult("All players joined");
        }
        return msg;
    }

    /**
     * This method allows, to every connected player, to actually choose if they want to play the starter card
     * by the front or the back and it's a STARTER phase method.
     * It returns a StarterCardAckMessage or it specifically returns a startChoosingObjectiveMessage depending on
     * if all players've already chosen the starter card side or if someone hasn't done it yet.
     * @param playerNickname the player that is choosing the side of the card
     * @param side the side that the player's just chosen (could be FRONT or BACK)
     * @return
     * if all players've chosen the side of their starter card, the method returns a StartChoosingObjectiveMessage which
     * gives the player infos (based on how they put down their starter card) and it also gives the common and secret
     * objectives so that the OBJECTIVES phase can start.
     * Otherwise, the method returns a StarterCardAckMessage in order to signalize that the player's chosen the side
     * correctly and it gives them the player infos of that playing.
     * @throws InvalidArgumentException
     * @throws InvalidPlayingException if someone tries to put the starter card in an unappropriated position
     */
    @Override
    public synchronized HashMap<String, StarterCardAckMessage> chooseStarterCardSide(String playerNickname, int side) throws InvalidArgumentException, InvalidPlayingException {
        if(phase!=STARTER)
            throw new InvalidPlayingException("You can't position starter card now");

        //RIINIZIALIZZO LISTA DEI CONNECTED PLAYERS E MANDO AL MESSAGEBUILDER
        this.messageBuilder = new MessageBuilder(gameModel.getConnectedPlayers());

        Player player = gameModel.chooseStarterCardSide(side, playerNickname);

        for(Player p : gameModel.getPlayers()){
            //check if someone has not played their starterCard yet
            if(p.getStarterCard().getOrder() < 0){
                //negativo
                HashMap<String, StarterCardAckMessage> msg = messageBuilder.notifyStarterCardSide(player); //setta playerinfo
                return msg;
            }
        }
        //positivo
        List<Player> playersList = gameModel.initObjectiveCards();
        //prima notifico che tutti hanno scelto la starter card -> ora si scelgono obiettivi
        messageBuilder.notifyObjectiveCards(gameModel.getCommonObjectives(), playersList); //setta gli altri
        //poi aggiungo le informazioni dell'ultimo che ha scelto
        HashMap<String, StarterCardAckMessage> msg1 = messageBuilder.notifyStarterCardSide(player); //setta playerinfo
        for(StarterCardAckMessage message: msg1.values()){
            message.setResult("Everyone's chosen the side of the starter card. Now you can choose the secret objective");
        }
        phase = OBJECTIVES;
        return msg1;
    }

    /**
     * This method is called in the OBJECTIVE phase and it allows, to the connected players, to choose between the two
     * secret objectives.
     * It returns anObjectiveAckMessage or it specifically returns a StartPlayingMessage depending on
     * if all players've already chosen their objective or if someone hasn't done it yet.
     * @param playerNickname the player that is choosing the secret objective
     * @param index the index (could only be 0 or 1) of the secret objective that they've chosen (in the array of two items).
     * @return
     * if all players've chosen their objective the method returns a StarPlayingMessage which
     * gives the just chosen secret objective, the nickname of the first player that needs to start playing and
     * so that the PLAY phase can start.
     * Otherwise, the method returns an ObjectiveAckMessage in order to signalize that the player's chosen the objective
     * correctly and it gives them the just chosen secret objective.
     * @throws InvalidArgumentException if the par are not valid
     * @throws InvalidPlayingException if the phase is not the OBJECTIVE
     */
    @Override
    public synchronized HashMap<String, ObjectiveAckMessage> chooseObjective(String playerNickname, int index) throws InvalidArgumentException, InvalidPlayingException {
        if(phase!=OBJECTIVES)
            throw new InvalidPlayingException("You can't choose objective now");

        //RIINIZIALIZZO LISTA DEI CONNECTED PLAYERS E MANDO AL MESSAGEBUILDER
        this.messageBuilder = new MessageBuilder(gameModel.getConnectedPlayers());

        Player player = gameModel.chooseObjective(index, playerNickname);

        for(Player p : gameModel.getPlayers()){
            //check if someone has not chosen his objectiveCard yet
            //negativo
            if(p.getSecretObjective().size()>1){
                HashMap<String, ObjectiveAckMessage> msg = messageBuilder.notifyChosenSecretObjective(player); //setti secretobjectives[]
                return msg;
            }
        }
        Player first = gameModel.chooseFirstPlayer();
        //positivo
        messageBuilder.notifyGameStarted(first);
        HashMap<String, ObjectiveAckMessage> msg1 = messageBuilder.notifyChosenSecretObjective(player); //setti secretobjectives[]
        for(ObjectiveAckMessage message: msg1.values()){
            message.setResult("The setup phase's finished and now the game can start");
        }
        phase = PLAY;
        return msg1;
    }

    /**
     * This is PLAY phase method and it allows the player to actually play a card down to their table player.
     * Basically the player says
     * 1. which of their cards wants to play,
     * 2. the side they want it to be played by,
     * 3. the card that they want to cover by playing their card
     * 4. the angle of the card that they want to cover
     * @param playerNickname the nickname of the player that's playing
     * @param indexCard the card that they want to play
     * @param angle the angle of the card that they want to cover
     * @param targetID the card that they want to cover
     * @param side the side they want to play the card by
     * @return
     * it could return
     * 1. PlayAckMessage("Pick a card", yourPlayerInfo, mustPick: "True") if there are still cards to be drawn
     * 2. PlayAckMessage("Your turn is over", YourPlayerInfo, NextPlayer, mustPick: "False") if there aren't any more cards to be drawn
     * @throws InvalidArgumentException if the par are invalid
     * @throws TargetNotPresentException
     * @throws InvalidAngleCoveredException
     * @throws InvalidPositionException
     * @throws RequirementsNotRespectedException 
     * @throws InvalidPlayingException if the phase is not PLAY or if it's not the player's turn or if the player is now
     * by himself and needs to wait for the others to reconnect
     * @throws NoOneIsConnectedException
     */
    @Override
    public synchronized HashMap<String, AcknowledgeMessage> playCard(String playerNickname, int indexCard, int angle, String targetID, int side)
            throws InvalidArgumentException, TargetNotPresentException,
            InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException,
            InvalidPlayingException, NoOneIsConnectedException {
        if(phase!=PLAY)
            throw new InvalidPlayingException("You can't play a card now");

        Set<String> connectedPlayers = gameModel.getConnectedPlayers();
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");
        if(connectedPlayers.size()<=1)
            throw new InvalidPlayingException("You are the only player, wait for the others to reconnect");
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

    /**
     * This is a PICK phase method and it allows the player to pick a card from the specified deck.
     * @param playerNickname the player that needs to pick a card from the deck now
     * @param deck the deck that the player needs to pick a card from
     * @return it returns a PickAckMessage that specified that their turn is over, it gives them their new player infos
     * and says who the next player is.
     * @throws InvalidArgumentException
     * @throws FinishedCardStackException
     * @throws InvalidPlayingException if the phase is not PLAY or if it's not the player's turn or if the player is now
     * by himself and needs to wait for the others to reconnect
     * @throws NoOneIsConnectedException
     */
    @Override
    public synchronized HashMap<String, AcknowledgeMessage> pickCard(String playerNickname, int deck) throws InvalidArgumentException, FinishedCardStackException,
            InvalidPlayingException, NoOneIsConnectedException {
        if(phase!=PICK)
            throw new InvalidPlayingException("You can't draw a card now");
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");
        if(gameModel.getConnectedPlayers().size()<=1)
            throw new InvalidPlayingException("You are the only player, wait for the others to reconnect");

        //RIINIZIALIZZO LISTA DEI CONNECTED PLAYERS E MANDO AL MESSAGEBUILDER
        this.messageBuilder = new MessageBuilder(gameModel.getConnectedPlayers());

        Player p = gameModel.pickCard(deck);
        messageBuilder.notifyPlayerPick(p);
        messageBuilder.notifyDecksModified(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        phase = PLAY;
        HashMap<String, AcknowledgeMessage> msg = checkEndGame();
        return msg;
    }

    /**
     *  This is a PICK phase method and it allows the player to pick one of the two visible cards (could be from the
     *  resource or the gold visible cards).
     * @param playerNickname the nickname of the player that needs to pick a card
     * @param deck the deck of the visible cards (could be gold or resource)
     * @param index teh index of the array that stands for the two visible cards (could only be 0 or 1)
     * @return it returns a PickAckMessage that specified that their turn is over, it gives them their new player infos
     *         and says who the next player is.
     * @throws InvalidArgumentException
     * @throws FinishedCardStackException
     * @throws InvalidPlayingException
     * @throws NoOneIsConnectedException
     */
    @Override
    public synchronized HashMap<String, AcknowledgeMessage> pickCard(String playerNickname, int deck, int index) throws InvalidArgumentException, FinishedCardStackException,
            InvalidPlayingException, NoOneIsConnectedException {
        if(phase!=PICK)
            throw new InvalidPlayingException("You can't draw a card now");
        if(!gameModel.getCurrPlayer().getNickname().equals(playerNickname))
            throw new InvalidPlayingException("It's not your turn");
        if(gameModel.getConnectedPlayers().size()<=1)
            throw new InvalidPlayingException("You are the only player, wait for the others to reconnect");

        //RIINIZIALIZZO LISTA DEI CONNECTED PLAYERS E MANDO AL MESSAGEBUILDER
        this.messageBuilder = new MessageBuilder(gameModel.getConnectedPlayers());

        Player p = gameModel.pickCard(deck, index);
        messageBuilder.notifyPlayerPick(p);
        messageBuilder.notifyDecksModified(gameModel.getResourceCardDeck(), gameModel.getGoldCardDeck());
        phase = PLAY;
        HashMap<String, AcknowledgeMessage> msg = checkEndGame();
        return msg;
    }

    /**
     * This method could be called either in the PLAY or in the PICK phase depending on the situation.
     * This method:
     * 1. checks if the player has reached 20 points after every playing of a card
     * 2. checks if there's no cards to be picked up
     * 3. allows the final turn to begin
     * @return .......
     * @throws NoOneIsConnectedException if nobody is connected at the moment
     */
    private synchronized HashMap<String, AcknowledgeMessage> checkEndGame() throws NoOneIsConnectedException {
        HashMap<String, AcknowledgeMessage> msg = null;
        //if game ended but last turn not started yet
        if(gameModel.checkTheEnd() && missingRounds ==-1) {
            missingRounds = 2;
            msg = messageBuilder.notifyLastTurn();
        }

        boolean isNewTurn = false;
        try {
            isNewTurn = gameModel.nextTurn();
        } catch (InvalidPlayingException e) {
            phase = NOGAME;
            throw new NoOneIsConnectedException();
        }
        //check if last turn is starting now
        if(missingRounds >0 && isNewTurn){
            missingRounds--;
        }
        if(missingRounds ==0 && isNewTurn){
            //if last turn is started and ended
            gameModel.computePlayerSecretObjectives();
            List<Player> playerList = gameModel.computeCommonObjectives();
            msg = messageBuilder.notifyPlayerObjectives(playerList);
            try {
                Player winner = gameModel.checkWinner();
                msg = messageBuilder.notifyWin(winner);
            } catch (DrawMatchException e) {
                for(AcknowledgeMessage m : msg.values()){
                    m.setResult(e.toString());
                }
            }
            phase = END;
        }
        else{
            //otherwise simply notify next player to play
            msg = messageBuilder.notifyNextTurn(gameModel.getCurrPlayer());
        }
        return msg;
    }
}
