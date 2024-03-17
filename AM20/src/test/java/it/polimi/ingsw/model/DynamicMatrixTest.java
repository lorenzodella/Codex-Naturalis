package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicMatrixTest {

    @Test
    void firstElementIsCenter() throws TargetNotPresentException {
        DynamicMatrix<Character> m = new DynamicMatrix<>('a');
        assertArrayEquals(new int[]{1,1}, m.find('a'));
    }

    @Test
    void insertUL() throws TargetNotPresentException, InvalidPositionException {
        DynamicMatrix<Character> m = new DynamicMatrix<>('a');
        m.insert('b','a',0);
        assertArrayEquals(new int[]{1,2}, m.find('a'));
        assertArrayEquals(new int[]{1,1}, m.find('b'));
        assertEquals(3, m.mat.size());
        assertEquals(4, m.mat.get(0).size());
    }

    @Test
    void insertUR() throws TargetNotPresentException, InvalidPositionException {
        DynamicMatrix<Character> m = new DynamicMatrix<>('a');
        m.insert('b','a',1);
        assertArrayEquals(new int[]{2,1}, m.find('a'));
        assertArrayEquals(new int[]{1,2}, m.find('b'));
        assertEquals(4, m.mat.size());
        assertEquals(4, m.mat.get(0).size());
    }

    @Test
    void insertPosInvalid() throws TargetNotPresentException {
        DynamicMatrix<Character> m = new DynamicMatrix<>('a');
        assertThrows(InvalidPositionException.class, ()->m.insert('b','a',5));
    }

    @Test
    void insertTargetNonPresent() throws InvalidPositionException {
        DynamicMatrix<Character> m = new DynamicMatrix<>('a');
        assertThrows(TargetNotPresentException.class, ()->m.insert('b','c',1));
    }

}