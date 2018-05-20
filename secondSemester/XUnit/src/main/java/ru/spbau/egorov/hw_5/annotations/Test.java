package ru.spbau.egorov.hw_5.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks tests to run.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
    /**
     * This string explains reason for ignoring test. "NOT_IGNORED" must be set to execute the test.
     *
     * @return reason for ignoring.
     */
    String ignored() default "NOT_IGNORED";

    /**
     * This class represents exception that must be thrown from test. If no exception is thrown, then it must be set to void.class.
     *
     * @return thrown exception.
     */
    Class expected() default void.class;
}
