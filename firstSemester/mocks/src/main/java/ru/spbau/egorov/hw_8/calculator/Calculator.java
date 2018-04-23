package ru.spbau.egorov.hw_8.calculator;

import java.util.Arrays;

import org.jetbrains.annotations.NotNull;
import ru.spbau.egorov.hw_8.stack.EmptyStackException;
import ru.spbau.egorov.hw_8.stack.Stack;

import java.util.function.*;

/**
 * This class implements calculator that parse string with the expression in Reverse Polish notation.
 */
public class Calculator {


    private final Stack<Double> numbers;
    private final Stack<Character> signs;

    public Calculator(Stack<Double> stack1, Stack<Character> stack2) {
        numbers = stack1;
        signs = stack2;
    }

    /**
     * Evaluates expression in Reverse Polish notation.
     *
     * @param input is string with expression.
     * @return calculation result.
     */
    public Double calc(String input) throws CalculationFailedException {
        try {
            input = toRPN(input);
        } catch (EmptyStackException e) {
            e.printStackTrace();
            throw new CalculationFailedException(e);
        }
        Arrays.stream(input.split(" ")).forEach(number -> {
            switch (number) {
                case "+":
                    calcSign(numbers, (n1, n2) -> n2 + n1);
                    break;
                case "-":
                    calcSign(numbers, (n1, n2) -> n2 - n1);
                    break;
                case "*":
                    calcSign(numbers, (n1, n2) -> n2 * n1);
                    break;
                case "/":
                    calcSign(numbers, (n1, n2) -> n2 / n1);
                    break;
                default:
                    numbers.push(Double.parseDouble(number));
            }
        });
        try {
            return numbers.pop();
        } catch (EmptyStackException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void calcSign(Stack<Double> numbers, BiFunction<Double, Double, Double> operation) {
        try {
            numbers.push(operation.apply(numbers.pop(), numbers.pop()));
        } catch (EmptyStackException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private String toRPN(String input) throws EmptyStackException {
        StringBuilder output = new StringBuilder(100);
        for (int i = 0; i < input.length(); i++)
            switch (input.charAt(i)) {
                case '+':
                case '-':
                    while (!signs.isEmpty() && (signs.peek() == '*' || signs.peek() == '/')) {
                        output.append(' ');
                        output.append(signs.pop());
                    }
                    output.append(' ');
                    signs.push(input.charAt(i));
                    break;
                case '*':
                case '/':
                    output.append(' ');
                    signs.push(input.charAt(i));
                    break;
                case '(':
                    signs.push(input.charAt(i));
                    break;
                case ')':
                    while (!signs.isEmpty() && signs.peek() != '(') {
                        output.append(' ');
                        output.append(signs.pop());
                    }
                    signs.pop();
                    break;
                default:
                    output.append(input.charAt(i));
                    break;
            }

        while (!signs.isEmpty())
            output.append(' ').append(signs.pop());
        return output.toString();
    }


    public static void main(String[] args) {
        Calculator c = new Calculator(new Stack<>(), new Stack<>());
        try {
            System.out.println(c.calc(args[0]));
        } catch (CalculationFailedException e) {
            e.printStackTrace();
        }
    }

}
