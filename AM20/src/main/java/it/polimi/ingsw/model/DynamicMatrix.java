package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;

import java.util.LinkedList;

/**
 * Dynamic matrix which changes it size after every insert operation, so that it is always present
 * one empty row above and below, one empty column to the left and to the right
 * @param <K> type of the elements key of the matrix
 * @param <T> type of the elements of the matrix
 */
public class DynamicMatrix<K,T> {

    private static class MatrixElement<K,T> {
        K key;
        T value;
        private MatrixElement(K key, T value){
            this.key = key;
            this.value = value;
        }
    }
    private final LinkedList<LinkedList<MatrixElement<K,T>>> mat;

    /**
     *
     * @param key key of the element
     * @param centerEl element to be placed in the center of the matrix
     */
    public DynamicMatrix(K key, T centerEl) {
        mat = new LinkedList<>();
        mat.add(new LinkedList<>());
        mat.get(0).add(new MatrixElement<>(key, centerEl));
        addColumnDX();
        addColumnSX();
        addRowDOWN();
        addRowUP();
    }

    /**
     *
     * @return number of columns of the matrix
     */
    public int width(){
        return mat.get(0).size();
    }

    /**
     *
     * @return number of rows of the matrix
     */
    public int height(){
        return mat.size();
    }

    /**
     * Insert an element in the matrix. Position is related to the target object.
     * @param key key of the element
     * @param el element to be inserted
     * @param targetKey object near which the element must be inserted
     * @param pos 0 = one cell on the left, 1 = one cell on the right and one above,
     *            2 = one cell on the left and one below, 3 = one cell on the right
     * @throws TargetNotPresentException if target object is not present
     * @throws InvalidPositionException if pos is not a valid value
     */
    public void insert(K key, T el, K targetKey, int pos) throws TargetNotPresentException, InvalidPositionException {
        int[] p = find(targetKey);
        int i = p[0]; int j = p[1];
        switch (pos) {
            case 0:
                putL(new MatrixElement<>(key, el), i, j);
                break;
            case 1:
                putUR(new MatrixElement<>(key, el), i, j);
                break;
            case 2:
                putDL(new MatrixElement<>(key, el), i, j);
                break;
            case 3:
                putR(new MatrixElement<>(key, el), i, j);
                break;
            default:
                throw new InvalidPositionException();
        }
    }

    /**
     * Find the coordinates of an element in the matrix.
     * @param targetKey key of the element to be found
     * @return an array of two elements: number of row and number of column of the found element
     * @throws TargetNotPresentException if no element with that key is not present
     */
    public int[] find(K targetKey) throws TargetNotPresentException {
        for (int i=0; i<mat.size(); i++) {
            for (int j=0; j<3; j++) {
                if(mat.get(i).get(j)!=null && mat.get(i).get(j).key.equals(targetKey)){
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
    private void putL(MatrixElement<K,T> el, int i, int j) {
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
    private void putUR(MatrixElement<K,T> el, int i, int j){
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
    private void putDL(MatrixElement<K,T> el, int i, int j){
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
    private void putR(MatrixElement<K,T> el, int i, int j){
        mat.get(i).set(j+1, el);
        if(j+1 == mat.get(0).size()-1)
            addColumnDX();
    }

    /**
     * Add a column to the right.
     */
    private void addColumnDX(){
        for (LinkedList<MatrixElement<K, T>> list : mat) {
            list.addLast(null);
        }
    }

    /**
     * Add a column to the left.
     */
    private void addColumnSX(){
        for (LinkedList<MatrixElement<K, T>> list : mat) {
            list.addFirst(null);
        }
    }

    /**
     * Add a row above.
     */
    private void addRowUP(){
        LinkedList<MatrixElement<K, T>> l = new LinkedList<>();
        for(int i=0; i<mat.get(0).size(); i++){
            l.add(null);
        }
        mat.addFirst(l);
    }

    /**
     * Add a row below.
     */
    private void addRowDOWN(){
        LinkedList<MatrixElement<K, T>> l = new LinkedList<>();
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
        for (LinkedList<MatrixElement<K, T>> list : mat) {
            for (MatrixElement<K,T> t : list) {
                s.append(t.key).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
