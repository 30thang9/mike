package com.nth.mike.model.mapper.user;

import java.util.List;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.Role;
import com.nth.mike.model.dto.user.UserLoginDTO;
import com.nth.mike.model.other.AuthenProvider;

public class UserLoginMapper {
    public static UserLoginDTO toUserLoginDTO(Account account, List<Role> roles, AuthenProvider auth) {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setId(account.getId());
        userLoginDTO.setUsername(account.getUsername());
        userLoginDTO.setFullName(account.getFullName());
        userLoginDTO.setAccountStatus(account.getAccountStatus());
        userLoginDTO.setRoles(roles);
        userLoginDTO.setAuthenProvider(auth);
        return userLoginDTO;
    }
}
