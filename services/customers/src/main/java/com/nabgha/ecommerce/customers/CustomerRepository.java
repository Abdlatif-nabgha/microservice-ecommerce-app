package com.nabgha.ecommerce.customers;

import org.springframework.data.mongodb.repository.MongoRepository;


interface CustomerRepository extends MongoRepository<Customer,String> {

}
