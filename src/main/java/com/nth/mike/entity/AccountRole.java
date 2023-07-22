package com.nth.mike.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "accountRoles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRole {

    @EmbeddedId
    private AccountRoleId id;

    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id",insertable = false, updatable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id",insertable = false, updatable = false)
    private Role role;
}
