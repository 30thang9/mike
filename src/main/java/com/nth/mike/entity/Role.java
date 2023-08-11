package com.nth.mike.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // @Column(name = "name", unique = true, nullable = false)
    // private String name;

    @Column(name = "name", nullable = false, columnDefinition = "ENUM('ROLE_ADMIN_CUSTOMER_SERVICE', 'ROLE_ADMIN_FINANCE', 'ROLE_ADMIN_INVENTORY', 'ROLE_ADMIN_SALES', 'ROLE_ADMIN_SUPER', 'ROLE_USER') DEFAULT 'ROLE_USER'")
    @Enumerated(EnumType.STRING)
    private RoleName name = RoleName.ROLE_USER;
}
