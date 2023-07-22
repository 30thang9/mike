package com.nth.mike.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRoleId implements Serializable {

    @Column(name = "accountId")
    private Long accountId;

    @Column(name = "roleId")
    private Long roleId;
}

