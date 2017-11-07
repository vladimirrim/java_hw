package ru.spbau.egorov.hw_6.function1;

/**
 * This class implements function with one argument.
 * @param <T> is type of the argument.
 * @param <U> is type of the returning value.
 */
public interface Function1<T, U> {
    /**
     * Contained function.
     * @param t is the argument
     */
    U apply(T t);

    /**
     * This method creates the composition g(f(x)).
     * @param g is function to compose.
     * @param <G> is type of the returning value of the new function.
     * @return g(f).
     */
    default <G> Function1<T,G> compose(Function1<? super U,G> g) {
        return t -> g.apply(apply(t));
    }
}
