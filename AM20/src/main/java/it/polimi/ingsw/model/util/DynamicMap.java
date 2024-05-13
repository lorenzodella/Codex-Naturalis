package it.polimi.ingsw.model.util;

import it.polimi.ingsw.model.cards.playable.PlayableCard;
import it.polimi.ingsw.model.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.exceptions.TargetNotPresentException;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Dynamic map which contains elements at a certain point in 2D space
 * @param <K> type of the elements key of the map
 * @param <T> type of the elements of the map
 */
public class DynamicMap<K,T> implements Serializable {
    // these are used to fetch elements near a certain element of the map
    public static final int UL = 0;
    public static final int UR = 1;
    public static final int DL = 2;
    public static final int DR = 3;

    public static final int U = 4;
    public static final int D = 5;

    private static class MapElement<T> implements Serializable {
        Point pos;
        T value;
        private MapElement(Point pos, T value){
            this.pos = pos;
            this.value = value;
        }
    }
    private final HashMap<K, MapElement<T>> map;

    /**
     *
     * @param key key of the element
     * @param centerEl element to be placed in the center of the map
     */
    public DynamicMap(K key, T centerEl) {
        map = new HashMap<>();
        map.put(key, new MapElement<>(new Point(0,0), centerEl));
    }

    /**
     *
     * @return num of elements of the map
     */
    public int numOfElements(){
        return map.size();
    }

    /**
     *
     * @return a list of all the elements in the map
     */
    public List<T> values(){
        return map.values().stream().map(e -> e.value).collect(Collectors.toList());
    }

    /**
     *
     * @return max x-distance between the elements
     */
    public int width(){
        if(numOfElements()==0)
            return 0;
        int min = map.values().stream().min(Comparator.comparingInt(a -> a.pos.x)).map(e->e.pos.x).orElse(0);
        int max = map.values().stream().max(Comparator.comparingInt(a -> a.pos.x)).map(e->e.pos.x).orElse(0);
        return max-min+1;
    }

    /**
     *
     * @return max y-distance between the elements
     */
    public int height(){
        if(numOfElements()==0)
            return 0;
        int min = map.values().stream().min(Comparator.comparingInt(a -> a.pos.y)).map(e->e.pos.y).orElse(0);
        int max = map.values().stream().max(Comparator.comparingInt(a -> a.pos.y)).map(e->e.pos.y).orElse(0);
        return max-min+1;
    }

    /**
     * Insert an element in the map. Position is related to the target object.
     * @param key key of the element
     * @param el element to be inserted
     * @param targetKey key of the object near which the element must be inserted
     * @param pos 0 = one point to the top left, 1 = one point to the top right,
     *            2 = one point to the bottom left, 3 = one point to the bottom right
     * @throws TargetNotPresentException if target object is not present
     * @throws InvalidPositionException if pos is not a valid value
     */
    public void insert(K key, T el, K targetKey, int pos) throws TargetNotPresentException, InvalidPositionException {
        //if there already is an element in that position remove it
        Map.Entry<K, MapElement<T>> tmp = getEntryAt(targetKey, pos);
        if(tmp!=null)
            remove(tmp.getKey());
        Point p = findPos(targetKey);
        switch (pos) {
            case UL:
                map.put(key, new MapElement<>(new Point(p.x-1, p.y+1), el));
                break;
            case UR:
                map.put(key, new MapElement<>(new Point(p.x+1, p.y+1), el));
                break;
            case DL:
                map.put(key, new MapElement<>(new Point(p.x-1, p.y-1), el));
                break;
            case DR:
                map.put(key, new MapElement<>(new Point(p.x+1, p.y-1), el));
                break;
            default:
                throw new InvalidPositionException(pos);
        }
    }

    /**
     * Get the element at those coordinates
     * @param x x-position of the element
     * @param y y-position of the element
     * @return the element
     */
    public T getElement(int x, int y){
        return map.values().stream()
                .filter(e -> e.pos.x == x && e.pos.y == y)
                .map(e -> e.value)
                .findFirst()
                .orElse(null);
    }

    /**
     * Get the element value at a certain position relative to a particular object
     * @param targetKey key of the object near which the element is
     * @param pos 0 = one point to the top left, 1 = one point to the top right,
     *            2 = one point to the bottom left, 3 = one point to the bottom right
     *            4 = two points on the top, 5 = two points on the bottom
     * @return element at that position
     * @throws TargetNotPresentException if target object is not present
     * @throws InvalidPositionException if pos is not a valid value
     */
    public T getElementAt(K targetKey, int pos) throws TargetNotPresentException, InvalidPositionException {
        Map.Entry<K, MapElement<T>> tmp = getEntryAt(targetKey, pos);
        return tmp!=null ? tmp.getValue().value : null;
    }

