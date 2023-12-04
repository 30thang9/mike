package com.nth.mike.model.mapper.user;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.Role;
import com.nth.mike.model.dto.user.UserDTO;
import com.nth.mike.model.dto.user.UserLoginDTO;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserDTO toUserDTO(Account account, List<Role> roles) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(account.getId());
        userDTO.setUsername(account.getUsername());
        userDTO.setFullName(account.getFullName());
        userDTO.setDateOfBirth(account.getDateOfBirth());
        userDTO.setGender(account.getGender());
        userDTO.setEmail(account.getEmail());
        userDTO.setPhone(account.getPhone());
        userDTO.setAddress(account.getAddress());
        userDTO.setUrlAvatar(account.getUrlAvatar());
        userDTO.setAccountStatus(account.getAccountStatus());
        userDTO.setRoles(roles);
        return userDTO;
    }

    public static UserDTO toUserDTO(UserLoginDTO uld) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(uld.getId());
        userDTO.setUsername(uld.getUsername());
        userDTO.setFullName(uld.getFullName());
        userDTO.setAccountStatus(uld.getAccountStatus());
        userDTO.setRoles(uld.getRoles());
        return userDTO;
    }

    public static Account toAccount(UserDTO userDTO) {
        Account account = new Account();
        account.setId(userDTO.getId());
        account.setUsername(userDTO.getUsername());
        account.setFullName(userDTO.getFullName());
        account.setDateOfBirth(userDTO.getDateOfBirth());
        account.setGender(userDTO.getGender());
        account.setEmail(userDTO.getEmail());
        account.setPhone(userDTO.getPhone());
        account.setAddress(userDTO.getAddress());
        account.setUrlAvatar(userDTO.getUrlAvatar());
        account.setAccountStatus(userDTO.getAccountStatus());
        return account;
    }

    public static List<Role> Role(UserDTO userDTO) {
        List<Role> roles = new ArrayList<>();
        roles = userDTO.getRoles();
        return roles;
    }
}
