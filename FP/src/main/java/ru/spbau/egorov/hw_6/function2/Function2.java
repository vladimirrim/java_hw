package ru.spbau.egorov.hw_6.function2;

import org.jetbrains.annotations.NotNull;
import ru.spbau.egorov.hw_6.function1.Function1;

/**
 * This class implements function with 2 arguments.
 *
 * @param <T> is type of returning value.
 * @param <X> is type of the first argument.
 * @param <Y> is type of the second argument.
 */
public interface Function2<T, X, Y> {

    /**
     * Contained function.
     *
     * @param x is the first argument.
     * @param y is the second argument.
     */
    T apply(X x, Y y);

    /**
     * This method return composition g(f).
     *
     * @param g   is function with 1 argument to compose.
     * @param <K> is type of the returning value.
     * @return composed function.
     */
    default <K> Function2<K, X, Y> compose(@NotNull Function1<? super T, K> g) {
        return (x, y) -> g.apply(apply(x, y));
    }

    /**
     * This method binds the first argument with given value.
     *
     * @param x is value to bind.
     * @return function with one argument which is partly applied old function.
     */
    default Function1<Y, T> bind1(X x) {
        return y -> apply(x, y);
    }

    /**
     * This method binds the second argument with given value.
     *
     * @param y is value to bind.
     * @return function with one argument which is partly applied old function.
     */
    default Function1<X, T> bind2(Y y) {
        return x -> apply(x, y);
    }

    /**
     * This method creates function with one argument by binding the second value of the old function.
     *
     * @param y is value to bind.
     * @return function with one argument which is partly applied old function.
     */
    default Function1<X, T> curry(Y y) {
        return bind2(y);
    }
}
