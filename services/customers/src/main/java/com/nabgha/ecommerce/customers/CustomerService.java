package com.nabgha.ecommerce.customers;


import com.nabgha.ecommerce.exceptions.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        var customer = mapper.toCustomer(request);
        var savedCustomer = customerRepository.save(customer);
        return mapper.toCustomerResponse(savedCustomer);
    }

    @Transactional
    public CustomerResponse updateCustomer(CustomerRequest request, String customerId) {
        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        if (request.firstName() != null) {
            customer.setFirstName(request.firstName());
        }
        if (request.lastName() != null) {
            customer.setLastName(request.lastName());
        }
        if (request.email() != null) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }

        var updatedCustomer = customerRepository.save(customer);
        return mapper.toCustomerResponse(updatedCustomer);
    }

    public Page<CustomerResponse> findAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(mapper::toCustomerResponse);
    }

    public CustomerResponse findCustomerById(String customerId) {
        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return mapper.toCustomerResponse(customer);
    }

    public Boolean existsById(String customerId) {
        return customerRepository.findById(customerId).isPresent();
    }

    @Transactional
    public void delete(String customerId) {
        if (!existsById(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        customerRepository.deleteById(customerId);
    }
}
