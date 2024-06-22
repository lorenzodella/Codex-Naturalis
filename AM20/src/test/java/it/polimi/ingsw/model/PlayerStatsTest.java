package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.PointsGoldCard;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatsTest {

    PlayerStats p;

    PointsGoldCard getExamplePointsGoldCard(String id){
        ArrayList<PlayableCard> PointsGoldCard = XMLparser.parseGoldCards("/xml/goldCards.xml");
        return (PointsGoldCard) PointsGoldCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    @BeforeEach
    void setUp(){ p = new PlayerStats();}

    @Test
    void checkRequirements(){
        PointsGoldCard pointsGoldCard = getExamplePointsGoldCard("G60");

        p.addKingdom(Kingdom.Plant);
        p.addKingdom(Kingdom.Plant);
        p.addKingdom(Kingdom.Plant);
        p.addKingdom(Kingdom.Plant);
        p.addKingdom(Kingdom.Plant);

        assertTrue(p.checkRequirements(pointsGoldCard.getRequirements()));

    }

}