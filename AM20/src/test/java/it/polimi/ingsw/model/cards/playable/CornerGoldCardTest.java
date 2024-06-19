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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CornerGoldCardTest {
    CornerGoldCard s;

    CornerGoldCard getExampleCornerGoldCard(String id){
        ArrayList<PlayableCard> CornerGoldCard = XMLparser.parseGoldCards("src/main/resources/xml/goldCards.xml");
        return (CornerGoldCard) CornerGoldCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("src/main/resources/xml/starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }

    @BeforeEach
    void setUp(){
        s = getExampleCornerGoldCard("G74");
    }

    @Test
    void getKingdoms(){
        HashMap<Kingdom, Integer> map = Kingdom.createEmptyMap();
        assertEquals(map, s.getKingdoms());

        //turn card to the back
        s.setSide(PlayableCard.BACK);
        map.put(Kingdom.Insect,1);
        assertEquals(map, s.getKingdoms());
    }

    @Test
    void getSpecialObjets(){
        HashMap<SpecialObject, Integer> map = SpecialObject.createEmptyMap();
        assertEquals(map,s.getSpecialObjects());

        s.setSide(PlayableCard.BACK);
        assertEquals(map, s.getSpecialObjects());
    }

    @Test
    void getRequirements(){
        HashMap<Kingdom, Integer> map = Kingdom.createEmptyMap();
        s.setSide(PlayableCard.BACK);
        assertEquals(map, s.getRequirements());

        s.setSide(PlayableCard.FRONT);
        map.put(Kingdom.Insect,3);
        map.put(Kingdom.Animal,1);
        assertEquals(map,s.getRequirements());
    }

    void addRequirementsOfGoldCard(PlayerTable playerTable, GoldCard gc){
        for(Map.Entry<Kingdom, Integer> e : gc.getRequirements().entrySet()) {
            for (int i = 0; i < e.getValue(); i++) {
                playerTable.getStats().addKingdom(e.getKey());
            }
        }
    }

    @Test
    void testComputePoints() throws TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException {
        StarterCard starterCard = getExampleStarterCard();
        PlayerTable playerTable = new PlayerTable();
        addRequirementsOfGoldCard(playerTable, s);
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(s, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(2,s.computePoints(playerTable));

        starterCard = getExampleStarterCard();
        playerTable = new PlayerTable();
        addRequirementsOfGoldCard(playerTable, s);
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(s, Corner.UR, starterCard.getID(), PlayableCard.BACK);
        assertEquals(0,s.computePoints(playerTable));

        starterCard = getExampleStarterCard();
        CornerGoldCard s1= getExampleCornerGoldCard("G65");
        CornerGoldCard s2 = getExampleCornerGoldCard("G66");
        playerTable = new PlayerTable();
        addRequirementsOfGoldCard(playerTable, s);
        addRequirementsOfGoldCard(playerTable, s1);
        addRequirementsOfGoldCard(playerTable, s2);
        playerTable.insertStarterCard(PlayableCard.BACK, starterCard);
        playerTable.insertCard(s1, Corner.UL, starterCard.getID(), PlayableCard.BACK);
        playerTable.insertCard(s2, Corner.UR, starterCard.getID(), PlayableCard.BACK);
        playerTable.insertCard(s, Corner.UR, s1.getID(), PlayableCard.FRONT);
        assertEquals(4,s.computePoints(playerTable));
    }


}