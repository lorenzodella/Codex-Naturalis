package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StarterCardTest {
    StarterCard s;

    @BeforeEach
    void setUp(){
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
        s = new StarterCard("S0", frontCorners, backCorners, res);
    }

    @Test
    void getKingdoms(){
        HashMap<Kingdom, Integer> map = Kingdom.createEmptyMap();
        map.put(Kingdom.Plant, 1);
        map.put(Kingdom.Insect, 2);

        assertEquals(map, s.getKingdoms());

        s.setFront(false);

        map.put(Kingdom.Fungi, 1);
        map.put(Kingdom.Animal, 1);
        map.put(Kingdom.Plant, 1);
        map.put(Kingdom.Insect, 1);

        assertEquals(map, s.getKingdoms());
    }

    @Test
    void getSpecialObjects(){
        HashMap<SpecialObject, Integer> map = SpecialObject.createEmptyMap();

        assertEquals(map, s.getSpecialObjects());
    }

}