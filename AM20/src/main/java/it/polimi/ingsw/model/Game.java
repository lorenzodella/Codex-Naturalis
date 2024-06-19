package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidPlayingException;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import it.polimi.ingsw.model.util.XMLparser;

import java.util.*;
import java.util.stream.Collectors;

public class Game implements GameObservable{
    /**
     * Dinamic arrayList of players:
     * Before starting the game, the first player choses the number of players they want to play with.
     * The number of players needs to be between 2 and 4.
     */
    private List<Player>  players;
    /**
     *  deck of resource cards
     */
    private Deck resourceCardDeck;
    /**
     *  deck of gold cards
     */
    private Deck goldCardDeck;
    /**
     * Array of 2 items: commonObjectives contains two objective cards that
     * are going to be the common objectives for all players
     */
    private ObjectiveCard[] commonObjectives;
    /**
     * This attribute stands for the player that's currently playing
     */
    private Player currPlayer;

    public Game(List<Player> players){
        this.players = players;
        currPlayer = players.get(0);
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }
    @Override
    public Set<String> getConnectedPlayers() {
        return players.stream().filter(Player::isOnline).map(Player::getNickname).collect(Collectors.toSet());
    }
    @Override
    public Deck getGoldCardDeck() {
        return goldCardDeck;
    }
    @Override
    public Deck getResourceCardDeck() {
        return resourceCardDeck;
    }
    @Override
    public Player getCurrPlayer() {
        return currPlayer;
    }
    @Override
    public ObjectiveCard[] getCommonObjectives() {
        return commonObjectives;
    }

    /**
     * This method allows to connect or disconnect a player
     * @param nickname name of the player who is connecting/disconnecting
     * @param isOnline tells if the player is online or not
     * @return a list of players, first one is the one who changed connection state
     * @throws InvalidArgumentException if there's no player with that nickname
     * @throws InvalidConnectionStateException if player's connection state was the same
     */
    @Override
    public List<Player> setPlayerConnection(String nickname, boolean isOnline) throws InvalidArgumentException, InvalidConnectionStateException {
        Player p = players.stream().filter(x -> x.getNickname().equals(nickname)).findFirst()
                .orElseThrow(()-> new InvalidArgumentException("nickname", nickname));
        if (p.isOnline() == isOnline)
            throw new InvalidConnectionStateException(isOnline);
        p.setOnline(isOnline);
        List<Player> returnList = players.stream().filter(x -> !x.equals(p)).collect(Collectors.toList());
        returnList.add(0, p);
        return returnList;
    }

    /**
     * This method
     * 1. creates and suffles both gold and resource deck card
     * 2. displays two visible cards on the table (per each deck)
     */
    @Override
    public Deck[] initDecks(){
        resourceCardDeck = new Deck(XMLparser.parseResourceCards("src/main/resources/xml/resourceCards.xml"));
        resourceCardDeck.shuffle();
        resourceCardDeck.initVisibleCards();

        goldCardDeck = new Deck(XMLparser.parseGoldCards("src/main/resources/xml/goldCards.xml"));
        goldCardDeck.shuffle();
        goldCardDeck.initVisibleCards();

        Deck[] decks = new Deck[2];
        decks[Deck.GOLD_CARDS] = goldCardDeck;
        decks[Deck.RESOURCE_CARDS] = resourceCardDeck;
        return decks;

    }

    /**
     * This method
     * 1. saves the initial cards into an arraylist (by reading them from the XML file)
     * 2. it shuffles the cards
     * 3. gives an initial card to every player
     */
    @Override
    public List<Player> giveStarterCards(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("src/main/resources/xml/starterCards.xml");
        Collections.shuffle(starterCards);
        //this.giveInitialCards(starterCards);
        //metodo del controller che dice al gicoatore scegli quale deelle due starterCard HP: metodo chiamto PlayableCard selStarterCard(PlayableCard c1, PlayableCard c2, Player p)
        for(int i=0; i<players.size();i++){
            players.get(i).setStarterCard((StarterCard)starterCards.get(i));
        }
        return players;
    }

    /**
     * This method gives the initial cards to every player:
     * it creates a linkedList per each player that contains 3 elements
     * (a gold card and two resource cards)
     */
    @Override
    public List<Player> giveInitialCards() {
        for(Player p: players){
            LinkedList<PlayableCard> carte = new LinkedList<>();

            try {
                carte.add(resourceCardDeck.draw());
                carte.add(resourceCardDeck.draw());
                carte.add(goldCardDeck.draw());

                p.drawInitialPlayableCard(carte);
            } catch (FinishedCardStackException e) {
                throw new RuntimeException(e);
            }
        }

        return  players;
    }

