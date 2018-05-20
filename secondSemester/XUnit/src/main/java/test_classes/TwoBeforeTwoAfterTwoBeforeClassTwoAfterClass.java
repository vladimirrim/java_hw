package test_classes;

import ru.spbau.egorov.hw_5.annotations.*;

public class TwoBeforeTwoAfterTwoBeforeClassTwoAfterClass {
    @BeforeClass
    void beforeClassOne() {
        System.out.print(1);
    }

    @BeforeClass
    void beforeClassTwo() {
        System.out.print(1);
    }

    @Before
    void beforeThree() {
        System.out.print(2);
    }

    @Before
    void beforeFour() {
        System.out.print(2);
    }

    @AfterClass
    void afterClassOne() {
        System.out.print(1);
    }

    @AfterClass
    void afterClassTwo() {
        System.out.print(1);
    }

    @After
    void afterThree() {
        System.out.print(2);
    }

    @After
    void afterFour() {
        System.out.print(2);
    }

    @Test
    void test() {
        System.out.print(5);
    }
}
