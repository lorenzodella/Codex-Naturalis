package it.polimi.ingsw.model.util;

import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DynamicMapTest {

    DynamicMap<Character, Character> m;

    @BeforeEach
    void setUp(){
        m = new DynamicMap<>('a', 'a');
    }

    @Test
    void testToString() throws TargetNotPresentException, InvalidPositionException {
        m.insert('b','b','a',0);
        m.insert('c','c','a',1);
        m.insert('d','d','a',2);
        m.insert('e','e','a',3);

        m.insert('g','g','c',0);
        m.insert('f','f','c',1);
        System.out.println(m);
    }

    @Test
    void emptyMapHasNoDimension() throws TargetNotPresentException {
        m.remove('a');
        assertEquals(0, m.width());
        assertEquals(0, m.height());
    }

    @Test
    void firstElementIsCenter() throws TargetNotPresentException {
        assertEquals(new Point(0,0), m.findPos('a'));
    }

    @Test
    void insertUL() throws TargetNotPresentException, InvalidPositionException {
        m.insert('b','b', 'a',DynamicMap.UL);
        assertEquals(new Point(-1,1), m.findPos('b'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.get('a',DynamicMap.UL));
    }

    @Test
    void insertUR() throws TargetNotPresentException, InvalidPositionException {
        m.insert('b','b', 'a',DynamicMap.UR);
        assertEquals(new Point(1,1), m.findPos('b'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.get('a',DynamicMap.UR));
    }

    @Test
    void insertDL() throws TargetNotPresentException, InvalidPositionException {
        m.insert('b','b', 'a',DynamicMap.DL);
        assertEquals(new Point(-1,-1), m.findPos('b'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.get('a',DynamicMap.DL));
    }

    @Test
    void insertDR() throws TargetNotPresentException, InvalidPositionException {
        m.insert('b','b', 'a',DynamicMap.DR);
        assertEquals(new Point(1,-1), m.findPos('b'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.get('a',DynamicMap.DR));
    }

    @Test
    void insertURandDR() throws TargetNotPresentException, InvalidPositionException {
        insertUR();
        m.insert('c','c', 'a',DynamicMap.DR);
        assertEquals(new Point(1,-1), m.findPos('c'));
        assertEquals(3, m.height());
        assertEquals(2, m.width());

        assertEquals('c', m.get('a',DynamicMap.DR));
    }

    @Test
    void getU() throws TargetNotPresentException, InvalidPositionException {
        insertUR();
        m.insert('c','c', 'b',DynamicMap.UL);
        assertEquals('c', m.get('a',DynamicMap.U));
    }

    @Test
    void getD() throws TargetNotPresentException, InvalidPositionException {
        insertDR();
        m.insert('c','c', 'b',DynamicMap.DL);
        assertEquals('c', m.get('a',DynamicMap.D));
    }

    @Test
    void insertPosInvalid() throws TargetNotPresentException {
        assertThrows(InvalidPositionException.class, ()->m.insert('b', 'b','a',5));
    }

    @Test
    void insertTargetNonPresent() throws InvalidPositionException {
        assertThrows(TargetNotPresentException.class, ()->m.insert('b', 'b','c',DynamicMap.UR));
    }

    @Test
    void getElementNotPresent() throws TargetNotPresentException, InvalidPositionException {
        assertNull(m.get('a', DynamicMap.UL));
        assertNull(m.get('a', DynamicMap.UR));
        assertNull(m.get('a', DynamicMap.DL));
        assertNull(m.get('a', DynamicMap.DR));
    }
}