    /**
     * This method allows the player p to choose the side (front/back) of the initial card
     * that it's been given to them
     * @param side it stands for the side of the card: front or back
     * @param nickname it stands for the player that's taking the action
     */
    @Override
    public Player chooseStarterCardSide(int side, String nickname) throws InvalidArgumentException, InvalidPlayingException {
        if(side != PlayableCard.FRONT && side != PlayableCard.BACK)
            throw new InvalidArgumentException("side", side);
        players.stream().filter(x -> x.getNickname().equals(nickname)).findFirst()
                .orElseThrow(()-> new InvalidArgumentException("nickname", nickname))
                .positionStarterCard(side);

        return players.stream().filter(x -> x.getNickname().equals(nickname)).findFirst()
                .orElseThrow(()-> new InvalidArgumentException("nickname", nickname));
    }

    /**
     * This method:
     * 1.  creates an arrayList where it saves all the objective cards (by reading them from the XML file)
     * 2.  shuffles the cards
     * 3.  creates an array of two elements, saving the two common objectives
     * 4.  creates an array of two elements that contains two possible secret objectives and it allows the player to choose
     *     their own secret objective between this two elements (by calling the chooseSecretObjective method)
     */
    @Override
    public List<Player> initObjectiveCards(){
        ArrayList<ObjectiveCard> tmp = new ArrayList<>(XMLparser.parseObjectiveCards("src/main/resources/xml/objectiveCards.xml"));
        Collections.shuffle(tmp);
        commonObjectives = new ObjectiveCard[2];
        commonObjectives[0] = tmp.get(0);
        commonObjectives[1] = tmp.get(1);

        for(int i=1; i<=players.size();i++){
            ArrayList<ObjectiveCard> obj = new ArrayList<>();
            obj.add(tmp.get(2*i));
            obj.add(tmp.get(2*i+1));
            players.get(i-1).setSecretObjective(obj);
        }

        return players;
    }

    /**
     * This method allows the player p to select the secret objetive they've chosen
     * @param index the player p selects the objective by choosing the index (0 or 1) of the array that contains the two possible
     *              secret objectives
     * @param nickname the nickname is the nickname of the player that's taking the action
     */
    @Override
    public Player chooseObjective(int index, String nickname) throws InvalidArgumentException, InvalidPlayingException {
        if(index<0 || index>1)
            throw new InvalidArgumentException("index", index);
        players.stream().filter(x -> x.getNickname().equals(nickname)).findFirst()
                .orElseThrow(()-> new InvalidArgumentException("nickname", nickname))
                .chooseObjectiveCard(index);

        return players.stream().filter(x -> x.getNickname().equals(nickname)).findFirst()
                .orElseThrow(()-> new InvalidArgumentException("nickname", nickname));
    }

    /**
     * This method shuffles the arrayList of player and sets as first player the one that
     * happens to be in the 0 position
     * @return the current player
     */
    @Override
    public Player chooseFirstPlayer(){
        Collections.shuffle(players);
        currPlayer = players.get(0);
        return currPlayer;
    }

    /**
     * This method:
     * 1. plays the card that's found at the index position of the cards list (Player)
     * 2. plays that specific card by the front or the back (depending on the side parameter --> ...)
     * 3. plays that specific card covering the angle (parameter) of the card that's specified by the targetID
     * @param indexCard : index of the card that you want to play that's found into the cards list (list of the
     *                  playable cards of that specific player)
     * @param angle : this attribute stands for the angle that you want to cover by positioning the card you're playing
     * @param targetID : this attribute stands for the card ID of the card that you want to cover by playing the card
     * @param side : this attribute specifies if the player want to play the card by the front or the back
     * @return the current player that's just played the card
     * @throws TargetNotPresentException if the target is not present
     * @throws InvalidAngleCoveredException if positioning the angle in that spot is incorrect
     * @throws InvalidPositionException if positioning the card in that spot is incorrect
     */
    @Override
    public Player playCard(int indexCard, int angle, String targetID, int side) throws InvalidArgumentException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException {
        if(side != PlayableCard.FRONT && side != PlayableCard.BACK)
            throw new InvalidArgumentException("side", side);
        try {
            currPlayer.playCard(indexCard, angle, targetID, side);
        } catch(IndexOutOfBoundsException e){
            throw new InvalidArgumentException("indexCard", indexCard);
        }
        return currPlayer;
    }

