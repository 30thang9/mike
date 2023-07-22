package com.nth.mike.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "saleDate")
    @Temporal(TemporalType.DATE)
    private Date saleDate;

    @Column(name = "totalAmount")
    private Double totalAmount;

    @Column(name = "saleStatus")
    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @Column(name = "paymentMethod", nullable = false, columnDefinition = "ENUM('CASH', 'CREDIT_CARD', 'PAYPAL', 'OTHER')")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(optional = true)
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Employee employee;

    // Getters and Setters
}
