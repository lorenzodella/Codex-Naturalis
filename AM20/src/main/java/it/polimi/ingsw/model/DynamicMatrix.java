package it.polimi.ingsw.model;

import java.util.LinkedList;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;

public class DynamicMatrix<T> {
    LinkedList<LinkedList<T>> mat;

    public DynamicMatrix(T centerEl){
        mat = new LinkedList<>();
        mat.add(new LinkedList<>());
        mat.get(0).add(centerEl);
        addColumnDX();
        addColumnSX();
        addRowDOWN();
        addRowUP();
    }

    public void insert(T el, T target, int pos) throws TargetNotPresentException {
        int[] p = find(target);
        int i = p[0]; int j = p[1];
        switch (pos){
            case 0:
                putUL(el, i, j);
            case 1:
                putUR(el, i, j);
            case 2:
                putDL(el, i, j);
            case 3:
                putDR(el, i, j);
        }
    }

    public int[] find(T el) throws TargetNotPresentException {
        for (int i=0; i<mat.size(); i++) {
            for (int j=0; j<3; j++) {
                if(mat.get(i).get(j).equals(el)){
                    int[] pos = new int[2];
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        throw new TargetNotPresentException();
    }

    private void putUL(T el, int i, int j) {
        mat.get(i).set(j-1, el);
        if(j-1 == 0)
            addColumnSX();
    }

    private void putUR(T el, int i, int j){
        mat.get(i-1).set(j+1, el);
        if(j+1 == mat.get(0).size()-1)
            addColumnDX();
        if(i-1 == 0)
            addRowUP();
    }

    private void putDL(T el, int i, int j){
        mat.get(i+1).set(j-1, el);
        if(j-1 == 0)
            addColumnSX();
        if(i+1 == mat.size()-1)
            addRowDOWN();
    }

    private void putDR(T el, int i, int j){
        mat.get(i).set(j+1, el);
        if(j+1 == mat.get(0).size()-1)
            addColumnDX();
    }

    private void addColumnDX(){
        for (LinkedList<T> list : mat) {
            list.addLast(null);
        }
    }

    private void addColumnSX(){
        for (LinkedList<T> list : mat) {
            list.addFirst(null);
        }
    }

    private void addRowUP(){
        LinkedList<T> l = new LinkedList<>();
        for(int i=0; i<mat.get(0).size(); i++){
            l.add(null);
        }
        mat.addFirst(l);
    }

    private void addRowDOWN(){
        LinkedList<T> l = new LinkedList<>();
        for(int i=0; i<mat.get(0).size(); i++){
            l.add(null);
        }
        mat.addLast(l);
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (LinkedList<T> list : mat) {
            for (T t : list) {
                s.append(t);
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
