package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidPlayingException;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.objective.DiagonalConfigurationObjectiveCard;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.*;
import it.polimi.ingsw.model.exceptions.InvalidAngleCoveredException;
import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.RequirementsNotRespectedException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player p;

    StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }

    PointsGoldCard getExamplePointsGoldCard(String id){
        ArrayList<PlayableCard> PointsGoldCard = XMLparser.parseGoldCards("goldCards.xml");
        return (PointsGoldCard) PointsGoldCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    DiagonalConfigurationObjectiveCard getExampleDiagonalConfigurationObjectiveCard(String id){
        ArrayList<ObjectiveCard> DiagonalConfigurationObjectiveCard = XMLparser.parseObjectiveCards("objectiveCards.xml");
        return (DiagonalConfigurationObjectiveCard) DiagonalConfigurationObjectiveCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    void addRequirementsOfGoldCard(PlayerTable playerTable, GoldCard gc){
        for(Map.Entry<Kingdom, Integer> e : gc.getRequirements().entrySet()) {
            for (int i = 0; i < e.getValue(); i++) {
                playerTable.getStats().addKingdom(e.getKey());
            }
        }
    }

    @BeforeEach
    void setUp() throws InvalidPlayingException {
        p = new Player("ireneer");

        StarterCard starterCard = getExampleStarterCard();
        p.setStarterCard(starterCard);
        p.positionStarterCard(PlayableCard.FRONT);
        for(Kingdom k : Kingdom.values()){
            assertEquals(1, p.getTable().getStats().getNumberOfResources(k));
        }

        LinkedList<PlayableCard> list = new LinkedList<>();
        list.add(getExamplePointsGoldCard("G48"));
        list.add(getExamplePointsGoldCard("G49"));
        list.add(getExamplePointsGoldCard("G50"));
        p.drawInitialPlayableCard(list);

        DiagonalConfigurationObjectiveCard diagonalConfigurationObjectiveCard1 = getExampleDiagonalConfigurationObjectiveCard("O87");
        DiagonalConfigurationObjectiveCard diagonalConfigurationObjectiveCard2 = getExampleDiagonalConfigurationObjectiveCard("O88");

        ObjectiveCard[] vet = new ObjectiveCard[2];
        vet[0] = diagonalConfigurationObjectiveCard1;
        vet[1] = diagonalConfigurationObjectiveCard2;
        p.setSecretObjective(vet);
        p.chooseObjectiveCard(0);

        assertThrows(InvalidPlayingException.class, ()->p.positionStarterCard(PlayableCard.FRONT));
        assertThrows(InvalidPlayingException.class, ()->p.chooseObjectiveCard(1));

    }

    @Test
    void addPointsOK() throws RequirementsNotRespectedException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException {
        StarterCard starterCard = getExampleStarterCard();
        addRequirementsOfGoldCard(p.getTable(), (GoldCard) p.getCards().get(2));
        p.playCard(2,2, starterCard.getID(), PlayableCard.FRONT);
        assertEquals(5,p.getScore());
    }

    @Test
    void addPointsKO() throws RequirementsNotRespectedException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException {
        StarterCard starterCard = getExampleStarterCard();
        addRequirementsOfGoldCard(p.getTable(), (GoldCard) p.getCards().get(2));
        p.playCard(2,2, starterCard.getID(), PlayableCard.BACK);
        assertEquals(0,p.getScore());
    }

    @Test
    void computeSecretObjective() throws RequirementsNotRespectedException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException {
        StarterCard starterCard = getExampleStarterCard();
        PointsGoldCard pointsGoldCard1 = getExamplePointsGoldCard("G48");
        PointsGoldCard pointsGoldCard2 = getExamplePointsGoldCard("G49");
        addRequirementsOfGoldCard(p.getTable(), (GoldCard) p.getCards().get(0));
        p.playCard(0,2, starterCard.getID(), PlayableCard.BACK);
        addRequirementsOfGoldCard(p.getTable(), (GoldCard) p.getCards().get(0));
        p.playCard(0,2, pointsGoldCard1.getID(), PlayableCard.BACK);
        addRequirementsOfGoldCard(p.getTable(), (GoldCard) p.getCards().get(0));
        p.playCard(0,2, pointsGoldCard2.getID(), PlayableCard.BACK);
        p.computeSecretObjective();
        assertEquals(2,p.getScore());
    }

    @Test
    void computeCommonObjective() throws RequirementsNotRespectedException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException {
        StarterCard starterCard = getExampleStarterCard();
        PointsGoldCard pointsGoldCard1 = getExamplePointsGoldCard("G48");
        PointsGoldCard pointsGoldCard2 = getExamplePointsGoldCard("G49");
        addRequirementsOfGoldCard(p.getTable(), (GoldCard) p.getCards().get(0));
        p.playCard(0,2, starterCard.getID(), PlayableCard.BACK);
        addRequirementsOfGoldCard(p.getTable(), (GoldCard) p.getCards().get(0));
        p.playCard(0,2, pointsGoldCard1.getID(), PlayableCard.BACK);
        addRequirementsOfGoldCard(p.getTable(), (GoldCard) p.getCards().get(0));
        p.playCard(0,2, pointsGoldCard2.getID(), PlayableCard.BACK);
        p.computeCommonObjective(getExampleDiagonalConfigurationObjectiveCard("O87"));
        assertEquals(2,p.getScore());
    }

}