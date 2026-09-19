package com.nabgha.ecommerce.products;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Category {

    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Product> products;
}
