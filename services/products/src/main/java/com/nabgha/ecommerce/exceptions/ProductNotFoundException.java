package com.nabgha.ecommerce.exceptions;


public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super("product not found with id: " + message);
    }
}
