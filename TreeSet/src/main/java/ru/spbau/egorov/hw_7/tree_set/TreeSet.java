package ru.spbau.egorov.hw_7.tree_set;

import org.jetbrains.annotations.NotNull;
import ru.spbau.egorov.hw_7.set.Set;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;

public class TreeSet<E> extends AbstractSet<E> implements MyTreeSet<E> {
    private Set<E> tree;
    private final Comparator<? super E> comparator;
    private E first, last;

    public TreeSet() {
        comparator = null;
        tree = new Set<>();
    }


    public TreeSet(Comparator<? super E> cmp) {
        comparator = cmp;
        tree = new Set<>(cmp);
    }


    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingTreeIterator(last());
    }


    @Override
    public MyTreeSet<E> descendingSet() {
        return new TreeSet<E>() {
            @Override
            public E first() {
                return TreeSet.this.last;
            }


            @Override
            public E last() {
                return TreeSet.this.first;
            }


            @Override
            public E lower(@NotNull E e) {
                return TreeSet.this.tree.higher(e);
            }


            @Override
            public E floor(@NotNull E e) {
                return TreeSet.this.tree.ceiling(e);
            }

            @Override
            public E ceiling(@NotNull E e) {
                return TreeSet.this.tree.floor(e);
            }


            @Override
            public E higher(@NotNull E e) {
                return TreeSet.this.tree.lower(e);
            }

            @NotNull
            @Override
            public Iterator<E> iterator() {
                return new TreeIterator(first());
            }

            @Override
            public Iterator<E> descendingIterator() {
                return new DescendingTreeIterator(last());
            }
        };

    }


    @Override
    public E first() {
        return first;
    }


    @Override
    public E last() {
        return last;
    }


    @Override
    public E lower(@NotNull E e) {
        return tree.lower(e);
    }


    @Override
    public E floor(@NotNull E e) {
        return tree.floor(e);
    }


    @Override
    public E ceiling(@NotNull E e) {
        return tree.ceiling(e);
    }


    @Override
    public E higher(@NotNull E e) {
        return tree.higher(e);
    }

    @Override
    public boolean add(@NotNull E e) {
        if (first == null || (comparator == null ? ((Comparable<? super E>) e).compareTo(first) < 0 : comparator.compare(e, first) < 0))
            first = e;

        if (last == null || (comparator == null ? ((Comparable<? super E>) e).compareTo(last) > 0 : comparator.compare(e, last) > 0))
            last = e;

        return tree.add(e);
    }

    @Override
    public boolean contains(Object e) {
        return tree.contains((E) e);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new TreeIterator(first);
    }

    @Override
    public int size() {
        return tree.size();
    }

    private class DescendingTreeIterator implements Iterator<E> {
        private E value;

        private DescendingTreeIterator(E val) {
            value = val;
        }

        @Override
        public boolean hasNext() {
            return value != null;
        }

        @Override
        public E next() {
            E oldValue = value;
            value = lower(value);
            return oldValue;
        }
    }

    private class TreeIterator implements Iterator<E> {
        private E value;

        private TreeIterator(E val) {
            value = val;
        }

        @Override
        public boolean hasNext() {
            return value != null;
        }

        @Override
        public E next() {
            E oldValue = value;
            value = higher(value);
            return oldValue;
        }
    }

}
