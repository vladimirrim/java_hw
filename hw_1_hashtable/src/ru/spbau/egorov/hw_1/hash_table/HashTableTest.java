package ru.spbau.egorov.hw_1.hash_table;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {
    @Test
    void createHashTable() {
        HashTable ht = new HashTable();
        assertNotNull(ht);
    }

    @Test
    void sizeZero() {
        HashTable ht = new HashTable();
        assertEquals(0, ht.size());
    }

    @Test
    void sizeTwo() {
        HashTable ht = new HashTable();
        ht.put("Jo", "Jo");
        ht.put("ke", "k");
        assertEquals(2, ht.size());
    }

    @Test
    void containsNothing() {
        HashTable ht = new HashTable();
        assertEquals(false, ht.contains("Jo"));
    }

    @Test
    void containsTwoElements() {
        HashTable ht = new HashTable();
        ht.put("Jo", "Jo");
        ht.put("ke", "k");
        assertEquals(true, ht.contains("Jo"));
    }

    @Test
    void getNothing() {
        HashTable ht = new HashTable();
        assertEquals(null, ht.get("Jo"));
    }

    @Test
    void getOneElement() {
        HashTable ht = new HashTable();
        ht.put("Jo", "Jo");
        assertEquals("Jo", ht.get("Jo"));
    }

    @Test
    void putNewKey() {
        HashTable ht = new HashTable();
        assertEquals(null, ht.put("Jo", "Jo"));
    }

    @Test
    void putSameKey() {
        HashTable ht = new HashTable();
        ht.put("Jo", "Jo");
        assertEquals("Jo", ht.put("Jo", "jo"));
    }

    @Test
    void removeNothing() {
        HashTable ht = new HashTable();
        assertEquals(null, ht.remove("Jo"));
    }

    @Test
    void removeOneElement() {
        HashTable ht = new HashTable();
        ht.put("Jo", "Jo");
        assertEquals("Jo", ht.remove("Jo"));
    }

    @Test
    void clearEmptyHashTable() {
        HashTable ht = new HashTable();
        ht.clear();
        assertNotNull(ht);
    }

    @Test
    void clearNotEmptyHashTable() {
        HashTable ht = new HashTable();
        ht.put("Jo", "Jo");
        ht.clear();
        assertEquals(false, ht.contains("Jo"));
    }

    @Test
    void sameHashCode() {
        HashTable ht = new HashTable();
        ht.put("AaAa", "jo");
        ht.put("BBBB", "Jo");
        assertEquals("jo", ht.get("AaAa"));
        assertEquals("Jo", ht.get("BBBB"));
    }

    @Test
    void resizeHashTable() {
        HashTable ht = new HashTable();
        for (int i = 0; i < 300000; i++) {
            String s = "" + i;
            ht.put(s, "Jo");
        }
        assertEquals(300000, ht.size());

        for (int i = 0; i < 300000; i++) {
            assertEquals("Jo", ht.get("" + i));
        }
    }

}