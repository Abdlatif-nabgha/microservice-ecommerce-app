package com.nabgha.ecommerce.orderLines;

import com.nabgha.ecommerce.orders.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "orderlines")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private double quantity;
}
