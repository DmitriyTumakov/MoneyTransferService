package ru.netology.moneytransferservice.Exceptions;

public class IncorrectCodeException extends RuntimeException {
    public IncorrectCodeException(String message) {
        super(message);
    }
}
