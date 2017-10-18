package ru.spbau.egorov.hw_4.maybe;

public class MaybeNothingException extends Exception {
    public MaybeNothingException(){
        super("get() used with null in value.");
    }
}
