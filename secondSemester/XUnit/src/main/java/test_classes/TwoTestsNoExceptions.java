package test_classes;

import ru.spbau.egorov.hw_5.annotations.Test;

public class TwoTestsNoExceptions {

    @Test
    void testOne() {
        System.out.print(5);
    }

    @Test
    void testTwo() {
        System.out.print(6);
    }
}
