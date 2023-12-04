package com.nth.mike.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shippingAddress")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "fullName", columnDefinition = "NVARCHAR(255)")
    private String fullName;
    @Column(name = "email")
    private String email;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "city", columnDefinition = "NVARCHAR(255)")
    private String city;
    @Column(name = "district", columnDefinition = "NVARCHAR(255)")
    private String district;
    @Column(name = "ward", columnDefinition = "NVARCHAR(255)")
    private String ward;
    @Column(name = "street", columnDefinition = "NVARCHAR(255)")
    private String street;
    @Column(name = "addressDefault", nullable = false, columnDefinition = "BIT DEFAULT 0")
    private Boolean addressDefault = false;
    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id", insertable = false, updatable = false)
    private Account account;
}
