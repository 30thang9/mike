package com.nth.mike.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "fullName", columnDefinition = "NVARCHAR(255) NULL")
    private String fullName;
    @Column(name = "dateOfBirth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "ENUM('MALE', 'FEMALE')")
    private Gender gender;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "urlAvatar")
    private String urlAvatar;
    @Column(name = "accountStatus", nullable = false, columnDefinition = "ENUM('PENDING', 'ACTIVE', 'INACTIVE', 'LOCKED') DEFAULT 'PENDING'")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.PENDING;
//    @Column(name = "accountType", nullable = false, columnDefinition = "ENUM('CUSTOMER', 'EMPLOYEE', 'SUPPLIER')")
//    @Enumerated(EnumType.STRING)
//    private AccountType accountType = AccountType.CUSTOMER;
    @Column(name = "reset_code", unique = true)
    private String resetCode;
    @Column(name = "reset_code_expiry_time")
    private Date resetCodeExpiryTime;

}
