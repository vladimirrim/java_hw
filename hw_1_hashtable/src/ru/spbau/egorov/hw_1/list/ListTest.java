package ru.spbau.egorov.hw_1.list;


import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {
    @Test
    void createList() {
        List l = new List();
        assertNotNull(l);
    }

    @Test
    void addAndGetNotNull() {
        List l = new List();
        l.add("jo", "jo");
        assertEquals("jo", l.get("jo"));
    }

    @Test
    void addAndGetNull() {
        List l = new List();
        l.add("jo", "jo");
        assertNull(l.get("Jo"));
    }

    @Test
    void removeInList() {
        List l = new List();
        l.add("jo", "jo");
        assertEquals("jo", l.remove("jo"));
    }

    @Test
    void removeNotInList() {
        List l = new List();
        l.add("jo", "jo");
        assertNull(l.remove("Jo"));
    }

    @Test
    void getByIndexNull() {
        List l = new List();
        assertNull(l.getByIndex(0));
    }

    @Test
    void getByIndexNotNull() {
        List l = new List();
        l.add("jo", "jo");
        assertEquals(new Pair<>("jo", "jo"), l.getByIndex(0));
    }
}