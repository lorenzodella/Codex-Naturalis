package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.exceptions.InsertionException;
import it.polimi.ingsw.model.exceptions.InvalidAngleCoveredException;
import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PointsGoldCardTest {
    PointsGoldCard s;

    PointsGoldCard getExamplePointsGoldCard(String id){
        ArrayList<PlayableCard> PointsGoldCard = XMLparser.parseGoldCards("goldCards.xml");
        return (PointsGoldCard) PointsGoldCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    @BeforeEach
    void setUp(){
        s = getExamplePointsGoldCard("G48");
    }

    @Test
    void getKingdoms(){
        HashMap<Kingdom, Integer> map = Kingdom.createEmptyMap();
        assertEquals(map, s.getKingdoms());

        //turn card to the back
        s.setSide(PlayableCard.BACK);
        map.put(Kingdom.Fungi,1);
        assertEquals(map, s.getKingdoms());
    }

    @Test
    void getSpecialObjets(){
        HashMap<SpecialObject, Integer> map = SpecialObject.createEmptyMap();
        map.put(SpecialObject.Quill,1);
        assertEquals(map,s.getSpecialObjects());

        map = SpecialObject.createEmptyMap();
        s.setSide(PlayableCard.BACK);
        assertEquals(map, s.getSpecialObjects());
    }

    @Test
    void getRequirements(){
        HashMap<Kingdom, Integer> map = Kingdom.createEmptyMap();
        s.setSide(PlayableCard.BACK);
        assertEquals(map, s.getRequirements());

        s.setSide(PlayableCard.FRONT);
        map.put(Kingdom.Fungi,3);
        assertEquals(map,s.getRequirements());
    }

    @Test
    void testComputePoints(){
        PlayerTable playerTable = new PlayerTable();
        playerTable.updateStats(s);
        assertEquals(3,s.computePoints(playerTable));

        s.setSide(PlayableCard.BACK);
        playerTable = new PlayerTable();
        playerTable.updateStats(s);
        assertEquals(0,s.computePoints(playerTable));


    }


}