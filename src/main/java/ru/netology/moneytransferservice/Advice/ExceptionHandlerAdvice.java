package ru.netology.moneytransferservice.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.moneytransferservice.Exceptions.CardNotFoundException;
import ru.netology.moneytransferservice.Exceptions.IncorrectCodeException;
import ru.netology.moneytransferservice.Exceptions.InvalidCVVException;
import ru.netology.moneytransferservice.Exceptions.InvalidExpiryDateException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<Object> CardNotFound(CardNotFoundException e) {
        return new ResponseEntity<>("CardNotFoundException", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectCodeException.class)
    public ResponseEntity<Object> IncorrectCode(IncorrectCodeException e) {
        return new ResponseEntity<>("IncorrectCodeException", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCVVException.class)
    public ResponseEntity<Object> InvalidCVV(InvalidCVVException e) {
        return new ResponseEntity<>("InvalidCVVException", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidExpiryDateException.class)
    public ResponseEntity<Object> InvalidExpiryDate(InvalidExpiryDateException e) {
        return new ResponseEntity<>("InvalidExpiryDateException", HttpStatus.BAD_REQUEST);
    }
}
