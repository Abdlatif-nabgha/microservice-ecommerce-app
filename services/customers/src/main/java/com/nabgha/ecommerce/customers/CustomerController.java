package com.nabgha.ecommerce.customers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
class CustomerController {

    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(request));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @RequestBody @Valid CustomerRequest request,
            @PathVariable String customerId
    ){
        return ResponseEntity
                .ok(customerService.updateCustomer(request, customerId));
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> findAllCustomers(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
    ) {
        return ResponseEntity
                .ok(customerService.findAllCustomers(PageRequest.of(page, size)));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> findCustomerById(
            @PathVariable String customerId
    ){
        return ResponseEntity
                .ok(customerService.findCustomerById(customerId));
    }

    @GetMapping("/exists/{customerId}")
    public ResponseEntity<Boolean> existsById(
            @PathVariable String customerId
    ){
        return ResponseEntity
                .ok(customerService.existsById(customerId));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(
            @PathVariable String customerId
    ){
        customerService.delete(customerId);
        return ResponseEntity.accepted().build();
    }
}
