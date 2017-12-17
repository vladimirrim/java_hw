package ru.spbau.egorov.cr_2;

public class InjectionCycleException extends Exception{
    public InjectionCycleException(){
        super("Cycle appears in resolving classes dependencies.");
    }
}
