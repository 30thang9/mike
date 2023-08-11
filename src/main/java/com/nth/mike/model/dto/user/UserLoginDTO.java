package com.nth.mike.model.dto.user;

import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;

import com.nth.mike.entity.AccountStatus;
import com.nth.mike.entity.Role;
import com.nth.mike.model.other.AuthenProvider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginDTO {
    private Long id;
    private String username;
    private String fullName;
    private AccountStatus accountStatus;
    private AuthenProvider authenProvider;
    private List<Role> roles;

}
