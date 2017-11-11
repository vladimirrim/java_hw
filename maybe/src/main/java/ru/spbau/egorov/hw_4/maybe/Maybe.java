package ru.spbau.egorov.hw_4.maybe;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * This class contains one element or null.
 *
 * @param <T> is type of contained value.
 */
public class Maybe<T> {

    private T value;

    private Maybe(@NotNull T t) {
        value = t;
    }


    private Maybe() {
        value = null;
    }

    /**
     * Create new instance of Maybe which contains t.
     *
     * @param t   value to contain
     * @param <T> type of t.
     */
    public static <T> Maybe<T> just(@NotNull T t) {
        return new Maybe<>(t);
    }


    /**
     * Create new instance of Maybe which contains null.
     */
    @NotNull
    public static <T> Maybe<T> nothing() {
        return new Maybe<>();
    }


    /**
     * @throws ValueNotPresentException if Maybe contains null.
     */
    public T get() throws ValueNotPresentException {
        if (value != null)
            return value;
        throw new ValueNotPresentException();
    }


    /**
     * Check if Maybe contains null.
     */
    public boolean isPresent() {
        return value != null;
    }


    /**
     * Apply function to value in Maybe and return new Maybe with modified value or Nothing if Maybe contains null.
     *
     * @param <U> type of modified value.
     */
    public <U> Maybe<U> map(@NotNull Function<T, U> mapper) {
        if (isPresent())
            return new Maybe<>(mapper.apply(value));
        return new Maybe<>();
    }

}
