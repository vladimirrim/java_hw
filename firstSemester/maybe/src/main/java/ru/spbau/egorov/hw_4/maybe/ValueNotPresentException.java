package ru.spbau.egorov.hw_4.maybe;

/**
 * This exception is thrown when trying to get value of Maybe when it`s null.
 */
public class ValueNotPresentException extends Exception {
    public ValueNotPresentException() {
        super("get() used with null in value.");
    }
}
