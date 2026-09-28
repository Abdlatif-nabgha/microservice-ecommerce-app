package com.nabgha.ecommerce.products;


import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecifications {

    private ProductSpecifications() {}

    public static Specification<Product> hasCategory(String categoryId) {
        return (root, query, criteriaBuilder) -> categoryId == null || categoryId.isBlank()
                ? null
                : criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> priceAtLeast(BigDecimal min) {
        return (root, query, criteriaBuilder) -> min == null
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min);
    }

    public static Specification<Product> priceAtMost(BigDecimal max) {
        return (root, query, cb) -> max == null ? null : cb.lessThanOrEqualTo(root.get("price"), max);
    }
}
