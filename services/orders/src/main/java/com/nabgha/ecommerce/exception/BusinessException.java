package com.nabgha.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    private String message;

    public BusinessException(String cannotCreateOrder) {

    }
}
