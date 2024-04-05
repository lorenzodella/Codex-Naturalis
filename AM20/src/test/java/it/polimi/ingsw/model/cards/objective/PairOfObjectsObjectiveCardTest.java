package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.playable.*;
import it.polimi.ingsw.model.exceptions.InsertionException;
import it.polimi.ingsw.model.exceptions.InvalidAngleCoveredException;
import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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

    @Test
    void testComputePoints() throws TargetNotPresentException, InsertionException, InvalidAngleCoveredException, InvalidPositionException {
        StarterCard starterCard = getExampleStarterCard();
        ResourceCard resourceCard = getExampleResourceCard("R26");
        ResourceCard resourceCard2 = getExampleResourceCard("R17");
        PointsGoldCard pointsGoldCard = getExamplePointsGoldCard("G49");
        PlayerTable playerTable = new PlayerTable();
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(pointsGoldCard, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(2,s.computePoints(playerTable));

        playerTable = new PlayerTable();
        starterCard = getExampleStarterCard();
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard2, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(pointsGoldCard, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(2,s.computePoints(playerTable));


        playerTable = new PlayerTable();
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