package test_classes;

import ru.spbau.egorov.hw_5.annotations.*;

public class TwoBeforeTwoAfterTwoBeforeClassTwoAfterClass {
    @BeforeClass
    void beforeClassOne() {
        System.out.print("BeforeClass");
    }

    @BeforeClass
    void beforeClassTwo() {
        System.out.print("BeforeClass");
    }

    @Before
    void beforeThree() {
        System.out.print("Before");
    }

    @Before
    void beforeFour() {
        System.out.print("Before");
    }

    @AfterClass
    void afterClassOne() {
        System.out.print("AfterClass");
    }

    @AfterClass
    void afterClassTwo() {
        System.out.print("AfterClass");
    }

    @After
    void afterThree() {
        System.out.print("After");
    }

    @After
    void afterFour() {
        System.out.print("After");
    }

    @Test
    void test() {
        System.out.print("Test");
    }
}
