package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;

import java.util.LinkedList;

/**
 * Dynamic matrix which changes it size after every insert operation, so that it is always present
 * one empty row above and below, one empty column to the left and to the right
 * @param <T> type of the elements of the matrix
 */
public class DynamicMatrix<T> {
    LinkedList<LinkedList<T>> mat;

    /**
     *
     * @param centerEl element to be placed in the center of the matrix
     */
    public DynamicMatrix(T centerEl){
        mat = new LinkedList<>();
        mat.add(new LinkedList<>());
        mat.get(0).add(centerEl);
        addColumnDX();
        addColumnSX();
        addRowDOWN();
        addRowUP();
    }

    /**
     * Insert an element in the matrix. Position is related to the target object.
     * @param el element to be inserted
     * @param target object near which the element must be inserted
     * @param pos 0 = one cell on the left, 1 = one cell on the right and one above,
     *            2 = one cell on the left and one below, 3 = one cell on the right
     * @throws TargetNotPresentException if target object is not present
     * @throws InvalidPositionException if pos is not a valid value
     */
    public void insert(T el, T target, int pos) throws TargetNotPresentException, InvalidPositionException {
        int[] p = find(target);
        int i = p[0]; int j = p[1];
        switch (pos) {
            case 0:
                putUL(el, i, j);
                break;
            case 1:
                putUR(el, i, j);
                break;
            case 2:
                putDL(el, i, j);
                break;
            case 3:
                putDR(el, i, j);
                break;
            default:
                throw new InvalidPositionException();
        }
    }

    /**
     * Find the coordinates of an element in the matrix.
     * @param el element to be found
     * @return an array of two elements: number of row and number of column
     * @throws TargetNotPresentException if the element is not present
     */
    public int[] find(T el) throws TargetNotPresentException {
        for (int i=0; i<mat.size(); i++) {
            for (int j=0; j<3; j++) {
                if(mat.get(i).get(j)!=null && mat.get(i).get(j).equals(el)){
                    int[] pos = new int[2];
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        throw new TargetNotPresentException();
    }

    /**
     * Insert an element one cell on the left of the given cell.
     * @param el element to be inserted
     * @param i row of given cell
     * @param j column of given cell
     */
    private void putUL(T el, int i, int j) {
        mat.get(i).set(j-1, el);
        if(j-1 == 0)
            addColumnSX();
    }

    /**
     * Insert an element one cell on the right and one above the given cell.
     * @param el element to be inserted
     * @param i row of given cell
     * @param j column of given cell
     */
    private void putUR(T el, int i, int j){
        mat.get(i-1).set(j+1, el);
        if(j+1 == mat.get(0).size()-1)
            addColumnDX();
        if(i-1 == 0)
            addRowUP();
    }

    /**
     * Insert an element one cell on the left and one below the given cell.
     * @param el element to be inserted
     * @param i row of given cell
     * @param j column of given cell
     */
    private void putDL(T el, int i, int j){
        mat.get(i+1).set(j-1, el);
        if(j-1 == 0)
            addColumnSX();
        if(i+1 == mat.size()-1)
            addRowDOWN();
    }

    /**
     * Insert an element one cell on the right the given cell.
     * @param el element to be inserted
     * @param i row of given cell
     * @param j column of given cell
     */
    private void putDR(T el, int i, int j){
        mat.get(i).set(j+1, el);
        if(j+1 == mat.get(0).size()-1)
            addColumnDX();
    }

    /**
     * Add a column to the right.
     */
    private void addColumnDX(){
        for (LinkedList<T> list : mat) {
            list.addLast(null);
        }
    }

    /**
     * Add a column to the left.
     */
    private void addColumnSX(){
        for (LinkedList<T> list : mat) {
            list.addFirst(null);
        }
    }

    /**
     * Add a row above.
     */
    private void addRowUP(){
        LinkedList<T> l = new LinkedList<>();
        for(int i=0; i<mat.get(0).size(); i++){
            l.add(null);
        }
        mat.addFirst(l);
    }

    /**
     * Add a row below.
     */
    private void addRowDOWN(){
        LinkedList<T> l = new LinkedList<>();
        for(int i=0; i<mat.get(0).size(); i++){
            l.add(null);
        }
        mat.addLast(l);
    }

    /**
     * Display the matrix.
     * @return a string representative the matrix
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        for (LinkedList<T> list : mat) {
            for (T t : list) {
                s.append(t).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