    /**
     * This method picks the card from the top of the specified deck (deck).
     * Once this method retrieved the correct new card, it adds that to the current player's card list.
     * @param deck : this attribute stands for the specific deck that you want to pick a card from
     * @return the current player that's just picked the card
     * @throws FinishedCardStackException if the deck's done
     */
    @Override
    public Player pickCard(int deck) throws FinishedCardStackException, InvalidArgumentException {
        Deck chosenDeck;
        PlayableCard card;
        if(deck == Deck.RESOURCE_CARDS )
            chosenDeck = resourceCardDeck;
        else if(deck == Deck.GOLD_CARDS)
            chosenDeck = goldCardDeck;
        else
            throw new InvalidArgumentException("deck", deck);

        card = chosenDeck.draw();

        this.currPlayer.drawCard(card);

        return currPlayer;
    }

    /**
     * This method picks up one of the two visible cards (shown on the table).
     * Specifically:
     * 1. it picks the visible card of the specific deck (deck) -->  which could be the gold or resource deck
     * 2. it picks the visible card (of that specific deck) that's found at the index position of the arraylist
     * of the visible cards --> array of only two elements so the index could only be 0 or 1
     * @param deck : the deck where the player wants to pick up the card from (which could be gold or resource)
     * @param visibleCardIndex : the index position of the card that's been chosen by the player
     * @return : the player that's just picked the card
     * @throws FinishedCardStackException
     * @throws InvalidArgumentException
     */
    @Override
    public Player pickCard(int deck, int visibleCardIndex ) throws FinishedCardStackException, InvalidArgumentException {
        // choiceDeck = 1 resourceCard  choiceDeck = 0 goldCard
        // visibile = true carta visibile all'indice index
        if(visibleCardIndex<0 || visibleCardIndex>1)
            throw new InvalidArgumentException("index", visibleCardIndex);

        Deck chosenDeck;
        PlayableCard card;
        if(deck == Deck.RESOURCE_CARDS )
            chosenDeck = resourceCardDeck;
        else if(deck == Deck.GOLD_CARDS)
            chosenDeck = goldCardDeck;
        else
            throw new InvalidArgumentException("deck", deck);

        card = chosenDeck.drawVisibleCard(visibleCardIndex);

        this.currPlayer.drawCard(card);

        return currPlayer;

    }

    /**
     * Sets current player as the next player, following playing order and checking if he's online.
     * @return true if next player is the first player, so a new turn started
     */
    @Override
    public boolean nextTurn() throws InvalidPlayingException{
        if(getConnectedPlayers().isEmpty())
            throw new InvalidPlayingException("No one is connected");
        boolean isNewTurn = false;
        do {
            int cur = players.indexOf(currPlayer);
            if (cur == players.size() - 1) {
                isNewTurn = true;
                currPlayer = players.get(0);
            }
            else
                currPlayer = players.get(cur + 1);
        } while (!currPlayer.isOnline());
        return isNewTurn;
    }

    /**
     * This method checks, every time that a player ends their turn, if they've reached the 20 points
     * (in that case the first phase of the game ends and the second phase starts) and it also checks if one of the deck
     * is empty (if the cards of that specific deck have been all played).
     * @return 1 if the player has reached 20 points (or more) or if the deck is empty, 0 otherwise
     */
    @Override
    public boolean checkTheEnd() {
        return currPlayer.getScore() >= 20 || areDeckFinished();
    }

    /**
     * Checks if both decks are empty, meaning that game must finish during the next turn
     * @return true if both decks are empty
     */
    @Override
    public boolean areDeckFinished() {
        return  resourceCardDeck.getVisibleCard(0) == null &&
                resourceCardDeck.getVisibleCard(1) == null &&
                goldCardDeck.getVisibleCard(0) == null &&
                goldCardDeck.getVisibleCard(1) == null;
    }

    /**
     * This method, at the end of the game, computes the points of the secret objective of every player
     */
    @Override
    public List<Player> computePlayerSecretObjectives(){
        for(Player p: players){
            p.computeSecretObjective();
        }

        return  players;
    }

    /**
     * This method adds, per each player, the points of the common objective
     */
    @Override
    public List<Player> computeCommonObjectives(){
        for(Player p: players){
            for(ObjectiveCard obj : commonObjectives){
                p.computeCommonObjective(obj);
            }
        }

        return players;
    }

    /**
     * This method returns to the controller the player that won the game
     * @return the player that won
     */
    //da decidere come gestire il caso di parità
    @Override
    public Player checkWinner() throws DrawMatchException{
        Player winner = players.get(0);
        for(Player p :players){
            int point = p.getScore();
            System.out.println(p.getNickname() + " " + point);
            if(point > winner.getScore())
                winner = p;
        }
        final Player tmpWinner = winner;
        if(players.stream().filter(p -> p.getScore() == tmpWinner.getScore()).count() > 1)
            throw new DrawMatchException();
        return winner;
    }

}