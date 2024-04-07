package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.ResourceCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TrioOfResourcesObjectiveCardTest {

    TrioOfResourcesObjectiveCard s;
    TrioOfResourcesObjectiveCard getExampleTrioOfResourcesObjectiveCard(){
        ArrayList<ObjectiveCard> TrioOfResourcesObjectiveCard = XMLparser.parseObjectiveCards("objectiveCards.xml");
        return (TrioOfResourcesObjectiveCard) TrioOfResourcesObjectiveCard.stream().filter(x->x.getID().equals("O97")).findAny().orElse(null);
    }

    StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }

    ResourceCard getExampleResourceCard(String id){
        ArrayList<PlayableCard> ResourceCard = XMLparser.parseResourceCards("resourceCards.xml");
        return (ResourceCard) ResourceCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    @BeforeEach
    void setUp(){ s = getExampleTrioOfResourcesObjectiveCard();}

    @Test
    void testComputePoints() throws TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException {
        StarterCard starterCard = getExampleStarterCard();
        ResourceCard resourceCard = getExampleResourceCard("R21");
        ResourceCard resourceCard2 = getExampleResourceCard("R25");
        PlayerTable playerTable = new PlayerTable();
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard2, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(2, s.computePoints(playerTable));

        playerTable = new PlayerTable();
        starterCard = getExampleStarterCard();
        ResourceCard resourceCard3 = getExampleResourceCard("R22");
        ResourceCard resourceCard4 = getExampleResourceCard("R23");
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard3, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard4, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(2, s.computePoints(playerTable));

        playerTable = new PlayerTable();
        starterCard = getExampleStarterCard();
        ResourceCard resourceCard5 = getExampleResourceCard("R24");
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard3, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard4, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard5, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(4, s.computePoints(playerTable));

        playerTable = new PlayerTable();
        starterCard = getExampleStarterCard();
        ResourceCard resourceCard6 = getExampleResourceCard("R32");
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard5, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard6, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(0, s.computePoints(playerTable));

        playerTable = new PlayerTable();
        starterCard = getExampleStarterCard();
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard2, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard3, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(2, s.computePoints(playerTable));

    }

}