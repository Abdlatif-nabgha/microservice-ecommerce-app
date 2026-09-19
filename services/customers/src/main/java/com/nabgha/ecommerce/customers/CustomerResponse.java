package com.nabgha.ecommerce.customers;


import lombok.Builder;

@Builder
public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
