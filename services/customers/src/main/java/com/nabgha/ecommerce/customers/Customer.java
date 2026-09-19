package com.nabgha.ecommerce.customers;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;

    @CreatedDate
    private LocalDateTime createdAt;
}
