package com.nabgha.ecommerce.orders;

import com.nabgha.ecommerce.orderLines.OrderLine;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String id;
    private String reference;
    private BigDecimal totalAmount;

    @Enumerated(STRING)
    private PaymentMethod paymentMethod;
    private String customerId;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;
}
