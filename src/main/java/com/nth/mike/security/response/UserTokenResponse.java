package com.nth.mike.security.response;

import com.nth.mike.entity.AccountStatus;
import com.nth.mike.entity.Role;
import com.nth.mike.model.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenResponse {
    private Long id;
    private String username;
    private AccountStatus accountStatus;
    private List<Role> roles;
    private String accessToken;
    private String refreshToken;

    public UserTokenResponse(UserDTO user, String accessToken, String refreshToken) {
        this.id = user.getId();
        this.username=user.getUsername();
        this.accountStatus=user.getAccountStatus();
        this.roles=user.getRoles();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
