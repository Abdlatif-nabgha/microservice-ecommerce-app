package com.nabgha.ecommerce.payment;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "payments")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String orderId;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;
}
