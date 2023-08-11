package com.nth.mike.entity;

import java.time.LocalDateTime;
import java.util.Date;

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

    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Account account;

    @Column(name = "orderDate")
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(name = "totalAmount")
    private Double totalAmount;

    @Column(name = "orderStatus", nullable = false, columnDefinition = "ENUM('PURCHASED', 'NOT_PURCHASED') DEFAULT 'NOT_PURCHASED'")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.NOT_PURCHASED;

    @Column(name = "updateDate", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.DATE)
    private Date updateDate;

    // Constructors, Getters, and Setters
}
