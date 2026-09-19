package com.nabgha.ecommerce.products;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByIdInOrderById(Collection<String> id);
}
