package com.nabgha.ecommerce.exceptions;


public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super("category not found with id: " + message);
    }
}
