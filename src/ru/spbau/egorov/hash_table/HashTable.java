package ru.spbau.egorov.hash_table;

import ru.spbau.egorov.list.List;

/**
 * @author Vladimir Egorov
 *         This class implements hash table.
 */


public class HashTable {
    private static final int TABLE_SIZE = 514229;
    private int size_ = 0;
    private List[] hashTable_;

    public HashTable() {
        hashTable_ = new List[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++)
            hashTable_[i] = new List();
    }

    public int size() {
        return size_;
    }

    public boolean contains(String key) {
        if (hashTable_[key.hashCode() % TABLE_SIZE].get(key) != null)
            return true;
        return false;
    }

    public String get(String key) {
        return hashTable_[key.hashCode() % TABLE_SIZE].get(key);
    }

    public String put(String key, String value) {
        String oldValue = hashTable_[key.hashCode() % TABLE_SIZE].add(key, value);
        if (oldValue == null)
            size_++;
        return oldValue;
    }

    public String remove(String key) {
        String oldValue = hashTable_[key.hashCode() % TABLE_SIZE].remove(key);
        if (oldValue != null)
            size_--;
        return oldValue;
    }

    public void clear() {
        size_ = 0;
        for (int i = 0; i < TABLE_SIZE; i++)
            hashTable_[i] = new List();
    }
}
