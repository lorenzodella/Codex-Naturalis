package it.polimi.ingsw.model.util;

import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;

import java.util.LinkedList;

/**
 * Dynamic matrix which changes it size before every insert operation
 * in order to use as little space as possible
 * @param <K> type of the elements key of the matrix
 * @param <T> type of the elements of the matrix
 */
public class DynamicMatrix<K,T> {
    public static final int L = 0;
    public static final int UR = 1;
    public static final int DL = 2;
    public static final int R = 3;

    public static final int U = 4;
    public static final int D = 5;

    private static class MatrixElement<K,T> {
        K key;
        T value;
        int order;
        static int num = 0;
        private MatrixElement(K key, T value){
            this.key = key;
            this.value = value;
            order = num;
            num++;
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
    }

    public int numOfElements(){
        return MatrixElement.num;
    }

    /**
     * This method returns the number of columns that are in the matrix
     * @return number of columns of the matrix
     */
    public int width(){
        return mat.get(0).size();
    }

    /**
     * This method returns the number of rows that are in the matrix
     * @return number of rows of the matrix
     */
    public int height(){
        return mat.size();
    }

    /**
     * Insert an element in the matrix. Position is related to the target object.
     * @param key key of the element
     * @param el element to be inserted
     * @param targetKey key of the object near which the element must be inserted
     * @param pos 0 = one cell on the left, 1 = one cell on the right and one above,
     *            2 = one cell on the left and one below, 3 = one cell on the right
     * @throws TargetNotPresentException if target object is not present
     * @throws InvalidPositionException if pos is not a valid value
     */
    public void insert(K key, T el, K targetKey, int pos) throws TargetNotPresentException, InvalidPositionException {
        int[] p = findPos(targetKey);
        int i = p[0]; int j = p[1];
        switch (pos) {
            case L:
                putLeft(new MatrixElement<>(key, el), i, j);
                break;
            case UR:
                putUpRight(new MatrixElement<>(key, el), i, j);
                break;
            case DL:
                putDownLeft(new MatrixElement<>(key, el), i, j);
                break;
            case R:
                putRight(new MatrixElement<>(key, el), i, j);
                break;
            default:
                throw new InvalidPositionException();
        }
    }

    /**
     * Get the element at those coordinates
     * @param i row
     * @param j column
     * @return the element
     */
    public T getElementAt(int i, int j){
        return mat.get(i).get(j).value;
    }

    /**
     * Get the element at a certain position relative to a particular object
     * @param targetKey key of the object near which the element is
     * @param pos 0 = one cell on the left, 1 = one cell on the right and one above,
     *            2 = one cell on the left and one below, 3 = one cell on the right,
     *            4 = one cell above, 5 = one cell below
     * @return element at that position
     * @throws TargetNotPresentException if target object is not present
     * @throws InvalidPositionException if pos is not a valid value
     */
    public T get(K targetKey, int pos) throws TargetNotPresentException, InvalidPositionException {
        int[] p = findPos(targetKey);
        int i = p[0]; int j = p[1];
        MatrixElement<K,T> tmp;
        switch (pos) {
            case L:
                tmp = getLeft(i, j);
                break;
            case UR:
                tmp = getUpRight(i, j);
                break;
            case DL:
                tmp = getDownLeft(i, j);
                break;
            case R:
                tmp = getRight(i, j);
                break;
            case U:
                tmp = getUp(i, j);
                break;
            case D:
                tmp = getDown(i, j);
                break;
            default:
                throw new InvalidPositionException();
        }
        return tmp!=null ? tmp.value : null;
    }

