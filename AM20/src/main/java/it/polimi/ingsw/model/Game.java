package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.util.XMLparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private List<Player>  players;
    private Deck resourceCardDeck;
    private Deck goldCardDeck;
    private ObjectiveCard[] commonObjectives;
    private Player currPlayer;

    public Game(List<Player> players){
        this.players = players;
        currPlayer = players.get(0);
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
    public void initStarterCard(){
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
    public void chooseFirstPlayer(){
        Collections.shuffle(players);
    }

    public void playCard(int indexCard, int angle, String targetID, int side) throws TargetNotPresentException, InsertionException, InvalidAngleCoveredException, InvalidPositionException {
        currPlayer.playCard(indexCard, angle, targetID, side);
    }


    // choiceDeck = true resourceCard  choiceDeck = 0 goldCard
    // visibile = true carta visibile all'indice index
    public boolean pickCard(int choiceDeck, boolean visible, int index ) throws finishedCardStack {
        Deck deckChoosen;
        PlayableCard card;
        if(choiceDeck == 1 )
            deckChoosen = resourceCardDeck;
        else
            deckChoosen = goldCardDeck;

        if(visible == false)
            card = deckChoosen.draw();
        else
            card = deckChoosen.getVisibleCard(index);

        this.currPlayer.drawCard(card);
        boolean res = this.checkTheEnd();
        this.currPlayer = this.nextPlayer();
        return res;

    }

    //TODO da testare
    private Player nextPlayer(){return players.get(players.indexOf(currPlayer)+1);}


    //metodo chaimato dal controllore appea dopo che chiama playCard e pickCard
    private boolean checkTheEnd() throws finishedCardStack {
        return currPlayer.getScore() >= 20 || finishedDeck();
    }

    private boolean finishedDeck() throws finishedCardStack {
        return  resourceCardDeck.getVisibleCard(0) != null || resourceCardDeck.getVisibleCard(1) != null || goldCardDeck.getVisibleCard(0) != null || goldCardDeck.getVisibleCard(1) != null;
    }

    public void computePlayerSecretObjectives(){}

    public void computeCommonObjectives(){}

    public Player checkWinner(){return null;}

}