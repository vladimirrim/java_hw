package test_classes;

import ru.spbau.egorov.hw_5.annotations.AfterClass;
import ru.spbau.egorov.hw_5.annotations.BeforeClass;
import ru.spbau.egorov.hw_5.annotations.Test;

public class BeforeClassAndAfterClass {
    @BeforeClass
    void beforeClassOne() {
        System.out.print("Before class");
    }

    @AfterClass
    void afterClassTwo() {
        System.out.print("After class");
    }

    @Test
    void test() {
        System.out.print("Test");
    }
}
