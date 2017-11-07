package ru.spbau.egorov.hw_6.function1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Function1Test {
    private Function1<Integer, String> toStr = Object::toString;
    private Function1<String, Integer> toInt = Integer::parseInt;
    private Function1<Integer, Integer> pow = integer -> integer * integer;


    @Test
    void composeTwoFunctions() {
        assertEquals((Integer) 123, toStr.compose(toInt).apply(123));
    }

    @Test
    void composeThreeFunctions() {
        assertEquals((Integer) 64, pow.compose(toStr.compose(toInt)).apply(8));
    }

    @Test
    void composeSameFunction() {
        assertEquals((Integer) 16, pow.compose(pow).apply(2));
    }

}