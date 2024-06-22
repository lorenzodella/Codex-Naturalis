package it.polimi.ingsw.model.util;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.cards.SpecialObject;
import it.polimi.ingsw.model.cards.objective.*;
import it.polimi.ingsw.model.cards.playable.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class XMLparserTest {

    @Test
    void parseStarterCards() {
        ArrayList<PlayableCard> starterCards = XMLparser.parseStarterCards("/xml/starterCards.xml");
        Corner[] backCorners = new Corner[4];
        backCorners[0] = new Corner();
        backCorners[1] = new Corner();
        Corner[] frontCorners = new Corner[4];
        frontCorners[0] = new Corner(Kingdom.Insect);
        frontCorners[1] = new Corner(Kingdom.Fungi);
        frontCorners[2] = new Corner(Kingdom.Plant);
        frontCorners[3] = new Corner(Kingdom.Animal);
        ArrayList<Kingdom> res = new ArrayList<>();
        res.add(Kingdom.Animal);
        res.add(Kingdom.Insect);
        res.add(Kingdom.Plant);
        StarterCard s = new StarterCard("S85", frontCorners, backCorners, res);
        assertEquals(s, starterCards.stream().filter(x->x.getID().equals("S85")).findAny().orElse(null));
        System.out.println(starterCards);
    }

    @Test
    void parseResourceCards() {
        ArrayList<PlayableCard> resourceCards = XMLparser.parseResourceCards("/xml/resourceCards.xml");
        Corner[] frontCorners = new Corner[4];
        frontCorners[0] = new Corner();
        frontCorners[1] = new Corner();
        frontCorners[2] = new Corner(Kingdom.Plant);
        Corner[] backCorners = new Corner[4];
        backCorners[0] = new Corner();
        backCorners[1] = new Corner();
        backCorners[2] = new Corner();
        backCorners[3] = new Corner();
        Kingdom k = Kingdom.Plant;
        int points = 1;
        ResourceCard s = new ResourceCard("R18", frontCorners, backCorners, k, points);
        //controllo che la carta che ho appena creato sia uguale alla carta che pesco dallo stream di resource card
        assertEquals(s, resourceCards.stream().filter(x->x.getID().equals("R18")).findAny().orElse(null));
        System.out.println(resourceCards);
    }

    @Test
    void parseGoldCards() {

        //object
        ArrayList<PlayableCard> goldCards = XMLparser.parseGoldCards("/xml/goldCards.xml");
        Corner[] objectFrontCorners = new Corner[4];
        objectFrontCorners[0] = new Corner();
        objectFrontCorners[2] = new Corner(SpecialObject.Inkwell);
        objectFrontCorners[3] = new Corner();
        Corner[] objectBackCorners = new Corner[4];
        objectBackCorners[0] = new Corner();
        objectBackCorners[1] = new Corner();
        objectBackCorners[2] = new Corner();
        objectBackCorners[3] = new Corner();
        Kingdom k1 = Kingdom.Plant;
        SpecialObject so = SpecialObject.Inkwell;
        HashMap<Kingdom, Integer> map1 = Kingdom.createEmptyMap();
        map1.put(Kingdom.Plant, 2);
        map1.put(Kingdom.Animal, 1);

        ObjectGoldCard s1 = new ObjectGoldCard("G53", objectFrontCorners, objectBackCorners, k1, map1, so);
        assertEquals(s1, goldCards.stream().filter(x->x.getID().equals("G53")).findAny().orElse(null));
        System.out.println(goldCards);

        //corner
        Corner[] cornerFrontCorners = new Corner[4];
        cornerFrontCorners[0] = new Corner();
        cornerFrontCorners[1] = new Corner();
        cornerFrontCorners[3] = new Corner();
        Corner[] cornerBackCorners = new Corner[4];
        cornerBackCorners[0] = new Corner();
        cornerBackCorners[1] = new Corner();
        cornerBackCorners[2] = new Corner();
        cornerBackCorners[3] = new Corner();
        Kingdom k2 = Kingdom.Fungi;
        HashMap<Kingdom, Integer> map2 = Kingdom.createEmptyMap();
        map2.put(Kingdom.Fungi, 3);
        map2.put(Kingdom.Animal, 1);

        CornerGoldCard s2 = new CornerGoldCard("G44", cornerFrontCorners, cornerBackCorners, k2, map2);
        assertEquals(s2, goldCards.stream().filter(x->x.getID().equals("G44")).findAny().orElse(null));
        System.out.println(goldCards);

        //points
        Corner[] pointsFrontCorners = new Corner[4];
        pointsFrontCorners[0] = new Corner();
        pointsFrontCorners[2] = new Corner(SpecialObject.Inkwell);
        Corner[] pointsBackCorners = new Corner[4];
        pointsBackCorners[0] = new Corner();
        pointsBackCorners[1] = new Corner();
        pointsBackCorners[2] = new Corner();
        pointsBackCorners[3] = new Corner();
        Kingdom k3 = Kingdom.Fungi;
        HashMap<Kingdom, Integer> map3 = Kingdom.createEmptyMap();
        map3.put(Kingdom.Fungi, 3);
        int points = 3;

        PointsGoldCard s3 = new PointsGoldCard("G47", pointsFrontCorners, pointsBackCorners, k3, map3, points);
        assertEquals(s3, goldCards.stream().filter(x->x.getID().equals("G47")).findAny().orElse(null));
        System.out.println(goldCards);
    }

    @Test
    void parseObjectiveCards() {
        //Diagonal
        ArrayList<ObjectiveCard> objectiveCards = XMLparser.parseObjectiveCards("/xml/objectiveCards.xml");
        Kingdom k1 = Kingdom.Insect;
        int cc1 = 3;
        int p1 = 2;
        DiagonalConfigurationObjectiveCard s3 = new DiagonalConfigurationObjectiveCard("O90", p1, k1, cc1);
        assertEquals(s3, objectiveCards.stream().filter(x->x.getID().equals("O90")).findAny().orElse(null));
        System.out.println(objectiveCards);

        //Vertical
        Kingdom kv1 = Kingdom.Animal;
        Kingdom kv2 = Kingdom.Insect;
        int cc2 = 3;
        int p2 = 3;
        VerticalConfigurationObjectiveCard s4 = new VerticalConfigurationObjectiveCard("O94", p2, kv1, kv2, cc2);
        assertEquals(s4, objectiveCards.stream().filter(x->x.getID().equals("O94")).findAny().orElse(null));
        System.out.println(objectiveCards);

        //PairOfObject
        SpecialObject so1 = SpecialObject.Manuscript;
        int p3 = 2;
        PairOfObjectsObjectiveCard s5 = new PairOfObjectsObjectiveCard("O100", p3, so1);
        assertEquals(s5, objectiveCards.stream().filter(x->x.getID().equals("O100")).findAny().orElse(null));
        System.out.println(objectiveCards);

        //TrioOfObject
        int p4 = 3;
        TrioOfObjectsObjectiveCard s6 = new TrioOfObjectsObjectiveCard("O99", p4);
        assertEquals(s6, objectiveCards.stream().filter(x->x.getID().equals("O99")).findAny().orElse(null));
        System.out.println(objectiveCards);

        //TrioOfResource
        Kingdom k2 = Kingdom.Insect;
        int p5 = 2;
        TrioOfResourcesObjectiveCard s7 = new TrioOfResourcesObjectiveCard("O98", p5, k2);
        assertEquals(s7, objectiveCards.stream().filter(x->x.getID().equals("O98")).findAny().orElse(null));
        System.out.println(objectiveCards);


    }

}