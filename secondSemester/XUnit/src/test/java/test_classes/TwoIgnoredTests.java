package test_classes;

import ru.spbau.egorov.hw_5.annotations.Test;

public class TwoIgnoredTests {
    @Test(ignored = "JoJo")
    void testOne() {
        System.out.print("Test1");
    }

    @Test(ignored = "jojo")
    void testTwo() {
        System.out.print("Test2");
    }
}
