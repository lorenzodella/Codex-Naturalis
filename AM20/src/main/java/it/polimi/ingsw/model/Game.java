package it.polimi.ingsw.model;
import it.polimi.ingsw.model.cards.ObjectiveCard;

import java.util.*;

public class Game {
    private List<Player>  players;
    private Deck resourceDeck;
    private Deck goldCardDeck;
    private ObjectiveCard[] commonObjectives;
    private Player currPlayer;

    //TODO:
    // volgiamo istanziare solamente il primo player oppure instanziamo già tutti i Player ttamite il controller? Io dico di passare al costruttore del Game già la lsita dei player dal controller
    public Game(List<Player> players){
        this.players = players;
        currPlayer = players.get(0);
    }


}