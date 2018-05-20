package test_classes;

import ru.spbau.egorov.hw_5.annotations.AfterClass;
import ru.spbau.egorov.hw_5.annotations.BeforeClass;
import ru.spbau.egorov.hw_5.annotations.Test;

public class BeforeClassAndAfterClass {
    @BeforeClass
    void beforeClassOne() {
        System.out.print(1);
    }

    @AfterClass
    void afterClassTwo() {
        System.out.print(2);
    }

    @Test
    void test() {
        System.out.print(5);
    }
}
