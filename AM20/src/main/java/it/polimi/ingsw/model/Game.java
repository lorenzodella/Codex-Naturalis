package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.util.XMLparser;

import java.util.*;

public class Game {
    /**
     * Dinamic arrayList of players:
     * Before starting the game, the first player choses the number of players they want to play with.
     * The number of players needs to be between 2 and 4.
     */
    private ArrayList<Player>  players;
    /**
     *  deck of resource cards
     */
    private Deck resourceCardDeck;
    /**
     *  deck of golden cards
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

    public Game(ArrayList<Player> players){
        this.players = players;
        currPlayer = players.get(0);
        turn = 0;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    public Deck getGoldCardDeck() {
        return goldCardDeck;
    }
    public Deck getResourceCardDeck() {
        return resourceCardDeck;
    }

    /**
     * This method
     * 1. create both gold and resource deck card
     * 2. it shuffles both decks
     * 3. ???
     */
    //TODO da testare
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
    //TODO da testare
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
    //TODO da testare
    public void giveInitialCards() {
        for(Player p: players){
            LinkedList<PlayableCard> carte = new LinkedList<>();

            try {
                carte.add(resourceCardDeck.draw());
                carte.add(resourceCardDeck.draw());
                carte.add(goldCardDeck.draw());

                p.drawInitialPlayableCard(carte);
            } catch (finishedCardStack e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This method allows the player p to choose the side (front/back) of the initial card
     * that it's been given to them
     * @param side it stands for the side of the card: front or back
     * @param p it stands for the player that's taking the action
     */
    //TODO da testare
    public void chooseStarterCardSide(int side, Player p){
        p.positionStarterCard(side);
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
     * @param p the player p is the player that's taking the action
     */
    public void chooseObjective(int index, Player p){
        p.chooseObjectiveCard(index);
    }

    /**
     * This method shuffles the arrayList of player and sets as first player the one that
     * happens to be in the 0 position
     * @return
     */

    //TODO da testare
    public Player chooseFirstPlayer(){
        Collections.shuffle(players);
        currPlayer = players.get(0);
        return currPlayer;
    }

    /**
     * 
     * @param indexCard
     * @param angle
     * @param targetID
     * @param side
     * @return
     * @throws TargetNotPresentException
     * @throws InsertionException
     * @throws InvalidAngleCoveredException
     * @throws InvalidPositionException
     */
    public Player playCard(int indexCard, int angle, String targetID, int side) throws TargetNotPresentException, InsertionException, InvalidAngleCoveredException, InvalidPositionException {
        currPlayer.playCard(indexCard, angle, targetID, side);
        return currPlayer;
    }

    /**
     *
     * @param deck
     * @param visible
     * @param index
     * @return
     * @throws finishedCardStack
     */
    public Player pickCard(int deck, boolean visible, int index ) throws finishedCardStack {
    // choiceDeck = true resourceCard  choiceDeck = 0 goldCard
    // visibile = true carta visibile all'indice index
        Deck deckChoosen;
        PlayableCard card;
        if(deck == 1 )
            deckChoosen = resourceCardDeck;
        else
            deckChoosen = goldCardDeck;

        if(visible == false)
            card = deckChoosen.draw();
        else
            card = deckChoosen.getVisibleCard(index);

        this.currPlayer.drawCard(card);

        return currPlayer;

    }

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


    //metodo chaimato dal controllore appea dopo che chiama playCard e pickCard
    public boolean checkTheEnd() throws finishedCardStack {
        return currPlayer.getScore() >= 20 || finishedDeck();
    }

    private boolean finishedDeck() throws finishedCardStack {
        return  resourceCardDeck.getVisibleCard(0) != null || resourceCardDeck.getVisibleCard(1) != null || goldCardDeck.getVisibleCard(0) != null || goldCardDeck.getVisibleCard(1) != null;
    }

    public void computePlayerSecretObjectives(){
        for(Player p: players){
            p.computeSecretObjective();
        }
    }

    public void computeCommonObjectives(){
        for(Player p: players){
            for(ObjectiveCard obj : commonObjectives){
                p.computeCommonObjective(obj);
            }
        }
    }

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