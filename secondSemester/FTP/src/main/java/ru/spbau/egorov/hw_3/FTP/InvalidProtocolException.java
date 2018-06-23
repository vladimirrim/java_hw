package ru.spbau.egorov.hw_3.FTP;


/**
 * This exception is thrown when the server or client requests don`t match with actual protocol.
 */
public class InvalidProtocolException extends Exception {
    public InvalidProtocolException() {
        super("Data doesn't match with the protocol.");
    }
}
