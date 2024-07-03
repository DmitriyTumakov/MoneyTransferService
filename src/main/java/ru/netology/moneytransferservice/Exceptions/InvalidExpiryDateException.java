package ru.netology.moneytransferservice.Exceptions;

public class InvalidExpiryDateException extends RuntimeException {
    public InvalidExpiryDateException(String message) {
        super(message);
    }
}
