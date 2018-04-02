package ru.spbau.egorov.hw_8.stack;

public class EmptyStackException extends Exception {
    public EmptyStackException() {
        super("Tried to pop from empty stack.");
    }
}
