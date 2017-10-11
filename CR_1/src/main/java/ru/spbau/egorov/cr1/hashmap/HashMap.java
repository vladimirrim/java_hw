package ru.spbau.egorov.cr1.hashmap;


import javafx.util.Pair;
import ru.spbau.egorov.cr1.list.List;

import java.util.*;


public class HashMap<K, V> implements Map<K, V> {

    private int mapSize = 10;
    private int size = 0;
    private List<Integer, List<K, V>> hashTable;
    private List<Integer, Node<K, V>> listOfEntries;
    private List<Node<K, V>, Integer> indices;
    private int curInd;

    public HashMap() {
        listOfEntries = new List<>();
        hashTable = new List<>();
        indices = new List<>();
        for (int i = 0; i < mapSize; i++)
            hashTable.add(i, new List<>());
    }


    private void resize() {
        if (2 * size < mapSize)
            return;

        List<Integer, List<K, V>> oldTable = new List<>();
        for (int i = 0; i < mapSize; i++)
            oldTable.add(i, new List());

        for (int i = 0; i < mapSize; i++)
            for (int j = 0; ; j++) {
                Pair<K, V> pa = hashTable.get(i).getByIndex(j);
                if (pa == null)
                    break;
                oldTable.get(i).add(pa.getKey(), pa.getValue());
            }

        List<Integer, List<K, V>> newTable = new List<>();
        for (int i = 0; i < mapSize * 2; i++)
            newTable.add(i, new List<>());

        hashTable = newTable;

        int oldTableSize = mapSize;
        size = 0;
        mapSize *= 2;

        for (int i = 0; i < oldTableSize; i++)
            for (int j = 0; ; j++) {
                Pair<K, V> pa = oldTable.get(i).getByIndex(j);
                if (pa == null)
                    break;
                put(pa.getKey(), pa.getValue());
            }
    }

    public int size() {
        return size;
    }

    /**
     * Check if there are elements in hashmap.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Check if the element with same key is in the table.
     */
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public boolean containsValue(Object value) {
        return false;
    }

    public V get(Object key) {
        return hashTable.get(key.hashCode() % mapSize).get(key);
    }

    /**
     * Add element to the hash table.Delete previous element with the same key.
     *
     * @return previous element with the same key` value.
     */
    public V put(K key, V value) {
        indices.add(new Node<>(key, value), curInd++);
        listOfEntries.add(curInd, new Node<>(key, value));
        V oldValue = hashTable.get(key.hashCode() % mapSize).add(key, value);
        if (oldValue == null)
            size++;

        resize();
        return oldValue;
    }


    /**
     * Remove element with same key.
     *
     * @return Removed element`s value.
     */
    public V remove(Object key) {
        listOfEntries.remove(key);
        V oldValue = hashTable.get(key.hashCode() % mapSize).remove(key);
        if (oldValue != null)
            size--;
        return oldValue;
    }

    public void putAll(Map<? extends K, ? extends V> m) {

    }

    /**
     * Delete all elements in the hash table.
     */
    public void clear() {
        size = 0;
        for (int i = 0; i < mapSize; i++)
            hashTable.add(i, new List<>());
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }


    private class Node<K, V> implements Entry<K, V> {
        K key;
        V value;

        public Node(K k, V val) {
            key = k;
            value = val;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V val) {
            V oldValue = value;
            value = val;
            return oldValue;
        }
    }


    final class HashIterator implements Iterator<Entry<K, V>> {
        int next;

        public final boolean hasNext() {
            return listOfEntries.get(next) != null;
        }

        final Node<K, V> nextNode() {
            while (indices.get(next) == null)
                next++;

            return listOfEntries.get(next++);
        }


        public final Map.Entry<K, V> next() {
            return nextNode();
        }
    }


    final class HashSet extends AbstractSet<Entry<K, V>> {
        public final int size() {
            return size;
        }

        public final Iterator<Map.Entry<K, V>> iterator() {
            return new HashIterator();
        }
    }

    public Set<Entry<K, V>> entrySet() {
        return new HashSet();
    }
}
