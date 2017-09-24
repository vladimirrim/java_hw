package ru.spbau.egorov.hw_1.hash_table;

import ru.spbau.egorov.hw_1.list.List;

/**
 * This class implements chained hash table with linked list.Values and keys are Strings.
 */
public class HashTable {
    private static final int TABLE_SIZE = 514229;
    private int size = 0;
    private List[] hashTable;

    public HashTable() {
        hashTable = new List[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++)
            hashTable[i] = new List();
    }

    /**
     * @return Count of current elements in the hash table.
     */
    public int size() {
        return size;
    }

    /**
     * Check if the element with same key is in the table.
     */
    public boolean contains(String key) {
        return get(key) != null;
    }

    public String get(String key) {
        return hashTable[key.hashCode() % TABLE_SIZE].get(key);
    }

    /**
     * Add element to the hash table.Delete previous element with the same key.
     *
     * @return Previous element with the same key` value.
     */
    public String put(String key, String value) {
        String oldValue = hashTable[key.hashCode() % TABLE_SIZE].add(key, value);
        if (oldValue == null)
            size++;
        return oldValue;
    }

    /**
     * Remove element with same key.
     *
     * @return Removed element`s value.
     */
    public String remove(String key) {
        String oldValue = hashTable[key.hashCode() % TABLE_SIZE].remove(key);
        if (oldValue != null)
            size--;
        return oldValue;
    }

    /**
     * Delete all elements in the hash table.
     */
    public void clear() {
        size = 0;
        for (int i = 0; i < TABLE_SIZE; i++)
            hashTable[i] = new List();
    }

    /**
     * Basic tests.
     */
    public static void main(String[] args) {
        HashTable ht = new HashTable();

        ht.put("jo1", "jo2");
        ht.put("jo2", "jo3");

        assert ht.size() == 2;
        assert ht.contains("jo1");

        ht.remove("jo1");

        assert !ht.contains("jo1");
        assert ht.get("jo2").equals("jo3");

        ht.clear();

        assert ht.size() == 0;
        assert !ht.contains("jo2");
    }
}
