package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
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
        //this.giveInitialCards(starterCards);
        //metodo del controller che dice al gicoatore scegli quale deelle due starterCard HP: metodo chiamto PlayableCard selStarterCard(PlayableCard c1, PlayableCard c2, Player p)


        for(int i=0; i<players.size();i++){
            players.get(i).setStarterCard((StarterCard)starterCards.get(i));
        }

    }

    //TODO:
    public void giveInitialCards() {

        for(Player p: players){
            LinkedList<PlayableCard> carte = new LinkedList<>();

            carte.add(resourceCardDeck.draw());
            carte.add(resourceCardDeck.draw());
            carte.add(goldCardDeck.draw());

            p.drawInitialPlayableCard(carte);

        }
    }

    public void chooseStarterCardSide(int front, Player p){
        p.positionStarterCard(front);
    }

    public void initObjectiveCards(){

        ArrayList<ObjectiveCard> tmp = new ArrayList<>(XMLparser.parseObjectiveCards("objectiveCards.xml"));
        Collections.shuffle(tmp);
        commonObjectives = new ObjectiveCard[2];
        commonObjectives[0] = tmp.get(0);
        commonObjectives[0] = tmp.get(1);

        for(int i=0; i<players.size();i++){
            ObjectiveCard[] obj = new ObjectiveCard[2];
            obj[0] = commonObjectives [i+2];
            obj[0] = commonObjectives [i+3];
            players.get(i).setSecretObjective(obj);
        }
    }

    public void chooseObjective(int index){}

    public void chooseFirstPlayer(){
        Collections.shuffle(players);
    }

    public boolean playCard(int indexCard, int angle, int cardID, boolean front){return true;}

    public boolean pickCard(boolean choiceDeck, boolean visible, int index ) {return true;}

    public boolean checkTheEnd(){return true;}

    public void computePlayerSecretObjectives(){}

    public void computeCommonObjectives(){}

    public Player checkWinner(){return players.get(0);}

}