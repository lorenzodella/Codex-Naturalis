package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ObjectGoldCardTest {
    ObjectGoldCard s;

    ObjectGoldCard getExampleObjectGoldCard(){
        ArrayList<PlayableCard> ObjectGoldCard = XMLparser.parseGoldCards("goldCards.xml");
        return (ObjectGoldCard) ObjectGoldCard.stream().filter(x->x.getID().equals("G42")).findAny().orElse(null);
    }

    ResourceCard getExampleResourceCard(String id){
        ArrayList<PlayableCard> ResourceCard = XMLparser.parseResourceCards("resourceCards.xml");
        return (ResourceCard) ResourceCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }

    @BeforeEach
    void setUp(){
        s = getExampleObjectGoldCard();
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
        s.setSide(PlayableCard.BACK);
        assertEquals(map,s.getSpecialObjects());

        s.setSide(PlayableCard.FRONT);
        map.put(SpecialObject.Inkwell,1);
        assertEquals(map, s.getSpecialObjects());
    }

   @Test
    void getRequirements(){
        HashMap<Kingdom, Integer> map = Kingdom.createEmptyMap();
        s.setSide(PlayableCard.BACK);
        assertEquals(map, s.getRequirements());

        s.setSide(PlayableCard.FRONT);
        map.put(Kingdom.Fungi,2);
        map.put(Kingdom.Plant, 1);
        assertEquals(map,s.getRequirements());
    }

    @Test
    void testComputePoints() throws TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException {
        PlayerTable playerTable = new PlayerTable();
        playerTable.updateStats(s);
        assertEquals(1,s.computePoints(playerTable));

        s.setSide(PlayableCard.BACK);
        playerTable = new PlayerTable();
        playerTable.updateStats(s);
        assertEquals(0,s.computePoints(playerTable));

        ResourceCard resourceCard = getExampleResourceCard("R16");
        StarterCard starterCard = getExampleStarterCard();
        playerTable = new PlayerTable();
        playerTable.insertStarterCard(PlayableCard.BACK, starterCard);
        playerTable.insertCard(s, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard, Corner.UL, s.getID(), PlayableCard.FRONT);
        assertEquals(2,s.computePoints(playerTable));
    }


}
