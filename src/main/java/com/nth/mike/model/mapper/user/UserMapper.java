package com.nth.mike.model.mapper.user;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.Role;
import com.nth.mike.model.dto.user.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserDTO toUserDTO(Account account, List<Role> roles) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(account.getId());
        userDTO.setUsername(account.getUsername());
        userDTO.setAccountStatus(account.getAccountStatus());
        userDTO.setRoles(roles);
        return userDTO;
    }

    public static Account toAccount(UserDTO userDTO) {
        Account account = new Account();
        account.setId(userDTO.getId());
        account.setUsername(userDTO.getUsername());
        account.setAccountStatus(userDTO.getAccountStatus());
        return account;
    }

    public static List<Role> Role(UserDTO userDTO) {
        List<Role> roles = new ArrayList<>();
        roles = userDTO.getRoles();
        return roles;
    }
}
