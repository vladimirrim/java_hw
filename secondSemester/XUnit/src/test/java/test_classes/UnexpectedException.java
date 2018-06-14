package test_classes;

import ru.spbau.egorov.hw_5.annotations.Test;

public class UnexpectedException {
    @Test
    void test() {
        throw new NullPointerException();
    }
}
