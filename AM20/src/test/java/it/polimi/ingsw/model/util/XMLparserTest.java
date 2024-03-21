package it.polimi.ingsw.model.util;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class XMLparserTest {

    @Test
    void parseStarterCards() {
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        Corner[] frontCorners = new Corner[4];
        frontCorners[0] = new Corner();
        frontCorners[1] = new Corner();
        Corner[] backCorners = new Corner[4];
        backCorners[0] = new Corner(Kingdom.Insect);
        backCorners[1] = new Corner(Kingdom.Fungi);
        backCorners[2] = new Corner(Kingdom.Plant);
        backCorners[3] = new Corner(Kingdom.Animal);
        ArrayList<Kingdom> res = new ArrayList<>();
        res.add(Kingdom.Animal);
        res.add(Kingdom.Insect);
        res.add(Kingdom.Plant);
        StarterCard s = new StarterCard("S85", frontCorners, backCorners, res);
        assertEquals(starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null), s);
        System.out.println(starterCards);
    }

    @Test
    void parseResourceCards() {

    }

}