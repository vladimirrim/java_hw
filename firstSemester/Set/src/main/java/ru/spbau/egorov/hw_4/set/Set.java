package ru.spbau.egorov.hw_4.set;


import org.jetbrains.annotations.NotNull;

/**
 * This class implements binary search tree of comparable elements.All elements are different.
 *
 * @param <T> type of contained elements.
 */
public class Set<T extends Comparable<T>> {
    private int size;
    private Node root;

    public Set() {
        root = new Node(null, null, null);
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

        if (t.compareTo(cur.value) == 0)
            return cur;

        if (t.compareTo(cur.value) < 0) {
            if (cur.right == null)
                cur.right = new Node(null, null, null);
            return prepareNode(t, cur.right);
        }

        if (cur.left == null)
            cur.left = new Node(null, null, null);

        return prepareNode(t, cur.left);
    }

    /**
     * Add element to the tree.If element was in the tree, replace it with new value.
     */
    public void add(@NotNull T t) {
        if (!contains(t))
            size++;
        prepareNode(t, root).value = t;
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
}
