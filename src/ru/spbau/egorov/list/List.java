package ru.spbau.egorov.list;


/**
 * @author Vladimir Egorov
 *         This class implements list.
 */


class Node {
    Node next_ = null;
    Node prev_ = null;
    String key_;
    String value_;

    Node(String key, String value) {
        key_ = key;
        value_ = value;
    }
}

public class List {
    private Node head_;


    public List() {
        head_ = new Node(null, null);
    }

    public String add(String key, String value) {
        String oldValue = remove(key);
        Node newNode = new Node(key, value);
        newNode.next_ = head_.next_;
        newNode.prev_ = head_;
        head_.next_ = newNode;
        if (newNode.next_ != null)
            head_.next_.prev_ = newNode;
        return oldValue;
    }

    public String remove(String key) {
        for (Node i = head_.next_; i != null; i = i.next_) {
            if (i.key_ != null && i.key_.equals(key)) {
                String val = i.value_;
                i.prev_.next_ = i.next_;
                if (i.next_ != null)
                    i.next_.prev_ = i.prev_;
                return val;
            }
        }
        return null;
    }

    public String get(String key) {
        for (Node i = head_.next_; i != null; i = i.next_) {
            if (i.key_ != null && i.key_.equals(key)) {
                return i.value_;
            }
        }
        return null;
    }
}