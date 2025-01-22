/**
 * 
 */
package com.rabobank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 
 */


@ControllerAdvice
public class GlobalExceptionHandler {
// Handle InsufficientBalanceException
@ExceptionHandler(InsufficientBalanceException.class)
public ResponseEntity<String> handleInsufficientBalance(InsufficientBalanceException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
}

// Handle AccountNotFoundException
@ExceptionHandler(AccountNotFoundException.class)
public ResponseEntity<String> handleAccountNotFound(AccountNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
}

// Handle InvalidCardException
@ExceptionHandler(InvalidCardException.class)
public ResponseEntity<String> handleInvalidCard(InvalidCardException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
}

// General Exception Handler
@ExceptionHandler(Exception.class)
public ResponseEntity<String> handleGeneralException(Exception ex) {
    return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
}
}