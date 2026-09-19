package com.nabgha.ecommerce.products;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
public class Product {

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    private double availableQuantity;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
