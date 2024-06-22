package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.PlayerTable;
import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.objective.DiagonalConfigurationObjectiveCard;
import it.polimi.ingsw.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.model.cards.playable.CornerGoldCard;
import it.polimi.ingsw.model.cards.playable.GoldCard;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.*;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalConfigurationObjectiveCardTest {
    DiagonalConfigurationObjectiveCard s;

    DiagonalConfigurationObjectiveCard getExampleDiagonalConfigurationObjectiveCard(){
        ArrayList<ObjectiveCard> DiagonalConfigurationObjectiveCard = XMLparser.parseObjectiveCards("/xml/objectiveCards.xml");
        return (DiagonalConfigurationObjectiveCard) DiagonalConfigurationObjectiveCard.stream().filter(x->x.getID().equals("O90")).findAny().orElse(null);
    }

    CornerGoldCard getExampleCornerGoldCard(String id){
        ArrayList<PlayableCard> CornerGoldCard = XMLparser.parseGoldCards("/xml/goldCards.xml");
        return (CornerGoldCard) CornerGoldCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    List<PlayableCard> getKingdomGoldCard(Kingdom k){
        ArrayList<PlayableCard> CornerGoldCard = XMLparser.parseGoldCards("/xml/goldCards.xml");
        return  CornerGoldCard.stream()
                .filter(x->x.getCardKingdom().equals(k))
                .collect(Collectors.toList());
    }

    StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("/xml/starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }

    @BeforeEach
    void setUp(){
        s = getExampleDiagonalConfigurationObjectiveCard();
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
        List<PlayableCard> list = getKingdomGoldCard(s.getKingdom());
        PlayerTable playerTable = new PlayerTable();
        playerTable.insertStarterCard(PlayableCard.BACK, starterCard);
        addRequirementsOfGoldCard(playerTable, (GoldCard) list.get(0));
        playerTable.insertCard(list.get(0), Corner.UL, starterCard.getID(), PlayableCard.BACK);
        addRequirementsOfGoldCard(playerTable, (GoldCard) list.get(1));
        playerTable.insertCard(list.get(1), Corner.UL, list.get(0).getID(), PlayableCard.BACK);
        addRequirementsOfGoldCard(playerTable, (GoldCard) list.get(2));
        playerTable.insertCard(list.get(2), Corner.UL, list.get(1).getID(), PlayableCard.BACK);
        assertEquals(2,s.computePoints(playerTable));

        addRequirementsOfGoldCard(playerTable, (GoldCard) list.get(3));
        playerTable.insertCard(list.get(3), Corner.UL, list.get(2).getID(), PlayableCard.BACK);
        addRequirementsOfGoldCard(playerTable, (GoldCard) list.get(4));
        playerTable.insertCard(list.get(4), Corner.UL, list.get(3).getID(), PlayableCard.BACK);
        assertEquals(2,s.computePoints(playerTable));

        addRequirementsOfGoldCard(playerTable, (GoldCard) list.get(5));
        playerTable.insertCard(list.get(5), Corner.UL, list.get(4).getID(), PlayableCard.BACK);
        assertEquals(4,s.computePoints(playerTable));

    }


}