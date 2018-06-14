package ru.spbau.egorov.hw_3.FTP;

/**
 * Represents types for FTP requests. 1 for list request and 2 for get request.
 */
public enum RequestType {
    LIST_REQUEST(1), GET_REQUEST(2);
    private final int code;

    RequestType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
