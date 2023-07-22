package com.nth.mike.entity;

import java.time.LocalDateTime;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customerId")
    private Long customerId;

    @Column(name = "orderDate")
    private LocalDateTime orderDate;

    @Column(name = "totalAmount")
    private Double totalAmount;

    @Column(name = "orderStatus", nullable = false, columnDefinition = "ENUM('PURCHASED', 'NOT_PURCHASED') DEFAULT 'NOT_PURCHASED'")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "updateDate", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateDate;

    // Constructors, Getters, and Setters
}
