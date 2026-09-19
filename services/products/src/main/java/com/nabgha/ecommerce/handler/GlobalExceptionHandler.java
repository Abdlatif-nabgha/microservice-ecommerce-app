package com.nabgha.ecommerce.handler;

import com.nabgha.ecommerce.exceptions.ProductNotFoundException;
import com.nabgha.ecommerce.exceptions.ProductPurchaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException e) {
        return new ResponseEntity<>(
                new ErrorResponse(Map.of("error", e.getMessage())),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ProductPurchaseException.class)
    public ResponseEntity<ErrorResponse> handleProductPurchaseException(ProductPurchaseException e) {
        return new ResponseEntity<>(
                new ErrorResponse(Map.of("error", e.getMessage())),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e) {

        var errors = new HashMap<String, String>();
        e.getBindingResult().getAllErrors()
                .forEach(error -> {
                    if (error instanceof FieldError fieldError) {
                        errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                    } else {
                        errors.put(error.getObjectName(), error.getDefaultMessage());
                    }
                });

        return new ResponseEntity<>(
                new ErrorResponse(errors),
                HttpStatus.BAD_REQUEST
        );
    }
}
