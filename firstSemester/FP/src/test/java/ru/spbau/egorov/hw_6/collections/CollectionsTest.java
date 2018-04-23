package ru.spbau.egorov.hw_6.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CollectionsTest {
    @Test
    void mapZeroElements() {
        ArrayList<Integer> arr = new ArrayList<>();
        assertEquals(arr, Collections.map(e -> e, arr));
    }

    @Test
    void mapThousandElements() {
        ArrayList<Integer> arr = new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            arr.add(i);
            res.add(i * i);
        }
        assertEquals(res, Collections.map(e -> e * e, arr));
    }

    @Test
    void filterZeroElements() {
        ArrayList<Integer> arr = new ArrayList<>();
        assertEquals(arr, Collections.filter(e -> true, arr));
    }

    @Test
    void filterThousandElementsOdd() {
        ArrayList<Integer> arr = new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            arr.add(i);
            if (i % 2 == 1)
                res.add(i);
        }
        assertEquals(res, Collections.filter(e -> e % 2 == 1, arr));
    }

    @Test
    void takeWhileZeroElements() {
        ArrayList<Integer> arr = new ArrayList<>();
        assertEquals(arr, Collections.takeWhile(e -> true, arr));
    }

    @Test
    void takeWhileThousandElementsEven() {
        ArrayList<Integer> arr = new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 1; i <= 500; i++) {
            arr.add(2 * i);
            res.add(2 * i);
        }
        for (int i = 1; i <= 500; i++) {
            arr.add(i);
        }
        assertEquals(res, Collections.takeWhile(e -> e % 2 == 0, arr));
    }

    @Test
    void takeUnlessZeroElements() {
        ArrayList<Integer> arr = new ArrayList<>();
        assertEquals(arr, Collections.takeUnless(e -> false, arr));
    }

    @Test
    void takeUnlessThousandElementsEven() {
        ArrayList<Integer> arr = new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 1; i <= 500; i++) {
            arr.add(2 * i);
            res.add(2 * i);
        }
        for (int i = 1; i <= 500; i++) {
            arr.add(i);
        }
        assertEquals(res, Collections.takeUnless(e -> e % 2 == 1, arr));
    }

    @Test
    void foldrZeroElements() {
        ArrayList<Integer> arr = new ArrayList<>();
        assertEquals((Integer) 0, Collections.foldr((e, t) -> t, arr, 0));
    }

    @Test
    void foldrThousandElementsSum() {
        ArrayList<Integer> arr = new ArrayList<>();
        Integer res = 0;
        for (int i = 1; i <= 1000; i++) {
            arr.add(i);
            res += i;
        }
        assertEquals(res, Collections.foldr((e, t) -> t + e, arr, 0));
    }

    @Test
    void foldlZeroElements() {
        ArrayList<Integer> arr = new ArrayList<>();
        assertEquals((Integer) 0, Collections.foldl((t, e) -> t, arr, 0));
    }

    @Test
    void foldlThousandElementsSum() {
        ArrayList<Integer> arr = new ArrayList<>();
        Integer res = 0;
        for (int i = 1; i <= 1000; i++) {
            arr.add(i);
            res += i;
        }
        assertEquals(res, Collections.foldr((t, e) -> t + e, arr, 0));
    }

}