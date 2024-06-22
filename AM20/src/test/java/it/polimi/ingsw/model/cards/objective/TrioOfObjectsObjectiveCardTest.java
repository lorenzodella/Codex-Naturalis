package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.playable.*;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TrioOfObjectsObjectiveCardTest {

    TrioOfObjectsObjectiveCard s;
    TrioOfObjectsObjectiveCard getExampleTrioOfObjectsObjectiveCard(){
        ArrayList<ObjectiveCard> TrioOfObjectsObjectiveCard = XMLparser.parseObjectiveCards("/xml/objectiveCards.xml");
        return (TrioOfObjectsObjectiveCard) TrioOfObjectsObjectiveCard.stream().filter(x->x.getID().equals("O99")).findAny().orElse(null);
    }

    StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("/xml/starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }

    ResourceCard getExampleResourceCard(String id){
        ArrayList<PlayableCard> ResourceCard = XMLparser.parseResourceCards("/xml/resourceCards.xml");
        return (ResourceCard) ResourceCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    PointsGoldCard getExamplePointsGoldCard(String id){
        ArrayList<PlayableCard> PointsGoldCard = XMLparser.parseGoldCards("/xml/goldCards.xml");
        return (PointsGoldCard) PointsGoldCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    @BeforeEach
    void setUp(){ s = getExampleTrioOfObjectsObjectiveCard();}

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
        ResourceCard resourceCard = getExampleResourceCard("R26");
        ResourceCard resourceCard2 = getExampleResourceCard("R16");
        PointsGoldCard pointsGoldCard = getExamplePointsGoldCard("G48");
        PlayerTable playerTable = new PlayerTable();
        addRequirementsOfGoldCard(playerTable, pointsGoldCard);
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(pointsGoldCard, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard2, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(3,s.computePoints(playerTable));

        playerTable = new PlayerTable();
        addRequirementsOfGoldCard(playerTable, pointsGoldCard);
        starterCard = getExampleStarterCard();
        ResourceCard resourceCard3 = getExampleResourceCard("R15");
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(pointsGoldCard, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard3, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(0,s.computePoints(playerTable));

        playerTable = new PlayerTable();
        addRequirementsOfGoldCard(playerTable, pointsGoldCard);
        starterCard = getExampleStarterCard();
        ResourceCard resourceCard4 = getExampleResourceCard("R25");
        PointsGoldCard pointsGoldCard2 = getExamplePointsGoldCard("G49");
        PointsGoldCard pointsGoldCard3 = getExamplePointsGoldCard("G69");
        addRequirementsOfGoldCard(playerTable, pointsGoldCard2);
        addRequirementsOfGoldCard(playerTable, pointsGoldCard3);
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(pointsGoldCard, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard2, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard4, Corner.UL, resourceCard2.getID(), PlayableCard.FRONT);
        playerTable.insertCard(pointsGoldCard2, Corner.UR, pointsGoldCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(pointsGoldCard3, Corner.UL, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(6,s.computePoints(playerTable));

    }


}