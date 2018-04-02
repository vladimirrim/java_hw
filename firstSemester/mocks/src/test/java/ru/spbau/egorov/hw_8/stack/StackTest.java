package ru.spbau.egorov.hw_8.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {
    @Test
    void isEmptyZeroElements() {
        Stack<Integer> stack = new Stack<>();
        assertTrue(stack.isEmpty());
    }

    @Test
    void isEmptyThousandElements() {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 1000; i++)
            stack.push(i);
        assertFalse(stack.isEmpty());
    }

    @Test
    void sizeZeroElements() {
        Stack<Integer> stack = new Stack<>();
        assertEquals(0, stack.size());
    }

    @Test
    void sizeThousandElements() {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 1000; i++)
            stack.push(i);
        assertEquals(1000, stack.size());
    }

    @Test
    void peekOneElement() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        assertEquals((Integer) 1, stack.peek());
    }

    @Test
    void peekAfterPop() throws EmptyStackException {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 1000; i++)
            stack.push(i);
        for (int i = 0; i < 500; i++)
            stack.pop();
        assertEquals((Integer) 499, stack.peek());
    }

    @Test
    void popThousandElements() throws EmptyStackException {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 1000; i++)
            stack.push(i);
        for (int i = 0; i < 1000; i++)
            assertEquals((Integer) (1000 - i - 1), stack.pop());
    }

    @Test
    void popEmptyStackException() {
        assertThrows(EmptyStackException.class, () -> (new Stack<>()).pop());
    }

}