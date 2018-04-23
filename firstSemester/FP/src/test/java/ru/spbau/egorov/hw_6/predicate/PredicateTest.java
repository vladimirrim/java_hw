package ru.spbau.egorov.hw_6.predicate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PredicateTest {
    private Predicate<Integer> eq8 = x -> x == 8;
    private Predicate<Integer> lt8 = x -> x < 8;
    private Predicate<Integer> gt5 = x -> x > 5;
    private Predicate<String> eqJo = x -> x.equals("Jo");
    private Predicate<Integer> bot = x -> {
        throw new RuntimeException();
    };

    @Test
    void orIntegers() {
        assertTrue(eq8.or(gt5).apply(9));
    }

    @Test
    void orSamePredicate() {
        assertFalse(eqJo.or(eqJo).apply("jo"));
    }

    @Test
    void orLaziness() {
        assertTrue(eq8.or(bot).apply(8));
    }

    @Test
    void andIntegers() {
        assertFalse(gt5.and(lt8).apply(9));
    }

    @Test
    void andSamePredicate() {
        assertTrue(eqJo.and(eqJo).apply("Jo"));
    }

    @Test
    void andLaziness() {
        assertFalse(lt8.and(bot).apply(8));
    }

    @Test
    void notIntegers() {
        assertTrue(gt5.not().apply(5));
    }

    @Test
    void notOrAndComposition() {
        assertTrue(gt5.and(lt8).or(eq8).not().apply(5));
    }

    @Test
    void alwaysTrue() {
        assertTrue(eqJo.alwaysTrue().apply("Jo"));
    }

    @Test
    void alwaysFalse() {
        assertFalse(eqJo.alwaysFalse().apply("Jo"));
    }
}