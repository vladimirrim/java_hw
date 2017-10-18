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

    @NotNull
    private Maybe(T t) {
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
    @NotNull
    public static <T> Maybe<T> just(T t) {
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
     * @throws MaybeNothingException if Maybe contains null.
     */
    public T get() throws MaybeNothingException {
        if (value != null)
            return value;
        throw new MaybeNothingException();
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
    public <U> Maybe<U> map(Function<T, U> mapper) {
        if (isPresent())
            return new Maybe<>(mapper.apply(value));
        return nothing();
    }

}
