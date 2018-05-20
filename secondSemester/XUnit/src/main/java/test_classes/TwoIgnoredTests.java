package test_classes;

import ru.spbau.egorov.hw_5.annotations.Test;

public class TwoIgnoredTests {
    @Test(ignored = "JoJo")
    void testOne() {
        System.out.print(5);
    }

    @Test(ignored = "jojo")
    void testTwo() {
        System.out.print(6);
    }
}
