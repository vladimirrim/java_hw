package ru.spbau.egorov.hw_1;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * This class generates Lazy containers for one or several threads.
 */
public class LazyFactory {

    /**
     * Generates Lazy container for one thread.
     *
     * @param sup is the supplier for the element in container.
     * @param <T> is the type of stored element.
     * @return Lazy container.
     */
    public static <T> Lazy<T> createOneThreadLazy(@NotNull Supplier<T> sup) {
        return new OneThreadLazy<>(sup);
    }

    /**
     * Generates Lazy container for several threads.
     *
     * @param sup is the supplier for the element in container.
     * @param <T> is the type of stored element.
     * @return Lazy container.
     */
    public static <T> Lazy<T> createMultiThreadLazy(@NotNull Supplier<T> sup) {
        return new MultiThreadLazy<>(sup);
    }

}
