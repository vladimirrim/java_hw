package test_classes;

import ru.spbau.egorov.hw_5.annotations.Test;

public class TwoTestsNoExceptions {

    @Test
    void testOne() {
        System.out.print("Test1");
    }

    @Test
    void testTwo() {
        System.out.print("Test2");
    }
}
