package com.nabgha.ecommerce.category;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_category_name", columnNames = "name"))
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String name;
    private String description;

}
