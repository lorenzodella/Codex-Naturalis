package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.cards.playable.PointsGoldCard;
import it.polimi.ingsw.model.cards.playable.ResourceCard;
import it.polimi.ingsw.model.cards.playable.StarterCard;
import it.polimi.ingsw.model.exceptions.InvalidAngleCoveredException;
import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.RequirementsNotRespectedException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;
import it.polimi.ingsw.model.util.DynamicMap;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTableTest {

    PlayerTable p;


    StarterCard getExampleStarterCard(){
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("/xml/starterCards.xml");
        return (StarterCard) starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null);
    }

    PointsGoldCard getExamplePointsGoldCard(String id){
        ArrayList<PlayableCard> PointsGoldCard = XMLparser.parseGoldCards("/xml/goldCards.xml");
        return (PointsGoldCard) PointsGoldCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }

    ResourceCard getExampleResourceCard(String id){
        ArrayList<PlayableCard> ResourceCard = XMLparser.parseResourceCards("/xml/resourceCards.xml");
        return (ResourceCard) ResourceCard.stream().filter(x->x.getID().equals(id)).findAny().orElse(null);
    }



    PlayerTable getExamplePlayerTable(){
        return new PlayerTable();
    }

    @BeforeEach
    void setUp() {
        p = getExamplePlayerTable();
    }

    @Test
    void insertStarterCard() throws TargetNotPresentException {
        PlayableCard card = getExampleStarterCard();
        assertEquals(0,p.getStats().getNumberOfResources(Kingdom.Animal));
        assertEquals(0,p.getStats().getNumberOfResources(Kingdom.Insect));
        assertEquals(0,p.getStats().getNumberOfResources(Kingdom.Plant));
        assertEquals(0,p.getStats().getNumberOfResources(Kingdom.Fungi));
        assertEquals(0,p.getStats().getNumberOfObjects(SpecialObject.Quill));
        assertEquals(0,p.getStats().getNumberOfObjects(SpecialObject.Manuscript));
        assertEquals(0,p.getStats().getNumberOfObjects(SpecialObject.Inkwell));
        p.insertStarterCard(PlayableCard.BACK,card);
        assertEquals(1,p.getStats().getNumberOfResources(Kingdom.Animal));
        assertEquals(1,p.getStats().getNumberOfResources(Kingdom.Insect));
        assertEquals(1,p.getStats().getNumberOfResources(Kingdom.Plant));
        assertEquals(0,p.getStats().getNumberOfResources(Kingdom.Fungi));
        assertEquals(0,p.getStats().getNumberOfObjects(SpecialObject.Quill));
        assertEquals(0,p.getStats().getNumberOfObjects(SpecialObject.Manuscript));
        assertEquals(0,p.getStats().getNumberOfObjects(SpecialObject.Inkwell));
        assertEquals(3, p.getMap().numOfElements());
        assertEquals(card, p.getMap().find(card.getID()));
        assertEquals(card, p.getMap().getElement(0,0));
        assertEquals(0, p.getMap().getElement(0,0).getSide());
    }

    @Test
    void insertCard() throws RequirementsNotRespectedException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException {
        PlayableCard card = getExampleStarterCard();
        p.insertStarterCard(PlayableCard.BACK,card);
        PlayableCard card1 = getExamplePointsGoldCard("G78");

        assertThrows(RequirementsNotRespectedException.class, ()->{
            p.insertCard(card1, DynamicMap.UL, card.getID(), PlayableCard.FRONT);
        } );

        PlayableCard card2 = getExampleResourceCard("R7");
        PlayableCard card3 = getExampleResourceCard("R8");

        assertThrows(TargetNotPresentException.class, ()->{
            p.insertCard(card3, DynamicMap.UL, card2.getID(), PlayableCard.FRONT);
        } );

        assertThrows(InvalidPositionException.class, ()->{
            p.insertCard(card2, 6 , card.getID(), PlayableCard.FRONT);
        } );

        assertThrows(InvalidAngleCoveredException.class, ()->{
            p.insertCard(card2, DynamicMap.DL , card.getID(), PlayableCard.FRONT);
        } );
        assertEquals(3, p.getMap().numOfElements());
        assertEquals(card, p.getMap().find(card.getID()));
        assertEquals(card, p.getMap().getElement(0,0));
        assertEquals(PlayableCard.BACK, p.getMap().getElement(0,0).getSide());

        p.insertCard(card2, DynamicMap.UL, card.getID(), PlayableCard.FRONT);

        assertEquals(6, p.getMap().numOfElements());
        assertEquals(card2, p.getMap().find(card2.getID()));
        assertEquals(card2, p.getMap().getElement(-1,+1));
        assertEquals(PlayableCard.FRONT, p.getMap().getElement(-1,+1).getSide());
    }

    @Test
    void updateStats() {
        PlayableCard card = getExampleStarterCard();
        p.insertStarterCard(PlayableCard.BACK,card);
        PlayableCard card2 = getExampleResourceCard("R7");
        //assertEquals(1,p.getStats().getNumberOfResources(Kingdom.Animal));
        assertEquals(1,p.getStats().getNumberOfResources(Kingdom.Insect));
        assertEquals(1,p.getStats().getNumberOfResources(Kingdom.Plant));
        assertEquals(0,p.getStats().getNumberOfResources(Kingdom.Fungi));
        assertEquals(0,p.getStats().getNumberOfObjects(SpecialObject.Quill));
        assertEquals(0,p.getStats().getNumberOfObjects(SpecialObject.Manuscript));
        assertEquals(0,p.getStats().getNumberOfObjects(SpecialObject.Inkwell));

        p.updateStats(card2);

        assertEquals(1,p.getStats().getNumberOfResources(Kingdom.Animal));
        assertEquals(2,p.getStats().getNumberOfResources(Kingdom.Insect));
        assertEquals(1,p.getStats().getNumberOfResources(Kingdom.Plant));
        assertEquals(1,p.getStats().getNumberOfResources(Kingdom.Fungi));
        assertEquals(0,p.getStats().getNumberOfObjects(SpecialObject.Quill));
        assertEquals(1,p.getStats().getNumberOfObjects(SpecialObject.Manuscript));
        assertEquals(0,p.getStats().getNumberOfObjects(SpecialObject.Inkwell));

    }

    @Test
    void numOfCoveredCornerUp2() throws RequirementsNotRespectedException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException {
        PlayableCard card = getExampleStarterCard();
        p.insertStarterCard(PlayableCard.FRONT,card);
        PlayableCard card7 = getExampleResourceCard("R7");
        p.insertCard(card7, DynamicMap.DR, card.getID(), PlayableCard.BACK);
        assertEquals(1, p.numOfCoveredCorner(card7));

        PlayableCard card8 = getExampleResourceCard("R8");
        p.insertCard(card8, DynamicMap.UR, card7.getID(), PlayableCard.BACK);
        assertEquals(1, p.numOfCoveredCorner(card7));

        PlayableCard card9 = getExampleResourceCard("R9");
        p.insertCard(card9, DynamicMap.UL, card8.getID(), PlayableCard.BACK);
        assertEquals(2, p.numOfCoveredCorner(card9));
    }

    @Test
    void numOfCoveredCornerUp3() throws RequirementsNotRespectedException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException {
        PlayableCard card = getExampleStarterCard();
        p.insertStarterCard(PlayableCard.FRONT,card);
        PlayableCard card7 = getExampleResourceCard("R7");
        p.insertCard(card7, DynamicMap.DR, card.getID(), PlayableCard.BACK);

        PlayableCard card8 = getExampleResourceCard("R8");
        p.insertCard(card8, DynamicMap.UR, card7.getID(), PlayableCard.BACK);

        PlayableCard card9 = getExampleResourceCard("R9");

        PlayableCard card10 = getExampleResourceCard("R10");
        p.insertCard(card10, DynamicMap.UR, card8.getID(), PlayableCard.BACK);
        PlayableCard card11 = getExampleResourceCard("R11");
        p.insertCard(card11, DynamicMap.UL, card10.getID(), PlayableCard.BACK);
        p.insertCard(card9, DynamicMap.UR, card.getID(), PlayableCard.BACK);
        assertEquals(3, p.numOfCoveredCorner(card9));

    }

    @Test
    void numOfCoveredCornerUp4() throws RequirementsNotRespectedException, TargetNotPresentException, InvalidAngleCoveredException, InvalidPositionException {
        PlayableCard card = getExampleStarterCard();
        p.insertStarterCard(PlayableCard.FRONT,card);
        PlayableCard card7 = getExampleResourceCard("R7");
        p.insertCard(card7, DynamicMap.DR, card.getID(), PlayableCard.BACK);

        PlayableCard card8 = getExampleResourceCard("R8");
        p.insertCard(card8, DynamicMap.UR, card7.getID(), PlayableCard.BACK);

        PlayableCard card9 = getExampleResourceCard("R9");

        PlayableCard card10 = getExampleResourceCard("R10");
        p.insertCard(card10, DynamicMap.UR, card8.getID(), PlayableCard.BACK);

        PlayableCard card11 = getExampleResourceCard("R11");
        p.insertCard(card11, DynamicMap.UL, card10.getID(), PlayableCard.BACK);


        PlayableCard card12 = getExampleResourceCard("R12");
        p.insertCard(card12, DynamicMap.UL, card11.getID(), PlayableCard.BACK);
        PlayableCard card13 = getExampleResourceCard("R13");
        p.insertCard(card13, DynamicMap.DL, card12.getID(), PlayableCard.BACK);
        p.insertCard(card9, DynamicMap.UR, card.getID(), PlayableCard.BACK);
        assertEquals(4, p.numOfCoveredCorner(card9));

    }


}