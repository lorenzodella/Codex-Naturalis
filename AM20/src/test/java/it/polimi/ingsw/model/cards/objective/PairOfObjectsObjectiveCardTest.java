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

class PairOfObjectsObjectiveCardTest {
    PairOfObjectsObjectiveCard s;

    PairOfObjectsObjectiveCard getExamplePairOfObjectsObjectiveCard(){
        ArrayList<ObjectiveCard>PairOfObjectsObjectiveCard = XMLparser.parseObjectiveCards("objectiveCards.xml");
        return (PairOfObjectsObjectiveCard) PairOfObjectsObjectiveCard.stream().filter(x->x.getID().equals("O100")).findAny().orElse(null);
    }

    StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }

    ResourceCard getExampleResourceCard(String id){
        ArrayList<PlayableCard> ResourceCard = XMLparser.parseResourceCards("resourceCards.xml");
        return (ResourceCard) ResourceCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    PointsGoldCard getExamplePointsGoldCard(String id){
        ArrayList<PlayableCard> PointsGoldCard = XMLparser.parseGoldCards("goldCards.xml");
        return (PointsGoldCard) PointsGoldCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    @BeforeEach
    void setUp(){
        s = getExamplePairOfObjectsObjectiveCard();
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
        ResourceCard resourceCard = getExampleResourceCard("R26");
        ResourceCard resourceCard2 = getExampleResourceCard("R17");
        PointsGoldCard pointsGoldCard = getExamplePointsGoldCard("G49");
        PlayerTable playerTable = new PlayerTable();
        addRequirementsOfGoldCard(playerTable, pointsGoldCard);
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(pointsGoldCard, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(2,s.computePoints(playerTable));

        playerTable = new PlayerTable();
        addRequirementsOfGoldCard(playerTable, pointsGoldCard);
        starterCard = getExampleStarterCard();
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard2, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(pointsGoldCard, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(2,s.computePoints(playerTable));


        playerTable = new PlayerTable();
        addRequirementsOfGoldCard(playerTable, pointsGoldCard);
        ResourceCard resourceCard3 = getExampleResourceCard("R7");
        starterCard = getExampleStarterCard();
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard2, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard3, Corner.UL, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(pointsGoldCard, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(4,s.computePoints(playerTable));

    }

}