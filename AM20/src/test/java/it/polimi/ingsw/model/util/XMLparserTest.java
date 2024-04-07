package it.polimi.ingsw.model.util;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.ResourceCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class XMLparserTest {

    @Test
    void parseStarterCards() {
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        Corner[] backCorners = new Corner[4];
        backCorners[0] = new Corner();
        backCorners[1] = new Corner();
        Corner[] frontCorners = new Corner[4];
        frontCorners[0] = new Corner(Kingdom.Insect);
        frontCorners[1] = new Corner(Kingdom.Fungi);
        frontCorners[2] = new Corner(Kingdom.Plant);
        frontCorners[3] = new Corner(Kingdom.Animal);
        ArrayList<Kingdom> res = new ArrayList<>();
        res.add(Kingdom.Animal);
        res.add(Kingdom.Insect);
        res.add(Kingdom.Plant);
        StarterCard s = new StarterCard("S85", frontCorners, backCorners, res);
        assertEquals(s, starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null));
        System.out.println(starterCards);
    }

    @Test
    void parseResourceCards() {
        ArrayList<PlayableCard> resourceCards = XMLparser.parseResourceCards("resourceCards.xml");
        Corner[] frontCorners = new Corner[4];
        frontCorners[0] = new Corner();
        frontCorners[1] = new Corner();
        frontCorners[2] = new Corner(Kingdom.Plant);
        Corner[] backCorners = new Corner[4];
        backCorners[0] = new Corner();
        backCorners[1] = new Corner();
        backCorners[2] = new Corner();
        backCorners[3] = new Corner();
        Kingdom k = Kingdom.Plant;
        int points = 1;
        ResourceCard s = new ResourceCard("R18", frontCorners, backCorners, k, points);
        assertEquals(s, resourceCards.stream().filter(x->x.getID().equals("R18")).findAny().orElse(null));
        System.out.println(resourceCards);
    }

    @Test
    void parseGoldCards() {
        /*TODO PER ELE
        fai come ho fatto sopra, crea una carta per ogni tipo di carta gold (object, corner, points)
        e verifica che sia uguale a quella letta dall'xml
         */
    }

    @Test
    void parseObjectiveCards() {
        /*TODO PER ELE
        fai come ho fatto sopra, crea una carta per ogni tipo di carta obiettivo (...)
        e verifica che sia uguale a quella letta dall'xml
         */
    }

}