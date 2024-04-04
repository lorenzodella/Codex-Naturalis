package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.exceptions.finishedCardStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck {
    public static final int RESOURCE_CARDS = 1;
    public static final int GOLD_CARDS = 0;

    private Stack<PlayableCard> cards;
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
     */
    public PlayableCard draw() throws finishedCardStack {
        if(cards.isEmpty())
            throw new finishedCardStack();
        else
            return cards.pop();
    }

    /**
     * This method allows to see the kingdom of the card on the top of the deck.
     * @return the kingdom of the card
     */
    public Kingdom geFirstCardKingdom(){
        return cards.peek().getCardKingdom();
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
     * This method allows the user to pick one of the two visible cards that are shown on the table.
     * These two cards are stored as an array of two elements and the card that the user wants to pick up
     * would be the one that's in the index position.
     * This method also picks a card A from the top of the deck ( draw() )and replaces the chosen visible card by
     * putting the A in the index position --> in order to always have two visible cards on the table
     * @param index: this is the position of the card that player picks up
     * @return it returns the card that the player wanted to pick up
     * @throws finishedCardStack if the deck is empty
     */
    public PlayableCard drawVisibleCard(int index) throws finishedCardStack {
        PlayableCard res = visibleCards[index];
        this.visibleCards[index] = this.draw();
        return res;
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }
}

