package ru.spbau.egorov.hw_7.set;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * This class implements binary search tree of comparable elements.All elements are different.
 *
 * @param <T> type of contained elements.
 */
public class Set<T> {
    private int size;
    private Node root;

    private final Comparator<? super T> comparator;

    public Set() {
        root = new Node(null, null, null);
        comparator = null;
    }


    public Set(@NotNull Comparator<? super T> cmp) {
        root = new Node(null, null, null);
        comparator = cmp;
    }


    private class Node {
        private Node left;
        private Node right;
        private T value;

        private Node(Node l, Node r, T val) {
            left = l;
            right = r;
            value = val;
        }
    }

    /**
     * Get node in the tree if the value t is in tree or create new node to write new value in it.
     */
    private Node prepareNode(T t, Node cur) {
        if (cur.value == null)
            return cur;

        if (comparator == null) {
            Comparable<? super T> cmpt = (Comparable<? super T>) t;

            if (cmpt.compareTo(cur.value) == 0)
                return cur;

            if (cmpt.compareTo(cur.value) > 0) {
                if (cur.right == null)
                    cur.right = new Node(null, null, null);
                return prepareNode(t, cur.right);
            }

            if (cur.left == null)
                cur.left = new Node(null, null, null);
        } else {
            if (comparator.compare(t, cur.value) == 0)
                return cur;

            if (comparator.compare(t, cur.value) > 0) {
                if (cur.right == null)
                    cur.right = new Node(null, null, null);
                return prepareNode(t, cur.right);
            }

            if (cur.left == null)
                cur.left = new Node(null, null, null);
        }

        return prepareNode(t, cur.left);
    }

    /**
     * Add element to the tree.If element was in the tree, replace it with new value.
     */
    public boolean add(@NotNull T t) {
        if (!contains(t))
            size++;
        Node node = prepareNode(t, root);
        T oldValue = node.value;
        node.value = t;
        return oldValue == null;
    }

    /**
     * Check if the element is in tree.
     */
    public boolean contains(@NotNull T t) {
        return prepareNode(t, root).value != null;
    }

    /**
     * Return current number of elements in the tree.
     */
    public int size() {
        return size;
    }

    public T ceiling(@NotNull T t) {
        return ceiling(t, root);
    }

    private T ceiling(T t, Node cur) {
        if (cur.value == null)
            return null;

        if (comparator == null ? ((Comparable<? super T>) t).compareTo(cur.value) <= 0 : comparator.compare(t, cur.value) <= 0)
            if (cur.left == null)
                return cur.value;
            else {
                T rval = ceiling(t, cur.left);
                if (rval == null)
                    return cur.value;
                else
                    return rval;
            }


        if (cur.right == null)
            return null;
        else
            return ceiling(t, cur.right);

    }

    public T higher(@NotNull T t) {
        return higher(t, root);
    }

    private T higher(T t, Node cur) {
        if (cur.value == null)
            return null;

        if (comparator == null ? ((Comparable<? super T>) t).compareTo(cur.value) < 0 : comparator.compare(t, cur.value) < 0)
            if (cur.left == null)
                return cur.value;
            else {
                T rval = higher(t, cur.left);
                if (rval == null)
                    return cur.value;
                else
                    return rval;
            }


        if (cur.right == null)
            return null;
        else
            return higher(t, cur.right);

    }

    public T floor(@NotNull T t) {
        return floor(t, root);
    }

    private T floor(T t, Node cur) {
        if (cur.value == null)
            return null;

        if (comparator == null ? ((Comparable<? super T>) t).compareTo(cur.value) >= 0 : comparator.compare(t, cur.value) >= 0)
            if (cur.right == null)
                return cur.value;
            else {
                T rval = floor(t, cur.right);
                if (rval == null)
                    return cur.value;
                else
                    return rval;
            }

        if (cur.left == null)
            return null;
        else
            return floor(t, cur.left);

    }

    public T lower(@NotNull T t) {
        return lower(t, root);
    }

    private T lower(T t, Node cur) {
        if (cur.value == null)
            return null;

        if (comparator == null ? ((Comparable<? super T>) t).compareTo(cur.value) > 0 : comparator.compare(t, cur.value) > 0)
            if (cur.right == null)
                return cur.value;
            else {
                T rval = lower(t, cur.right);
                if (rval == null)
                    return cur.value;
                else
                    return rval;
            }

        if (cur.left == null)
            return null;
        else
            return lower(t, cur.left);

    }
}