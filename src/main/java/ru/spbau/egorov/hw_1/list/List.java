package ru.spbau.egorov.hw_1.list;


import javafx.util.Pair;

/**
 * This class implements doubly linked list with two Strings as data.
 */
public class List {

    private class Node {
        Node next = null;
        Node prev = null;
        String key;
        String value;

        Node(String key, String value, Node prev, Node next) {
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
    public Pair<String,String> getByIndex(int ind){
        int cnt = 0;
        for (Node i = head.next; i != null; i = i.next) {
            if(cnt == ind)
                return new Pair<String,String>(i.key,i.value);
            cnt++;
        }
        return null;
    }

    /**
     * Add new element to the head of the list.Delete previous element with same key(if it exists).
     * @return Previous element with same key`s value.
     */
    public String add(String key, String value) {
        String oldValue = remove(key);
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
    public String remove(String key) {
        for (Node i = head.next; i != null; i = i.next) {
            if (i.key != null && i.key.equals(key)) {
                String val = i.value;
                i.prev.next = i.next;

                if (i.next != null)
                    i.next.prev = i.prev;

                return val;
            }
        }
        return null;
    }

    public String get(String key) {
        for (Node i = head.next; i != null; i = i.next) {
            if (i.key != null && i.key.equals(key)) {
                return i.value;
            }
        }
        return null;
    }
}