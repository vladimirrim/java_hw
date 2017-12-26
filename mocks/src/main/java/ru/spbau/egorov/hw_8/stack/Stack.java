package ru.spbau.egorov.hw_8.stack;

/**
 * This class implements stack on linked list.
 *
 * @param <E> is type of contained elements.
 */
public class Stack<E> {
    private int n = 0;
    private Node first = null;

    /**
     * Checks if the stack is empty.
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of elements in stack.
     */
    public int size() {
        return n;
    }


    /**
     * Returns the last added item.
     */
    public E peek() {
        return first.value;
    }


    /**
     * Adds element to stack.
     */
    public void push(E elem) {
        Node oldFirst = first;

        first = new Node();
        first.value = elem;
        first.next = oldFirst;

        n++;
    }

    /**
     * Removes and returns the last added element.
     */
    public E pop() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException();
        E value = first.value;
        first = first.next;
        n--;

        return value;
    }

    private class Node {
        private E value;
        private Node next;
    }

}