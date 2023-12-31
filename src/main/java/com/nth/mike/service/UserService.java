package com.nth.mike.service;

import com.nth.mike.entity.*;
import com.nth.mike.model.dto.user.UserDTO;
import com.nth.mike.model.dto.user.UserLoginDTO;
import com.nth.mike.model.other.AuthenProvider;

import java.util.List;

public interface UserService {
    Account saveAccount(Account account);

    Account saveAccountHashPass(Account account);

    Long deleteAccount(Long accountId);

    Role saveRole(Role role);

    AccountRole addRoleToUser(String username, RoleName roleName);

    Account findAccountByUserName(String username);

    Account findAccountById(Long id);

    List<Account> findAllAccount();

    List<Account> findAccountByRole(Role role);

    List<Role> findRoleByAccount(Account account);

    Role findRoleByName(RoleName roleName);
    List<UserDTO> findAllUser();

    UserDTO findUserByAccount(Account account);

    UserLoginDTO findUserLoginByAccount(Account account, AuthenProvider auth);

    void activeAccount(Account account);

    //
    void storeResetCode(String email, String resetCode);

    void storeAcceptPassCode(String email, String resetCode);

    boolean isValidResetCode(String resetCode);

    boolean isResetCodeExpired(String resetCode);

    void changePassword(String email, String newPassword);

    Account findByResetCode(String resetCode);

    void removeCodeByExpired();

}