    /**
     * Get the element at a certain position relative to a particular object
     * @param targetKey key of the object near which the element is
     * @param pos 0 = one point to the top left, 1 = one point to the top right,
     *            2 = one point to the bottom left, 3 = one point to the bottom right
     *            4 = two points on the top, 5 = two points on the bottom
     * @return element at that position
     * @throws TargetNotPresentException if target object is not present
     * @throws InvalidPositionException if pos is not a valid value
     */
    private Map.Entry<K, MapElement<T>> getEntryAt(K targetKey, int pos) throws TargetNotPresentException, InvalidPositionException {
        Point p = findPos(targetKey);
        switch (pos) {
            case UL:
                return map.entrySet().stream()
                        .filter(e -> e.getValue().pos.x == p.x-1 && e.getValue().pos.y == p.y+1)
                        .findFirst().orElse(null);
            case UR:
                return map.entrySet().stream()
                        .filter(e -> e.getValue().pos.x == p.x+1 && e.getValue().pos.y == p.y+1)
                        .findFirst().orElse(null);
            case DL:
                return map.entrySet().stream()
                        .filter(e -> e.getValue().pos.x == p.x-1 && e.getValue().pos.y == p.y-1)
                        .findFirst().orElse(null);
            case DR:
                return map.entrySet().stream()
                        .filter(e -> e.getValue().pos.x == p.x+1 && e.getValue().pos.y == p.y-1)
                        .findFirst().orElse(null);
            case U:
                return map.entrySet().stream()
                        .filter(e -> e.getValue().pos.x == p.x && e.getValue().pos.y == p.y+2)
                        .findFirst().orElse(null);
            case D:
                return map.entrySet().stream()
                        .filter(e -> e.getValue().pos.x == p.x && e.getValue().pos.y == p.y-2)
                        .findFirst().orElse(null);
            default:
                throw new InvalidPositionException(pos);
        }
    }

    /**
     * Find the coordinates of an element in the map.
     * @param targetKey key of the element to be found
     * @return the point where the element is positioned
     * @throws TargetNotPresentException if no element with that key is not present
     */
    public Point findPos(K targetKey) throws TargetNotPresentException {
        MapElement<T> el = map.get(targetKey);
        if(el!=null)
            return el.pos;
        else
            throw new TargetNotPresentException(targetKey);
    }

    /**
     * Get the element in the map with the given key.
     * @param targetKey key of the element to be found
     * @return the element
     * @throws TargetNotPresentException if no element with that key is not present
     */
    public T find(K targetKey) throws TargetNotPresentException {
        T el = map.get(targetKey).value;
        if(el!=null)
            return el;
        else
            throw new TargetNotPresentException(targetKey);
    }

    /**
     * Remove the element in the map with the given key.
     * @param targetKey key of the element to be removed
     * @throws TargetNotPresentException if no element with that key is not present
     */
    public void remove(K targetKey) throws TargetNotPresentException {
        MapElement<T> el = map.remove(targetKey);
        if(el==null)
            throw new TargetNotPresentException(targetKey);
    }

    /**
     * Creates a list of sorted maps with the elements' key of the map.
     * Maps are sorted by y-position (descending), their elements are sorted by x-position (ascending).
     * @return a list of maps representing a matrix: key is the position, value is the element
     */
    private List<Map<Point, K>> getMapElementsKeyLocation(){
        Collection<Map<Point, K>> list = map.entrySet().stream()
                .collect(Collectors.groupingBy(e -> e.getValue().pos.y, TreeMap::new,
                        Collectors.toMap(e -> e.getValue().pos, Map.Entry::getKey)))
                .values();
        return list.stream()
                .sorted((a,b) -> Integer.compare(
                        b.keySet().stream().findAny().map(e->e.y).orElse(0),
                        a.keySet().stream().findAny().map(e->e.y).orElse(0)
                ))
                .map(m -> m.entrySet().stream().sorted(Comparator.comparingInt(a -> a.getKey().x))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b)->a, LinkedHashMap::new)))
                .collect(Collectors.toList());
    }

    /**
     * Creates a list of sorted maps with the elements of the map.
     * Maps are sorted by y-position (descending), their elements are sorted by x-position (ascending).
     * @return a list of maps representing a matrix: key is the position, value is the element
     */
    public List<Map<Point, T>> getMapElementsLocation(){
        Collection<Map<Point, T>> list = map.values().stream()
                .collect(Collectors.groupingBy(e -> e.pos.y, TreeMap::new,
                        Collectors.toMap(e -> e.pos, e -> e.value)))
                .values();
        return list.stream()
                .sorted((a,b) -> Integer.compare(
                        b.keySet().stream().findAny().map(e->e.y).orElse(0),
                        a.keySet().stream().findAny().map(e->e.y).orElse(0)
                ))
                .map(m -> m.entrySet().stream().sorted(Comparator.comparingInt(a -> a.getKey().x))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b)->a, LinkedHashMap::new)))
                .collect(Collectors.toList());
    }

    public int min(){
        return map.values().stream().min(Comparator.comparingInt(a -> a.pos.x)).map(e->e.pos.x).orElse(0);
    }

    /**
     * Display the map.
     * @return a string representative the map
     */
    public String toString(){
        int min = map.values().stream().min(Comparator.comparingInt(a -> a.pos.x)).map(e->e.pos.x).orElse(0);
        int tmp;
        List<Map<Point, K>> m = getMapElementsKeyLocation();
        StringBuilder s = new StringBuilder();
        for (Map<Point, K> orderedMap : m) {
            tmp = min;
            for (Map.Entry<Point, K> t : orderedMap.entrySet()) {
                for(int i=tmp; i<t.getKey().x; i++){
                    s.append("  ");
                }
                tmp = t.getKey().x+1;
                //s.append(t.value+"("+t.pos.x+","+t.pos.y+")");
                s.append(t.getValue()+" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
