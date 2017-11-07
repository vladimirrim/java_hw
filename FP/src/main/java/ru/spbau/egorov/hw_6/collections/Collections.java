package ru.spbau.egorov.hw_6.collections;

import ru.spbau.egorov.hw_6.function1.Function1;
import ru.spbau.egorov.hw_6.function2.Function2;
import ru.spbau.egorov.hw_6.predicate.Predicate;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * This class implements common functions in functional programming for Iterable collections.
 */
public class Collections {
    /**
     * This method apply function to all elements in the collection.
     *
     * @param f   is function to apply to elements in collection.
     * @param a   is collection.
     * @param <T> is return type of given function.
     * @param <E> is type of elements in collection.
     * @return collection with elements created by applying function to the previous elements.
     */
    public static <T, E> ArrayList<T> map(Function1<? super E, T> f, Iterable<E> a) {
        ArrayList<T> arr = new ArrayList<>();
        for (E elem : a) {
            arr.add(f.apply(elem));
        }
        return arr;
    }

    /**
     * This method take elements which satisfying predicate.
     *
     * @param p   is predicate to select elements.
     * @param a   is collection.
     * @param <E> is type of elements in collection.
     * @return collection with elements which satisfying predicate.
     */
    public static <E> ArrayList<E> filter(Predicate<? super E> p, Iterable<E> a) {
        ArrayList<E> arr = new ArrayList<>();
        for (E elem : a) {
            if (p.apply(elem))
                arr.add(elem);
        }
        return arr;
    }

    /**
     * This method take elements until element does not satisfy predicate.
     *
     * @param p   is predicate to select elements.
     * @param a   is collection.
     * @param <E> is type of elements in collection.
     * @return collection with elements from the start to the first element that does not satisfy predicate.
     */
    public static <E> ArrayList<E> takeWhile(Predicate<? super E> p, Iterable<E> a) {
        ArrayList<E> arr = new ArrayList<>();
        for (E elem : a) {
            if (!p.apply(elem))
                break;
            arr.add(elem);
        }
        return arr;
    }

    /**
     * This method take elements until element satisfy predicate.
     *
     * @param p   is predicate to select elements.
     * @param a   is collection.
     * @param <E> is type of elements in collection.
     * @return collection with elements from the start to the first element that does satisfy predicate.
     */
    public static <E> ArrayList<E> takeUnless(Predicate<? super E> p, Iterable<E> a) {
        ArrayList<E> arr = new ArrayList<>();
        for (E elem : a) {
            if (p.apply(elem))
                break;
            arr.add(elem);
        }
        return arr;
    }

    /**
     * This method apply function to element from collection and accumulated value, going from right to left.
     *
     * @param acc is function that accumulate value from collection.
     * @param a   is collection.
     * @param id  is initial value.
     * @param <E> is type of elements in collection.
     * @param <T> is type of accumulated value.
     * @return accumulated value from the collection.
     */
    public static <E, T> T foldr(Function2<T, ? super E, T> acc, Iterable<E> a, T id) {
        T val = id;
        ArrayList<E> arr = new ArrayList<>();
        for (E elem : a) {
            arr.add(elem);
        }
        ListIterator<E> it = arr.listIterator(arr.size());

        while (it.hasPrevious()) {
            val = acc.apply(it.previous(), val);
        }
        return val;
    }

    /**
     * This method apply function to element from collection and accumulated value, going from left to right.
     *
     * @param acc is function that accumulate value from collection.
     * @param a   is collection.
     * @param id  is initial value.
     * @param <E> is type of elements in collection.
     * @param <T> is type of accumulated value.
     * @return accumulated value from the collection.
     */
    public static <E, T> T foldl(Function2<T, T, ? super E> acc, Iterable<E> a, T id) {
        T val = id;
        for (E elem : a) {
            val = acc.apply(val, elem);
        }
        return val;
    }
}
