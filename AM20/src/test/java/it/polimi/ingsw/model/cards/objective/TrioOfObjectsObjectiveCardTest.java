package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.PointsGoldCard;
import it.polimi.ingsw.model.cards.playable.ResourceCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.InsertionException;
import it.polimi.ingsw.model.exceptions.InvalidAngleCoveredException;
import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TrioOfObjectsObjectiveCardTest {

    TrioOfObjectsObjectiveCard s;
    TrioOfObjectsObjectiveCard getExampleTrioOfObjectsObjectiveCard(){
        ArrayList<ObjectiveCard> TrioOfObjectsObjectiveCard = XMLparser.parseObjectiveCards("objectiveCards.xml");
        return (TrioOfObjectsObjectiveCard) TrioOfObjectsObjectiveCard.stream().filter(x->x.getID().equals("O99")).findAny().orElse(null);
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
    void setUp(){ s = getExampleTrioOfObjectsObjectiveCard();}

    @Test
    void testComputePoints() throws TargetNotPresentException, InsertionException, InvalidAngleCoveredException, InvalidPositionException {
        StarterCard starterCard = getExampleStarterCard();
        ResourceCard resourceCard = getExampleResourceCard("R26");
        ResourceCard resourceCard2 = getExampleResourceCard("R16");
        PointsGoldCard pointsGoldCard = getExamplePointsGoldCard("G48");
        PlayerTable playerTable = new PlayerTable();
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(pointsGoldCard, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard2, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(3,s.computePoints(playerTable));

        playerTable = new PlayerTable();
        starterCard = getExampleStarterCard();
        ResourceCard resourceCard3 = getExampleResourceCard("R15");
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(pointsGoldCard, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard3, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(0,s.computePoints(playerTable));

        playerTable = new PlayerTable();
        starterCard = getExampleStarterCard();
        ResourceCard resourceCard4 = getExampleResourceCard("R25");
        PointsGoldCard pointsGoldCard2 = getExamplePointsGoldCard("G49");
        PointsGoldCard pointsGoldCard3 = getExamplePointsGoldCard("G69");
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