package it.polimi.ingsw.model.cards.playable;

import it.polimi.ingsw.model.cards.Corner;
import it.polimi.ingsw.model.cards.Kingdom;
import it.polimi.ingsw.model.util.XMLparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ObjectGoldCardTest {
    ObjectGoldCard s;

    ObjectGoldCard getExampleObjectGoldCard(){
        ArrayList<PlayableCard> ObjectGoldCard = XMLparser.parseGoldCards("goldCards.xml");
        return (ObjectGoldCard) ObjectGoldCard.stream().filter(x->x.getID().equals("G42")).findAny().orElse(null);
    }

    @BeforeEach
    void setUp(){
        s = getExampleObjectGoldCard();
    }

    @Test
    void getKingdoms(){
        HashMap<Kingdom, Integer> map = Kingdom.createEmptyMap();
        assertEquals(map, s.getKingdoms());

        //turn card to the back
        s.setSide(PlayableCard.BACK);
        map.put(Kingdom.Fungi,1);
        assertEquals(map, s.getKingdoms());
    }


}