    /**
     * Find the coordinates of an element in the matrix.
     * @param targetKey key of the element to be found
     * @return an array of two elements: number of row and number of column of the found element
     * @throws TargetNotPresentException if no element with that key is not present
     */
    public int[] findPos(K targetKey) throws TargetNotPresentException {
        for (int i=0; i<mat.size(); i++) {
            for (int j=0; j<mat.get(0).size(); j++) {
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
     * Get the element in the matrix with the given key.
     * @param targetKey key of the element to be found
     * @return the element
     * @throws TargetNotPresentException if no element with that key is not present
     */
    public T find(K targetKey) throws TargetNotPresentException {
        for (int i=0; i<mat.size(); i++) {
            for (int j=0; j<mat.get(0).size(); j++) {
                if(mat.get(i).get(j)!=null && mat.get(i).get(j).key.equals(targetKey)){
                    return mat.get(i).get(j).value;
                }
            }
        }
        throw new TargetNotPresentException();
    }

    /**
     * Remove the element in the matrix with the given key.
     * @param targetKey key of the element to be removed
     * @throws TargetNotPresentException if no element with that key is not present
     */
    public void remove(K targetKey) throws TargetNotPresentException {
        for (int i=0; i<mat.size(); i++) {
            for (int j=0; j<mat.get(0).size(); j++) {
                if(mat.get(i).get(j)!=null && mat.get(i).get(j).key.equals(targetKey)){
                    mat.get(i).set(j, null);
                    MatrixElement.num--;
                    return;
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
    private void putLeft(MatrixElement<K,T> el, int i, int j) {
        if(j == 0) addColumnLeft();
        else j--;
        mat.get(i).set(j, el);
    }

    /**
     * Insert an element one cell on the right and one above the given cell.
     * @param el element to be inserted
     * @param i row of given cell
     * @param j column of given cell
     */
    private void putUpRight(MatrixElement<K,T> el, int i, int j){
        if(j == mat.get(0).size()-1) addColumnRight();
        if(i == 0) addRowUp();
        else i--;
        mat.get(i).set(j+1, el);
    }

    /**
     * Insert an element one cell on the left and one below the given cell.
     * @param el element to be inserted
     * @param i row of given cell
     * @param j column of given cell
     */
    private void putDownLeft(MatrixElement<K,T> el, int i, int j){
        if(j == 0) addColumnLeft();
        else j--;
        if(i == mat.size()-1) addRowDown();
        mat.get(i+1).set(j, el);
    }

    /**
     * Insert an element one cell on the right the given cell.
     * @param el element to be inserted
     * @param i row of given cell
     * @param j column of given cell
     */
    private void putRight(MatrixElement<K,T> el, int i, int j){
        if(j == mat.get(0).size()-1) addColumnRight();
        mat.get(i).set(j+1, el);
    }

    /**
     * Get the matrix element one cell on the left of the given cell.
     * @param i row of given cell
     * @param j column of given cell
     * @return the element at that position
     */
    private MatrixElement<K,T> getLeft(int i, int j){
        if(j == 0) return null;
        return mat.get(i).get(j-1);
    }

    /**
     * Get the matrix element on the right and one above the given cell.
     * @param i row of given cell
     * @param j column of given cell
     * @return the element at that position
     */
    private MatrixElement<K,T> getUpRight(int i, int j){
        if(j == mat.get(0).size()-1) return null;
        if(i == 0) return null;
        return mat.get(i-1).get(j+1);
    }

    /**
     * Get the matrix element one cell on the left and one below the given cell.
     * @param i row of given cell
     * @param j column of given cell
     * @return the element at that position
     */
    private MatrixElement<K,T> getDownLeft(int i, int j){
        if(j == 0) return null;
        if(i == mat.size()-1) return null;
        return mat.get(i+1).get(j-1);
    }

    /**
     * Get the matrix element one cell above the given cell
     * @param i row of given cell
     * @param j column of given cell
     * @return the element at that position
     */
    private MatrixElement<K,T> getUp(int i, int j){
        if(i == 0) return null;
        return  mat.get(i-1).get(j);
    }

    /**
     * Get the matrix element one cell below the given cell
     * @param i row of given cell
     * @param j column of given cell
     * @return the element at that position
     */
    private MatrixElement<K,T> getDown(int i, int j){
        if(i == mat.size()-1) return null;
        return  mat.get(i+1).get(j);
    }

    /**
     * Get the matrix element one cell to the right of the given cell
     * @param i row of given cell
     * @param j column of given cell
     * @return the element at that position
     */
    private MatrixElement<K,T> getRight(int i, int j){
        if(j == mat.get(0).size()-1) return null;
        return  mat.get(i).get(j+1);
    }

    /**
     * Add a column to the right.
     */
    private void addColumnRight(){
        for (LinkedList<MatrixElement<K, T>> list : mat) {
            list.addLast(null);
        }
    }

    /**
     * Add a column to the left.
     */
    private void addColumnLeft(){
        for (LinkedList<MatrixElement<K, T>> list : mat) {
            list.addFirst(null);
        }
    }

    /**
     * Add a row above.
     */
    private void addRowUp(){
        LinkedList<MatrixElement<K, T>> l = new LinkedList<>();
        for(int i=0; i<mat.get(0).size(); i++){
            l.add(null);
        }
        mat.addFirst(l);
    }

    /**
     * Add a row below.
     */
    private void addRowDown(){
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
                if(t!=null)
                    s.append(t.key).append(" ");
                else
                    s.append("    ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
