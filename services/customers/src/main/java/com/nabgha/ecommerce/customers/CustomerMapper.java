package com.nabgha.ecommerce.customers;

import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toCustomer(CustomerRequest request) {
        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .build();
    }
}
