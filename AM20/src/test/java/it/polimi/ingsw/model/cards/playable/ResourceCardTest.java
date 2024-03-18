package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.playable.ResourceCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceCardTest {
    ResourceCard resourceCard;

    @BeforeEach
    void setUp() {
        Corner[] frontCorners = new Corner[4];
        frontCorners[0] = new Corner();
        frontCorners[1] = new Corner(Kingdom.Plant);
        frontCorners[2] = new Corner(SpecialObject.Inkwell);
        frontCorners[3] = new Corner();
        Corner[] backCorners = new Corner[4];
        backCorners[0] = new Corner();
        backCorners[1] = new Corner();
        backCorners[2] = new Corner();
        backCorners[3] = new Corner();
        resourceCard = new ResourceCard("S0", frontCorners, backCorners, Kingdom.Animal, 0);
    }

    @Test
    void testGetKingdoms() {
        HashMap<Kingdom, Integer> map = Kingdom.createEmptyMap();
        map.put(Kingdom.Plant, 1);

        assertEquals(map, resourceCard.getKingdoms());

        resourceCard.setFront(false);

        map = Kingdom.createEmptyMap();
        map.put(Kingdom.Animal, 1);

        assertEquals(map, resourceCard.getKingdoms());
    }

    @Test
    void testGetSpecialObjects() {
        HashMap<SpecialObject, Integer> map = SpecialObject.createEmptyMap();
        map.put(SpecialObject.Inkwell, 1);

        assertEquals(map, resourceCard.getSpecialObjects());

        resourceCard.setFront(false);

        map = SpecialObject.createEmptyMap();
        assertEquals(map, resourceCard.getSpecialObjects());
    }
}