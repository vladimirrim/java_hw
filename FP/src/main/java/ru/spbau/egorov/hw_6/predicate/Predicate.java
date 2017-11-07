package ru.spbau.egorov.hw_6.predicate;

import ru.spbau.egorov.hw_6.function1.Function1;

/**
 * This class implements predicate.
 *
 * @param <T> is the type of the argument.
 */
public interface Predicate<T> extends Function1<T, Boolean> {

    /**
     * This method make new predicate f || p.
     *
     * @param p is predicate.
     * @return f || p.
     */
    default Predicate<T> or(Predicate<T> p) {
        return t -> apply(t) || p.apply(t);
    }

    /**
     * This method make new predicate f && p.
     *
     * @param p is predicate.
     * @return f && p.
     */
    default Predicate<T> and(Predicate<T> p) {
        return t -> apply(t) && p.apply(t);
    }

    /**
     * This method make new predicate !f.
     *
     * @return !f.
     */
    default Predicate<T> not() {
        return t -> !apply(t);
    }

    default Predicate<T> alwaysTrue() {
        return t -> true;
    }

    default Predicate<T> alwaysFalse() {
        return t -> false;
    }

}
