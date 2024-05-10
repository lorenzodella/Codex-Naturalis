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
        m = new DynamicMap<>('A', 'a');
    }

    @Test
    void testToString() throws TargetNotPresentException, InvalidPositionException {
        m.insert('B','b','A',0);
        m.insert('C','c','A',1);
        m.insert('D','d','A',2);
        m.insert('E','e','A',3);

        m.insert('G','g','C',0);
        m.insert('F','f','C',1);
        System.out.println(m);
    }

    @Test
    void emptyMapHasNoDimension() throws TargetNotPresentException {
        m.remove('A');
        assertEquals(0, m.width());
        assertEquals(0, m.height());
    }

    @Test
    void firstElementIsCenter() throws TargetNotPresentException {
        assertEquals(new Point(0,0), m.findPos('A'));
    }

    @Test
    void insertUL() throws TargetNotPresentException, InvalidPositionException {
        m.insert('B','b', 'A',DynamicMap.UL);
        assertEquals(new Point(-1,1), m.findPos('B'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.getElementAt('A',DynamicMap.UL));
    }

    @Test
    void insertUR() throws TargetNotPresentException, InvalidPositionException {
        m.insert('B','b', 'A',DynamicMap.UR);
        assertEquals(new Point(1,1), m.findPos('B'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.getElementAt('A',DynamicMap.UR));
    }

    @Test
    void insertDL() throws TargetNotPresentException, InvalidPositionException {
        m.insert('B','b', 'A',DynamicMap.DL);
        assertEquals(new Point(-1,-1), m.findPos('B'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.getElementAt('A',DynamicMap.DL));
    }

    @Test
    void insertDR() throws TargetNotPresentException, InvalidPositionException {
        m.insert('B','b', 'A',DynamicMap.DR);
        assertEquals(new Point(1,-1), m.findPos('B'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.getElementAt('A',DynamicMap.DR));
    }

    @Test
    void insertURandDR() throws TargetNotPresentException, InvalidPositionException {
        insertUR();
        m.insert('C','c', 'A',DynamicMap.DR);
        assertEquals(new Point(1,-1), m.findPos('C'));
        assertEquals(3, m.height());
        assertEquals(2, m.width());

        assertEquals('c', m.getElementAt('A',DynamicMap.DR));
    }

    @Test
    void getU() throws TargetNotPresentException, InvalidPositionException {
        insertUR();
        m.insert('C','c', 'B',DynamicMap.UL);
        assertEquals('c', m.getElementAt('A',DynamicMap.U));
    }

    @Test
    void getD() throws TargetNotPresentException, InvalidPositionException {
        insertDR();
        m.insert('C','c', 'B',DynamicMap.DL);
        assertEquals('c', m.getElementAt('A',DynamicMap.D));
    }

    @Test
    void insertPosInvalid() throws TargetNotPresentException {
        assertThrows(InvalidPositionException.class, ()->m.insert('B', 'b','A',5));
    }

    @Test
    void insertTargetNonPresent() throws InvalidPositionException {
        assertThrows(TargetNotPresentException.class, ()->m.insert('B', 'b','C',DynamicMap.UR));
    }

    @Test
    void getElementNotPresent() throws TargetNotPresentException, InvalidPositionException {
        assertNull(m.getElementAt('A', DynamicMap.UL));
        assertNull(m.getElementAt('A', DynamicMap.UR));
        assertNull(m.getElementAt('A', DynamicMap.DL));
        assertNull(m.getElementAt('A', DynamicMap.DR));
    }
}