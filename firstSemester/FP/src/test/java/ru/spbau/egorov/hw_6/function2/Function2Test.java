package ru.spbau.egorov.hw_6.function2;

import org.junit.jupiter.api.Test;
import ru.spbau.egorov.hw_6.function1.Function1;

import static org.junit.jupiter.api.Assertions.*;

class Function2Test {
    private Function2<Integer, Integer, String> sum = (x, y) -> x + Integer.parseInt(y);
    private Function1<Integer, String> toStr = Object::toString;

    @Test
    void composeTwoFunctions() {
        assertEquals("123", sum.compose(toStr).apply(23, "100"));
    }

    @Test
    void bind1Integer() {
        assertEquals((Integer) 5, sum.bind1(5).apply("0"));
    }

    @Test
    void bind2String() {
        assertEquals((Integer) 5, sum.bind2("5").apply(0));
    }

    @Test
    void curryString() {
        assertEquals((Integer) 18, sum.curry("8").apply(10));
    }

}