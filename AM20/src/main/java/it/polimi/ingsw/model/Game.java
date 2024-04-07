package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.exceptions.InvalidArgumentException;
import it.polimi.ingsw.model.util.XMLparser;

import java.util.*;

public class Game {
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
    private int turn;

    public Game(List<Player> players){
        this.players = players;
        currPlayer = players.get(0);
        turn = 0;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public Deck getGoldCardDeck() {
        return goldCardDeck;
    }
    public Deck getResourceCardDeck() {
        return resourceCardDeck;
    }
    public Player getCurrPlayer() {
        return currPlayer;
    }

    public ObjectiveCard[] getCommonObjectives() {
        return commonObjectives;
    }

    /**
     * This method
     * 1. creates and suffles both gold and resource deck card
     * 2. displays two visible cards on the table (per each deck)
     */
    public void initDecks(){
        resourceCardDeck = new Deck(XMLparser.parseResourceCards("resourceCards.xml"));
        resourceCardDeck.shuffle();
        resourceCardDeck.initVisibleCards();
        goldCardDeck = new Deck(XMLparser.parseGoldCards("goldCards.xml"));
        goldCardDeck.shuffle();
        goldCardDeck.initVisibleCards();
    }

    /**
     * This method
     * 1. saves the initial cards into an arraylist (by reading them from the XML file)
     * 2. it shuffles the cards
     * 3. gives an initial card to every player
     */
    public void giveStarterCards(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        Collections.shuffle(starterCards);
        //this.giveInitialCards(starterCards);
        //metodo del controller che dice al gicoatore scegli quale deelle due starterCard HP: metodo chiamto PlayableCard selStarterCard(PlayableCard c1, PlayableCard c2, Player p)
        for(int i=0; i<players.size();i++){
            players.get(i).setStarterCard((StarterCard)starterCards.get(i));
        }
    }

    /**
     * This method gives the initial cards to every player:
     * it creates a linkedList per each player that contains 3 elements
     * (a gold card and two resource cards)
     */
    public void giveInitialCards() {
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
    }

    /**
     * This method allows the player p to choose the side (front/back) of the initial card
     * that it's been given to them
     * @param side it stands for the side of the card: front or back
     * @param nickname it stands for the player that's taking the action
     */
    public void chooseStarterCardSide(int side, String nickname) throws InvalidArgumentException{
        if(side != PlayableCard.FRONT && side != PlayableCard.BACK)
            throw new InvalidArgumentException("side", side);
        players.stream().filter(x -> x.getNickname().equals(nickname)).findFirst()
                .orElseThrow(()-> new InvalidArgumentException("nickname", nickname))
                .positionStarterCard(side);
    }

    /**
     * This method:
     * 1.  creates an arrayList where it saves all the objective cards (by reading them from the XML file)
     * 2.  shuffles the cards
     * 3.  creates an array of two elements, saving the two common objectives
     * 4.  creates an array of two elements that contains two possible secret objectives and it allows the player to choose
     *     their own secret objective between this two elements (by calling the chooseSecretObjective method)
     */
    public void initObjectiveCards(){
        ArrayList<ObjectiveCard> tmp = new ArrayList<>(XMLparser.parseObjectiveCards("objectiveCards.xml"));
        Collections.shuffle(tmp);
        commonObjectives = new ObjectiveCard[2];
        commonObjectives[0] = tmp.get(0);
        commonObjectives[1] = tmp.get(1);

        for(int i=0; i<players.size();i++){
            ObjectiveCard[] obj = new ObjectiveCard[2];
            obj[0] = tmp.get(i+2);
            obj[1] = tmp.get(i+3);
            players.get(i).setSecretObjective(obj);
        }
    }

    /**
     * This method allows the player p to select the secret objetive they've chosen
     * @param index the player p selects the objective by choosing the index (0 or 1) of the array that contains the two possible
     *              secret objectives
     * @param nickname the nickname is the nickname of the player that's taking the action
     */
    public void chooseObjective(int index, String nickname) throws InvalidArgumentException{
        if(index<0 || index>1)
            throw new InvalidArgumentException("index", index);
        players.stream().filter(x -> x.getNickname().equals(nickname)).findFirst()
                .orElseThrow(()-> new InvalidArgumentException("nickname", nickname))
                .chooseObjectiveCard(index);
    }

    /**
     * This method shuffles the arrayList of player and sets as first player the one that
     * happens to be in the 0 position
     * @return the current player
     */
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

    //TODO VOGLIAMO SPLITTARE IL METODO IN DUE? (uno per pescare dal mazzo, uno per perscare una visiblecard)
    /**
     * This method picks the card from the top of the spicified deck (deck) or it picks one of the two visible cards.
     * Specifically, it picks the visible card that's found at the index position of the arraylist of the visible cards.
     * //TODO questa frase mi sembra scritta da tia talmente non si capisce niente...
     * This method also calls getVisible (that's found in "deck") and this method also
     * returns the chosen visible card and it adds it at the list cards (that's found in player).
     * //TODO la mia prof di inglese diceva sempre "QUESTA E' LOGICA NON INGLESE!!!!"
     * ---->>> Once this method retrieved the correct new card, it adds that to the current player's card list.
     * @param deck : this attribute stands for the specific deck that you want to pick a card from
     * @param visible : this attribute is a boolean that specifies if the player wants to pick a card from the deck or
     *                from a visible card(...)
     * @param index : this attribute stands for the index of the card that's in the visible cards array
     * @return the current player that's just picked the card
     * @throws FinishedCardStackException if the deck's done
     */
    public Player pickCard(int deck, boolean visible, int index ) throws FinishedCardStackException, InvalidArgumentException {
    // choiceDeck = 1 resourceCard  choiceDeck = 0 goldCard
    // visibile = true carta visibile all'indice index
        if(index<0 || index>1)
            throw new InvalidArgumentException("index", index);

        Deck chosenDeck;
        PlayableCard card;
        if(deck == Deck.RESOURCE_CARDS )
            chosenDeck = resourceCardDeck;
        else if(deck == Deck.GOLD_CARDS)
            chosenDeck = goldCardDeck;
        else
            throw new InvalidArgumentException("deck", deck);

        if(!visible)
            card = chosenDeck.draw();
        else
            card = chosenDeck.drawVisibleCard(index);

        this.currPlayer.drawCard(card);

        return currPlayer;

    }

    /**
     * Sets current player as the next player, following playing order.
     * @return true if next player is the first player, so a new turn started
     */
    public boolean nextTurn(){
        int cur = players.indexOf(currPlayer);
        if(cur == players.size()-1) {
            turn++;
            currPlayer = players.get(0);
            return true;
        }
        this.currPlayer = players.get(cur+1);
        return false;
    }

    /**
     * This method checks, every time that a player ends their turn, if they've reached the 20 points
     * (in that case the first phase of the game ends and the second phase starts) and it also checks if one of the deck
     * is empty (if the cards of that specific deck have been all played).
     * @return 1 if the player has reached 20 points (or more) or if the deck is empty, 0 otherwise
     */
    public boolean checkTheEnd() {
        return currPlayer.getScore() >= 20 || areDeckFinished();
    }

    /**
     * Checks if both decks are empty, meaning that game must finish during the next turn
     * @return true if both decks are empty
     */
    public boolean areDeckFinished() {
        return  resourceCardDeck.getVisibleCard(0) == null &&
                resourceCardDeck.getVisibleCard(1) == null &&
                goldCardDeck.getVisibleCard(0) == null &&
                goldCardDeck.getVisibleCard(1) == null;
    }

    /**
     * This method, at the end of the game, computes the points of the secret objective of every player
     */
    public void computePlayerSecretObjectives(){
        for(Player p: players){
            p.computeSecretObjective();
        }
    }

    /**
     * This method adds, per each player, the points of the common objective
     */
    public void computeCommonObjectives(){
        for(Player p: players){
            for(ObjectiveCard obj : commonObjectives){
                p.computeCommonObjective(obj);
            }
        }
    }

    /**
     * This method returns to the controller the player that won the game
     * @return the player that won
     */
    //da decidere come gestire il caso di parità
    public Player checkWinner(){
        Player winner = players.get(0);
        for(Player p :players){
            int point = p.getScore();
            if(point >= winner.getScore())
                winner = p;
        }
        return winner;
    }

}