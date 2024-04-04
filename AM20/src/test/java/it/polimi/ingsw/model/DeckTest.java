package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.exceptions.finishedCardStack;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Deck d;

    @BeforeEach
    void setUp() {
        d = new Deck(XMLparser.parseResourceCards("resourceCards.xml"));
        d.shuffle();
        d.initVisibleCards();
    }

    @Test
    void getVisibleCard() throws finishedCardStack {
        PlayableCard oldtop = d.getCards().peek();
        d.drawVisibleCard(0);
        assertEquals(oldtop, d.getVisibleCard(0));
    }

    @Test
    void empty() throws finishedCardStack {
        int dim = d.getCards().size();
        for (int i = 0; i < dim; i++) {
            d.draw();
        }
        assertThrows(finishedCardStack.class, ()->d.draw());
    }
}