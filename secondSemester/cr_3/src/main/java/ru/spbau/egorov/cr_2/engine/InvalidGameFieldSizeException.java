package ru.spbau.egorov.cr_2.engine;

/**
 * Is thrown when the game field size is out of range.
 */
public class InvalidGameFieldSizeException extends Exception {
    public InvalidGameFieldSizeException() {
        super("Given game size is out of possible range.");
    }
}
