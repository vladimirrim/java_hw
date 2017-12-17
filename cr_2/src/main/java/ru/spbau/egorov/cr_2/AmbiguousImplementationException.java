package ru.spbau.egorov.cr_2;

public class AmbiguousImplementationException extends Exception {
    public AmbiguousImplementationException(){
        super("Found more then 1 implementation for interface or abstract class.");
    }
}
