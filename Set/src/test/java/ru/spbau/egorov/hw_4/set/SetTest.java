package ru.spbau.egorov.hw_4.set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetTest {
    @Test
    void createSet() {
        Set<Integer> s = new Set<>();
        assertNotNull(s);
    }

    @Test
    void addAndContainsOneElement() {
        Set<String> s = new Set<>();
        s.add("0");
        assertTrue(s.contains("0"));
    }

    @Test
    void addAndContainsZeroElements() {
        Set<String> s = new Set<>();
        assertFalse(s.contains("0"));
    }

    @Test
    void addAndContainsThousandElements() {
        Set<Integer> s = new Set<>();
        for (int i = 0; i < 1000; i++)
            s.add(i);
        for (int i = 0; i < 1000; i++)
            assertTrue(s.contains(i));
    }

    @Test
    void addAndContainsSameElement() {
        Set<Integer> s = new Set<>();
        for (int i = 0; i < 1000; i++)
            s.add(0);
        assertTrue(s.contains(0));
    }

    @Test
    void sizeOneElement() {
        Set<Integer> s = new Set<>();
        s.add(0);
        assertEquals(1, s.size());
    }


    @Test
    void sizeThousandElements() {
        Set<Integer> s = new Set<>();
        for (int i = 0; i < 1000; i++)
            s.add(i);
        assertEquals(1000, s.size());
    }

    @Test
    void sizeSameElement() {
        Set<Integer> s = new Set<>();
        for (int i = 0; i < 1000; i++)
            s.add(0);
        assertEquals(1, s.size());
    }

}