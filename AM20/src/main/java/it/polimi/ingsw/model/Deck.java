package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.playable.PlayableCard;

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

    /**
     * This method allows to pick a card from the deck.
     * @return
     */
    public PlayableCard draw(){
        return cards.pop();
    }

    /**
     * This method allows the user to pick one of the two visible cards that are shown on the table.
     * These two cards are stored as an array of two elements and the card that the user wants to pick up
     * would be the one that's in the index position.
     * This method also picks a card A from the top of the deck ( draw() )and replaces the chosen visible card by
     * putting the A in the index position.
     * @param index
     * @return
     */
    public PlayableCard getVisibleCard(int index){

        return visibleCards[index];
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }
}



