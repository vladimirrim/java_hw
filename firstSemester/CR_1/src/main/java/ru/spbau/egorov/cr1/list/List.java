package ru.spbau.egorov.cr1.list;

import javafx.util.Pair;

/**
 * This class implements doubly linked list with two Strings as data.
 */
public class List<K,V> {

    public class Node {
        Node next = null;
        Node prev = null;
        K key;
        V value;

        Node(K key, V value, Node prev, Node next) {
            this.key = key;
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node head;


    public List() {
        head = new Node(null, null, null, null);
    }

    /**
     * Copy all elements from another list.
     */

    /**
     * Get key and value from node ind nodes away from head.
     * @return null if index is out of range.
     */
    public Pair<K,V> getByIndex(int ind){
        int cnt = 0;
        for (Node i = head.next; i != null; i = i.next) {
            if(cnt == ind)
                return new Pair<>(i.key,i.value);
            cnt++;
        }
        return null;
    }

    /**
     * Add new element to the head of the list.Delete previous element with same key(if it exists).
     * @return Previous element with same key`s value.
     */
    public V add(K key, V value) {
        V oldValue = remove(key);
        Node newNode = new Node(key, value, head, head.next);
        head.next = newNode;
        if (newNode.next != null)
            newNode.next.prev = newNode;
        return oldValue;
    }

    /**
     * Remove element with same key.
     * @return Removed element`s value.
     */
    public V remove(Object key) {
        for (Node i = head.next; i != null; i = i.next) {
            if (i.key != null && i.key.equals(key)) {
                V val = i.value;
                i.prev.next = i.next;

                if (i.next != null)
                    i.next.prev = i.prev;

                return val;
            }
        }
        return null;
    }

    public V get(Object key) {
        for (Node i = head.next; i != null; i = i.next) {
            if (i.key != null && i.key.equals(key)) {
                return i.value;
            }
        }
        return null;
    }
}