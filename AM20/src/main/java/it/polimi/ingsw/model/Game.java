package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.util.XMLparser;

import java.util.*;

public class Game {
    private ArrayList<Player>  players;
    private Deck resourceCardDeck;
    private Deck goldCardDeck;
    private ObjectiveCard[] commonObjectives;
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

    //TODO da testare
    public void initDecks(){
        resourceCardDeck = new Deck(XMLparser.parseResourceCards("resourceCards.xml"));
        resourceCardDeck.shuffle();
        resourceCardDeck.initVisibleCards();
        goldCardDeck = new Deck(XMLparser.parseGoldCards("goldCards.xml"));
        goldCardDeck.shuffle();
        goldCardDeck.initVisibleCards();
    }

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

    //TODO da testare
    public void chooseStarterCardSide(int side, Player p){
        p.positionStarterCard(side);
    }

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

    public void chooseObjective(int index, Player p){
        p.chooseObjectiveCard(index);
    }

    //TODO da testare
    public Player chooseFirstPlayer(){
        Collections.shuffle(players);
        currPlayer = players.get(0);
        return currPlayer;
    }

    public Player playCard(int indexCard, int angle, String targetID, int side) throws TargetNotPresentException, InsertionException, InvalidAngleCoveredException, InvalidPositionException {
        currPlayer.playCard(indexCard, angle, targetID, side);
        return currPlayer;
    }


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