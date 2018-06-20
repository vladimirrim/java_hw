package test_classes;

import ru.spbau.egorov.hw_5.annotations.Test;

public class ExpectedException {
    @Test(expected = NullPointerException.class)
    void test() {
        throw new NullPointerException();
    }
}
