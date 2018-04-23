package ru.spbau.egorov.hw_1;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * This class implements container with lazy evaluation for one thread.
 *
 * @param <T> is the type of stored element.
 */
public class OneThreadLazy<T> implements Lazy<T> {

    private AtomicReference<T> valRef;
    private Supplier<T> sup;


    public OneThreadLazy(Supplier<T> sup) {
        this.sup = sup;
    }

    public T get() {
        if (valRef == null) {
            valRef = new AtomicReference<>(sup.get());
        }
        return valRef.get();
    }
}
