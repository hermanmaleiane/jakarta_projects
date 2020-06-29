package com.challenge.herman.exception;

public class NumberGeneratorException extends RuntimeException {

    public NumberGeneratorException(String message) {
        super(message);
    }

    public NumberGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NumberGeneratorException(Throwable cause) {
        super(cause);
    }
}