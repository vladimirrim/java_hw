package ru.spbau.egorov.hw_7.tree;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {
    @Test
    void createSet() {
        Tree<Integer> s = new Tree<>();
        assertNotNull(s);
    }

    @Test
    void addAndContainsOneElement() {
        Tree<String> s = new Tree<>();
        s.add("0");
        assertTrue(s.contains("0"));
    }

    @Test
    void addAndContainsZeroElements() {
        Tree<String> s = new Tree<>();
        assertFalse(s.contains("0"));
    }

    @Test
    void addAndContainsThousandElements() {
        Tree<Integer> s = new Tree<>();
        for (int i = 0; i < 1000; i++)
            s.add(i);
        for (int i = 0; i < 1000; i++)
            assertTrue(s.contains(i));
    }

    @Test
    void addAndContainsSameElement() {
        Tree<Integer> s = new Tree<>();
        for (int i = 0; i < 1000; i++)
            s.add(0);
        assertTrue(s.contains(0));
    }

    @Test
    void sizeOneElement() {
        Tree<Integer> s = new Tree<>();
        s.add(0);
        assertEquals(1, s.size());
    }


    @Test
    void sizeThousandElements() {
        Tree<Integer> s = new Tree<>();
        for (int i = 0; i < 1000; i++)
            s.add(i);
        assertEquals(1000, s.size());
    }

    @Test
    void sizeSameElement() {
        Tree<Integer> s = new Tree<>();
        for (int i = 0; i < 1000; i++)
            s.add(0);
        assertEquals(1, s.size());
    }

}