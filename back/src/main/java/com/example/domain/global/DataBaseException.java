package com.example.domain.global;

public class DataBaseException extends RuntimeException {
    public DataBaseException() {
        super();
    }

    public DataBaseException(String message) {
        super(message);
    }
}
