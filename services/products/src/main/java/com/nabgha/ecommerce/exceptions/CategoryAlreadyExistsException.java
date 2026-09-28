package com.nabgha.ecommerce.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String name) {
        super("category already exists with name: " + name);
    }
}
