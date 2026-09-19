package com.nabgha.ecommerce.customers;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        @NotBlank(message = "Customer fist name is required")
        String firstName,
        @NotBlank(message = "Customer last name is required")
        String lastName,
        @NotBlank(message = "Customer email name is required")
        @Email(message = "email is not valid, Please enter a valid email address")
        String email,
        Address address
) {
}
