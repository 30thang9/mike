package com.nth.mike.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "orderDate")
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @Column(name = "totalAmount")
    private Double totalAmount;
    @Column(name = "orderStatus", nullable = false, columnDefinition = "ENUM('ORDER', 'HANDLE_PAYMENT', 'PENDING', 'UNPAID', 'PREPAY', 'PAID', 'CANCELLED') DEFAULT 'ORDER'")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ORDER;
    @Column(name = "paymentMethod", columnDefinition = "ENUM('CASH', 'CREDIT_CARD', 'PAYPAL', 'OTHER')")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @ManyToOne(optional = true)
    @JoinColumn(name = "deliveryId", referencedColumnName = "id")
    private Delivery delivery;
    @Column(name = "fullName", columnDefinition = "NVARCHAR(255)")
    private String fullName;
    @Column(name = "email")
    private String email;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
    @ManyToOne(optional = true)
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    private Account customer;
    @ManyToOne(optional = true)
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Account employee;
}
