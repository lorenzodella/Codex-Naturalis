package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCardTest {
    ResourceCard s;

    ResourceCard getExampleResourceCard(String id){
        ArrayList<PlayableCard> ResourceCard = XMLparser.parseResourceCards("/xml/resourceCards.xml");
        return (ResourceCard) ResourceCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    @BeforeEach
    void setUp(){
        s = getExampleResourceCard("R15");
    }

    @Test
    void getKingdoms() {
        HashMap<Kingdom, Integer> map = Kingdom.createEmptyMap();
        map.put(Kingdom.Plant,1);
        map.put(Kingdom.Insect,1);
        assertEquals(map, s.getKingdoms());

        map = Kingdom.createEmptyMap();
        s.setSide(PlayableCard.BACK);
        map.put(Kingdom.Plant,1);
        assertEquals(map, s.getKingdoms());
    }

    @Test
    void getSpecialObjects() {
        HashMap<SpecialObject, Integer> map = SpecialObject.createEmptyMap();
        s.setSide(PlayableCard.BACK);
        assertEquals(map,s.getSpecialObjects());

        map.put(SpecialObject.Quill,1);
        s.setSide(PlayableCard.FRONT);
        assertEquals(map, s.getSpecialObjects());
    }

    @Test
    void testGetRequirements(){
        HashMap<Kingdom, Integer> map = Kingdom.createEmptyMap();
        assertEquals(map, s.getRequirements());

        s.setSide(PlayableCard.BACK);
        assertEquals(map,s.getRequirements());
    }

    @Test
    void testComputePoints(){
        s = getExampleResourceCard("R18");
        PlayerTable playerTable = new PlayerTable();
        playerTable.updateStats(s);
        assertEquals(1,s.computePoints(playerTable));

        s.setSide(PlayableCard.BACK);
        playerTable = new PlayerTable();
        playerTable.updateStats(s);
        assertEquals(0,s.computePoints(playerTable));
    }

}