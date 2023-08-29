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

    @Column(name = "accountStatus", nullable = false, columnDefinition = "ENUM('PENDING', 'ACTIVE', 'INACTIVE', 'LOCKED') DEFAULT 'PENDING'")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.PENDING;

    @Column(name = "fullName", columnDefinition = "NVARCHAR(255) NULL")
    private String fullName;

    @Column(name = "reset_code", unique = true)
    private String resetCode;

    @Column(name = "reset_code_expiry_time")
    private Date resetCodeExpiryTime;

}
