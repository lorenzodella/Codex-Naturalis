package it.polimi.ingsw.model.util;

import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;
import it.polimi.ingsw.model.util.DynamicMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicMatrixTest {
    DynamicMatrix<Character,Character> m;

    @BeforeEach
    void setUp(){
        m = new DynamicMatrix<>('a', 'a');
    }

    @Test
    void firstElementIsCenter() throws TargetNotPresentException {
        assertArrayEquals(new int[]{0,0}, m.find('a'));
    }

    @Test
    void insertUL() throws TargetNotPresentException, InvalidPositionException {
        m.insert('b','b', 'a',0);
        assertArrayEquals(new int[]{0,1}, m.find('a'));
        assertArrayEquals(new int[]{0,0}, m.find('b'));
        assertEquals(1, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.get('a',0));
    }

    @Test
    void insertUR() throws TargetNotPresentException, InvalidPositionException {
        m.insert('b','b', 'a',1);
        assertArrayEquals(new int[]{1,0}, m.find('a'));
        assertArrayEquals(new int[]{0,1}, m.find('b'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.get('a',1));
    }

    @Test
    void insertDL() throws TargetNotPresentException, InvalidPositionException {
        m.insert('b','b', 'a',2);
        assertArrayEquals(new int[]{0,1}, m.find('a'));
        assertArrayEquals(new int[]{1,0}, m.find('b'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.get('a',2));
    }

    @Test
    void insertDR() throws TargetNotPresentException, InvalidPositionException {
        m.insert('b','b', 'a',3);
        assertArrayEquals(new int[]{0,0}, m.find('a'));
        assertArrayEquals(new int[]{0,1}, m.find('b'));
        assertEquals(1, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.get('a',3));
    }

    @Test
    void insertURandDR() throws TargetNotPresentException, InvalidPositionException {
        insertUR();
        m.insert('c','c', 'a',3);
        assertArrayEquals(new int[]{1,0}, m.find('a'));
        assertArrayEquals(new int[]{0,1}, m.find('b'));
        assertArrayEquals(new int[]{1,1}, m.find('c'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('c', m.get('a',3));
    }


    @Test
    void insertPosInvalid() throws TargetNotPresentException {
        assertThrows(InvalidPositionException.class, ()->m.insert('b', 'b','a',5));
    }

    @Test
    void insertTargetNonPresent() throws InvalidPositionException {
        assertThrows(TargetNotPresentException.class, ()->m.insert('b', 'b','c',1));
    }

    @Test
    void getElementNotPresent() throws TargetNotPresentException, InvalidPositionException {
        assertNull(m.get('a', 0));
        assertNull(m.get('a', 1));
        assertNull(m.get('a', 2));
        assertNull(m.get('a', 3));
    }

}