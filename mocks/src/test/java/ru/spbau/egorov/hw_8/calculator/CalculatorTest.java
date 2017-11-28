package ru.spbau.egorov.hw_8.calculator;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import ru.spbau.egorov.hw_8.stack.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CalculatorTest {
    @Test
    void calcSmallExpression() {
        Stack<Double> doubleMock = mock(Stack.class);
        when(doubleMock.pop()).thenReturn(200.0, 200.0, 400.0, 200.0, 600.0);
        Stack<Character> charMock = mock(Stack.class);
        when(charMock.peek()).thenReturn('+');
        when(charMock.pop()).thenReturn('+');
        when(charMock.isEmpty()).thenReturn(false, false, false, false, false, true);
        Calculator calc = new Calculator(doubleMock, charMock);
        assertEquals((Double) 600.0, calc.calc("200+200+200"));
        verify(charMock, times(3)).pop();

        InOrder inOrder = inOrder(doubleMock);
        inOrder.verify(doubleMock, times(3)).push(200.0);
        inOrder.verify(doubleMock).push(400.0);
        inOrder.verify(doubleMock).push(600.0);
    }

    @Test
    void calcAllSignsDifferentNumbers() {
        Stack<Double> doubleMock = mock(Stack.class);
        when(doubleMock.pop()).thenReturn(6.0, 8.0, 2.0, 4.0, 2.0, 2.0, 4.0, 7.0, 11.0);
        Stack<Character> charMock = mock(Stack.class);
        when(charMock.pop()).thenReturn('-', '/', '/', '*', '+');
        when(charMock.peek()).thenReturn('*');
        when(charMock.isEmpty()).thenReturn(true, false, true, false, false, true, false, true);
        Calculator calc = new Calculator(doubleMock, charMock);
        assertEquals((Double) 11.0, calc.calc("2*4/(8-6)+7"));
        verify(charMock, times(5)).pop();

        InOrder inOrder = inOrder(doubleMock);
        inOrder.verify(doubleMock).push(2.0);
        inOrder.verify(doubleMock).push(4.0);
        inOrder.verify(doubleMock).push(8.0);
        inOrder.verify(doubleMock).push(6.0);
        inOrder.verify(doubleMock).push(2.0);
        inOrder.verify(doubleMock).push(2.0);
        inOrder.verify(doubleMock).push(4.0);
        inOrder.verify(doubleMock).push(7.0);
        inOrder.verify(doubleMock).push(11.0);

    }

}