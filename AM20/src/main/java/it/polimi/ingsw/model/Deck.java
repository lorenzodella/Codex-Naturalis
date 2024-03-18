package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.PlayableCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck {
    private Stack<PlayableCard> cards;
    private PlayableCard[] visibleCards;

    public Deck(ArrayList<PlayableCard> cardList){
        cards = new Stack<>();
        cards.addAll(cardList);
        visibleCards = new PlayableCard[2];
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public void initVisibleCards(){
        visibleCards[0] = cards.pop();
        visibleCards[1] = cards.pop();
    }

    public PlayableCard draw(){
        return cards.pop();
    }

    public PlayableCard getVisibleCard(int index){
        return visibleCards[index];
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }
}

