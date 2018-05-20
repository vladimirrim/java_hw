package test_classes;

import ru.spbau.egorov.hw_5.annotations.AfterClass;
import ru.spbau.egorov.hw_5.annotations.BeforeClass;

public class MethodWithTwoAnnotations {

    @AfterClass
    @BeforeClass
    void test() {
        System.out.print(1);
    }
}
