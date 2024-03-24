package it.polimi.ingsw.model.util;

import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;
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
        assertArrayEquals(new int[]{0,0}, m.findPos('a'));
    }

    @Test
    void insertUL() throws TargetNotPresentException, InvalidPositionException {
        m.insert('b','b', 'a',DynamicMatrix.L);
        assertArrayEquals(new int[]{0,1}, m.findPos('a'));
        assertArrayEquals(new int[]{0,0}, m.findPos('b'));
        assertEquals(1, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.get('a',DynamicMatrix.L));
    }

    @Test
    void insertUR() throws TargetNotPresentException, InvalidPositionException {
        m.insert('b','b', 'a',DynamicMatrix.UR);
        assertArrayEquals(new int[]{1,0}, m.findPos('a'));
        assertArrayEquals(new int[]{0,1}, m.findPos('b'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.get('a',DynamicMatrix.UR));
    }

    @Test
    void insertDL() throws TargetNotPresentException, InvalidPositionException {
        m.insert('b','b', 'a',DynamicMatrix.DL);
        assertArrayEquals(new int[]{0,1}, m.findPos('a'));
        assertArrayEquals(new int[]{1,0}, m.findPos('b'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.get('a',DynamicMatrix.DL));
    }

    @Test
    void insertDR() throws TargetNotPresentException, InvalidPositionException {
        m.insert('b','b', 'a',3);
        assertArrayEquals(new int[]{0,0}, m.findPos('a'));
        assertArrayEquals(new int[]{0,1}, m.findPos('b'));
        assertEquals(1, m.height());
        assertEquals(2, m.width());

        assertEquals('b', m.get('a',DynamicMatrix.R));
    }

    @Test
    void insertURandDR() throws TargetNotPresentException, InvalidPositionException {
        insertUR();
        m.insert('c','c', 'a',DynamicMatrix.R);
        assertArrayEquals(new int[]{1,0}, m.findPos('a'));
        assertArrayEquals(new int[]{0,1}, m.findPos('b'));
        assertArrayEquals(new int[]{1,1}, m.findPos('c'));
        assertEquals(2, m.height());
        assertEquals(2, m.width());

        assertEquals('c', m.get('a',DynamicMatrix.R));
    }
    
    @Test
    void getU() throws TargetNotPresentException, InvalidPositionException {
        insertUR();
        m.insert('c','c', 'b',DynamicMatrix.L);
        assertEquals('c', m.get('a',DynamicMatrix.U));
    }

    @Test
    void getD() throws TargetNotPresentException, InvalidPositionException {
        insertDR();
        m.insert('c','c', 'b',DynamicMatrix.DL);
        assertEquals('c', m.get('a',DynamicMatrix.D));
    }

    @Test
    void insertPosInvalid() throws TargetNotPresentException {
        assertThrows(InvalidPositionException.class, ()->m.insert('b', 'b','a',5));
    }

    @Test
    void insertTargetNonPresent() throws InvalidPositionException {
        assertThrows(TargetNotPresentException.class, ()->m.insert('b', 'b','c',DynamicMatrix.UR));
    }

    @Test
    void getElementNotPresent() throws TargetNotPresentException, InvalidPositionException {
        assertNull(m.get('a', DynamicMatrix.L));
        assertNull(m.get('a', DynamicMatrix.UR));
        assertNull(m.get('a', DynamicMatrix.DL));
        assertNull(m.get('a', DynamicMatrix.R));
    }

}