package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.util.XMLparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private List<Player>  players;
    private Deck resourceCardDeck;
    private Deck goldCardDeck;
    private ObjectiveCard[] commonObjectives;
    private Player currPlayer;

    //TODO:
    // volgiamo istanziare solamente il primo player oppure instanziamo già tutti i Player ttamite il controller? Io dico di passare al costruttore del Game già la lsita dei player dal controller
    public Game(List<Player> players){
        this.players = players;
        currPlayer = players.get(0);
    }

    public void initDecks(){
        resourceCardDeck = new Deck(XMLparser.parseResourceCards("resourceCards.xml"));
        resourceCardDeck.shuffle();
        resourceCardDeck.initVisibleCards();
        goldCardDeck = new Deck(XMLparser.parseGoldCards("goldCards.xml"));
        goldCardDeck.shuffle();
        goldCardDeck.initVisibleCards();
    }

    public void initStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        Collections.shuffle(starterCards);
        this.giveInitialCards(starterCards);
    }

    private void giveInitialCards(ArrayList<PlayableCard> starterCards) {
        // metodo fittizio per mandare le due carte tra cui sscegliere ai player nel controller: giveStartercardPlayer(PlayableCard c1, PlayableCard c2, Player p);
        /*for(int i=0;i<players.size(); i++){
            res = chooseStarterCardPlayer(starterCards.get(i), starterCards.get(i+1), players.get(i));
            players.get(i).setStarterCard(res);
        }*/


    }

    public void chooseStarterCard(boolean front, Player p){
        p.positionStarterCard(front);
    }





}