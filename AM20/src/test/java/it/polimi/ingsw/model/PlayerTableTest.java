package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTableTest {

    PlayerTable p;


    StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }

    PlayerTable getExamplePlayerTable(){
        return new PlayerTable();
    }

    @BeforeEach
    void setUp() {
        p = getExamplePlayerTable();
    }

    @Test
    void insertStarterCard() {
    }

    @Test
    void insertCard() {
    }

    @Test
    void updateStats() {
    }

    @Test
    void numOfCoveredCorner() {
    }

    @Test
    void findDiagonalConfiguration() {
    }

    @Test
    void findVerticalConfiguration() {
    }
}