package com.nth.mike.model.dto;

import com.nth.mike.entity.AccountStatus;
import com.nth.mike.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private AccountStatus accountStatus;
    private List<Role> roles;
}
