package ru.spbau.egorov.hw_8.calculator;


/**
 * Calculator throws this exception if stack throws EmptyStackException.
 */
public class CalculationFailedException extends Exception{
    public CalculationFailedException(Throwable cause){
        super("Failed to calculate expression.", cause);
    }
}
