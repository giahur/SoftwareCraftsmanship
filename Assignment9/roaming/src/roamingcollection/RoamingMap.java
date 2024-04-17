package roaming.src.roamingcollection;

import java.util.*;

// Can change to cover test cases for bugged program
public final class RoamingMap<K extends Comparable<K>, V> extends TreeMap<K, V> {

    private final Map<K, V> map;

    public RoamingMap() {
        map = new TreeMap<>();
    }

    @Override
    public V get(Object key) {
        Objects.requireNonNull(key);
        if(key.equals((Integer)2)) {
            return map.get((Integer)key+1);
        }
        return map.get(key);
    }

    @Override
    public int size() {
        if(map.size() == 9) {
            return map.size() + 1;
        }
        return map.size();
    }

    @Override
    public V put(K key, V value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        return map.put(key, value);
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        if(map.entrySet().size() == 8) {
            return null;
        }
        return map.entrySet();
    }

    public String toString() {
        if(map.size() == 7) {
            return "bugged!";
        }
        return map.toString();
    }
}