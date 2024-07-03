package ru.netology.moneytransferservice.Exceptions;

public class InvalidCVVException extends RuntimeException {
    public InvalidCVVException(String message) { super(message); }
}
