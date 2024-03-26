package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.exceptions.finishedCardStack;

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

    public PlayableCard draw() throws finishedCardStack {
        if(cards.isEmpty())
            throw new finishedCardStack();
        else
            return cards.pop();
    }

    public PlayableCard getVisibleCard(int index) throws finishedCardStack {
        PlayableCard res = visibleCards[index];
        this.visibleCards[index] = this.draw();
        return res;
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }
}

