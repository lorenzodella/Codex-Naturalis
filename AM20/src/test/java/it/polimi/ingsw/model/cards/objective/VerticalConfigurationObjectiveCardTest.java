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

class VerticalConfigurationObjectiveCardTest {

    VerticalConfigurationObjectiveCard s;
    VerticalConfigurationObjectiveCard getExampleVerticalConfigurationObjectiveCard(){
        ArrayList<ObjectiveCard> VerticalConfigurationObjectiveCard = XMLparser.parseObjectiveCards("objectiveCards.xml");
        return (VerticalConfigurationObjectiveCard) VerticalConfigurationObjectiveCard.stream().filter(x->x.getID().equals("O94")).findAny().orElse(null);
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
    void setUp(){ s = getExampleVerticalConfigurationObjectiveCard();}

    @Test
    void testComputePoints() throws TargetNotPresentException, InsertionException, InvalidAngleCoveredException, InvalidPositionException, RequirementsNotRespectedException {
        StarterCard starterCard = getExampleStarterCard();
        ResourceCard resourceCard = getExampleResourceCard("R31");
        ResourceCard resourceCard2 = getExampleResourceCard("R33");
        ResourceCard resourceCard3 = getExampleResourceCard("R29");
        PlayerTable playerTable = new PlayerTable();
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.UL, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard2, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard3, Corner.UL, resourceCard.getID(), PlayableCard.FRONT);
        assertEquals(3, s.computePoints(playerTable));

        playerTable = new PlayerTable();
        starterCard = getExampleStarterCard();
        resourceCard = getExampleResourceCard("R31");
        resourceCard2 = getExampleResourceCard("R33");
        ResourceCard resourceCard4 = getExampleResourceCard("R37");
        ResourceCard resourceCard5 = getExampleResourceCard("R34");
        ResourceCard resourceCard6 = getExampleResourceCard("R28");
        ResourceCard resourceCard7 = getExampleResourceCard("R27");
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.UL, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard2, Corner.UR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard4, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard5, Corner.DR, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard6, Corner.UL, resourceCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard7, Corner.UR, resourceCard.getID(), PlayableCard.FRONT);
        assertEquals(6, s.computePoints(playerTable));

        playerTable = new PlayerTable();
        starterCard = getExampleStarterCard();
        resourceCard = getExampleResourceCard("R31");
        resourceCard2 = getExampleResourceCard("R33");
        ResourceCard resourceCard8 = getExampleResourceCard("R40");
        playerTable.insertStarterCard(PlayableCard.FRONT, starterCard);
        playerTable.insertCard(resourceCard, Corner.UL, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard2, Corner.DL, starterCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard8, Corner.DR, resourceCard2.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard4, Corner.DL, resourceCard8.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard6, Corner.UL, resourceCard.getID(), PlayableCard.FRONT);
        playerTable.insertCard(resourceCard7, Corner.DL, resourceCard2.getID(), PlayableCard.FRONT);
        assertEquals(3, s.computePoints(playerTable));



    }

}