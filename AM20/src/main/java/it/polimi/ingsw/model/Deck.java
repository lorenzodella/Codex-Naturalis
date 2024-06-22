package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.exceptions.FinishedCardStackException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck {
    public static final int RESOURCE_CARDS = 1;
    public static final int GOLD_CARDS = 0;

    /**
     * this attribute stands for the stack that contains all the cards
     */
    private Stack<PlayableCard> cards;
    /**
     * this attribute is a 2 items array that contains the 2 visible cards
     */
    private PlayableCard[] visibleCards;

    public Deck(ArrayList<PlayableCard> cardList){
        cards = new Stack<>();
        cards.addAll(cardList);
        visibleCards = new PlayableCard[2];

    }

    public Stack<PlayableCard> getCards() {
        return cards;
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
     * @return the card on the top of the deck
     * @throws FinishedCardStackException if the deck is empty
     */
    public PlayableCard draw() throws FinishedCardStackException {
        if(cards.isEmpty())
            throw new FinishedCardStackException();
        else
            return cards.pop();
    }

    /**
     * This method allows to see the card on the top of the deck.
     * @return the first card, null if deck is empty
     */
    public PlayableCard getFirstCard(){
        if(cards.isEmpty())
            return null;
        else
            return cards.peek();
    }

    /**
     * This method allows to see the visible card at the given index.
     * @param index position of the card to see
     * @return the requested visible card
     */
    public PlayableCard getVisibleCard(int index) {
        return visibleCards[index];
    }

    /**
     *
     * @return the array of the visible card
     */
    public PlayableCard[] getVisibleCards() {
        return visibleCards;
    }

    /**
     * This method allows the user to pick one of the two visible cards that are shown on the table.
     * These two cards are stored as an array of two elements and the card that the user wants to pick up
     * would be the one that's in the index position.
     * This method also picks a card A from the top of the deck ( draw() )and replaces the chosen visible card by
     * putting the A in the index position --> in order to always have two visible cards on the table
     * @param index: this is the position of the card that player picks up
     * @return it returns the card that the player wanted to pick up
     * @throws FinishedCardStackException if the chosen card is not present (because deck is empty)
     */
    public PlayableCard drawVisibleCard(int index) throws FinishedCardStackException {
        PlayableCard res = visibleCards[index];
        if(res==null)
            throw new FinishedCardStackException();
        try {
            this.visibleCards[index] = this.draw();
            //if draw() throws an exception is because there is no card to replace the visible card with
        } catch(FinishedCardStackException e){
            this.visibleCards[index] = null;
        }
        return res;
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }
}

