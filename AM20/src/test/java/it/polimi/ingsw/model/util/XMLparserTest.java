package it.polimi.ingsw.model.util;

import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.Kingdom;
import it.polimi.ingsw.model.StarterCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class XMLparserTest {

    @Test
    void parseStarterCards() {
        ArrayList<StarterCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        Corner[] frontCorners = new Corner[4];
        frontCorners[0] = new Corner();
        frontCorners[1] = new Corner(Kingdom.Plant);
        frontCorners[2] = new Corner(Kingdom.Insect);
        frontCorners[3] = new Corner();
        Corner[] backCorners = new Corner[4];
        backCorners[0] = new Corner(Kingdom.Fungi);
        backCorners[1] = new Corner(Kingdom.Plant);
        backCorners[2] = new Corner(Kingdom.Insect);
        backCorners[3] = new Corner(Kingdom.Animal);
        ArrayList<Kingdom> res = new ArrayList<>();
        res.add(Kingdom.Insect);
        StarterCard s = new StarterCard("S0", frontCorners, backCorners, res);
        assertEquals(starterCards.get(0), s);
        System.out.println(starterCards);
    }
}