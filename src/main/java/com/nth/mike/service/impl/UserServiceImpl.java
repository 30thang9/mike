package com.nth.mike.service.impl;

import com.nth.mike.entity.*;
import com.nth.mike.model.dto.user.UserDTO;
import com.nth.mike.model.dto.user.UserLoginDTO;
import com.nth.mike.model.mapper.user.UserLoginMapper;
import com.nth.mike.model.mapper.user.UserMapper;
import com.nth.mike.model.other.AuthenProvider;
import com.nth.mike.repository.AccountRepo;
import com.nth.mike.repository.AccountRoleRepo;
import com.nth.mike.repository.RoleRepo;
import com.nth.mike.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final AccountRepo accountRepo;
    private final RoleRepo roleRepo;
    private final AccountRoleRepo accountRoleRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${spring.forgotPass.timeResetCode}")
    private int timeResetCode;
    @Value("${spring.forgotPass.timeAcceptPass}")
    private int timeAcceptPass;

    @Override
    public Account saveAccount(Account account) {
        return accountRepo.save(account);
    }

    @Override
    public Account saveAccountHashPass(Account account) {
        account.setPassword(encodePassword(account.getPassword()));
        return accountRepo.save(account);
    }

    @Override
    public Long deleteAccount(Long accountId) {
        try {
            accountRepo.deleteById(accountId);
            return accountId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Role saveRole(Role role) {
        List<Role> roles = roleRepo.findAll();
        Boolean existRoleName = false;
        for (Role r : roles) {
            if (r.getName().equals(role.getName())) {
                existRoleName = true;
                break;
            }
        }
        if (existRoleName) {
            return null;
        }
        return roleRepo.save(role);
    }

    @Override
    public AccountRole addRoleToUser(String username, String roleName) {
        Account account = accountRepo.findByUsername(username);
        if (account == null) {
            throw new RuntimeException("User not found");
        }
        Role role = roleRepo.findByName(roleName);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }
        AccountRole accountRole = new AccountRole();
        AccountRoleId id = new AccountRoleId();
        id.setAccountId(account.getId());
        id.setRoleId(role.getId());
        accountRole.setId(id);
        accountRole.setAccount(account);
        accountRole.setRole(role);
        return accountRoleRepo.save(accountRole);
    }

    @Override
    public Account findAccountByUserName(String username) {
        return accountRepo.findByUsername(username);
    }

    @Override
    public Account findAccountById(Long id) {
        return accountRepo.findById(id).orElse(null);
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public List<Role> findRoleByAccount(Account account) {
        return accountRoleRepo.findRoleByAccount(account);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        List<Account> accounts = accountRepo.findAll();
        for (Account account : accounts) {
            List<Role> roles = accountRoleRepo.findRoleByAccount(account);
            users.add(UserMapper.toUserDTO(account, roles));
        }
        return users;
    }

    @Override
    public UserDTO findUserByAccount(Account account) {
        UserDTO user = new UserDTO();
        List<Role> roles = accountRoleRepo.findRoleByAccount(account);
        user = UserMapper.toUserDTO(account, roles);
        return user;
    }

    @Override
    public void activeAccount(Account account) {
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepo.save(account);
    }

    //
    @Override
    public void storeResetCode(String email, String resetCode) {
        Account account = accountRepo.findByUsername(email);
        account.setResetCode(resetCode);
        account.setResetCodeExpiryTime(calculateExpiryTime(timeResetCode));
        accountRepo.save(account);
    }

    @Override
    public void storeAcceptPassCode(String email, String resetCode) {
        Account account = accountRepo.findByUsername(email);
        account.setResetCode(resetCode);
        account.setResetCodeExpiryTime(calculateExpiryTime(timeAcceptPass));
        accountRepo.save(account);
    }

    @Override
    public boolean isValidResetCode(String resetCode) {
        Account account = accountRepo.findByResetCode(resetCode);
        return account != null && resetCode.equals(account.getResetCode());
    }

    @Override
    public boolean isResetCodeExpired(String resetCode) {
        Account account = accountRepo.findByResetCode(resetCode);
        return account != null && resetCode.equals(account.getResetCode())
                && isExpired(account.getResetCodeExpiryTime());
    }

    @Override
    public void changePassword(String email, String newPassword) {
        Account account = accountRepo.findByUsername(email);
        account.setPassword(encodePassword(newPassword));
        account.setResetCode(null);
        account.setResetCodeExpiryTime(null);
        accountRepo.save(account);
    }

    @Override
    public Account findByResetCode(String resetCode) {
        return accountRepo.findByResetCode(resetCode);
    }

    @Override
    public void removeCodeByExpired() {
        List<Account> accounts = accountRepo.findAll();
        for (Account account : accounts) {
            String resetCode = account.getResetCode();
            Date expiryTime = account.getResetCodeExpiryTime();

            if (resetCode != null && expiryTime != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(expiryTime);
                cal.add(Calendar.MINUTE, timeAcceptPass); // Increase expiry time by 5 minutes

                if (isExpired(cal.getTime())) {
                    account.setResetCode(null);
                    account.setResetCodeExpiryTime(null);
                    accountRepo.save(account);
                }
            }
        }
    }

    private boolean isExpired(Date expiryTime) {
        Date currentTime = new Date();
        return currentTime.after(expiryTime);
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private Date calculateExpiryTime(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute); // Thêm 2 phút vào thời gian hiện tại
        return calendar.getTime();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepo.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        List<Role> roles = accountRoleRepo.findRoleByAccount(account);
        roles.forEach(role -> {
            System.out.println(role.getName());
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        });
        return new User(account.getUsername(), account.getPassword(), authorities);
    }

    @Override
    public UserLoginDTO findUserLoginByAccount(Account account, AuthenProvider auth) {
        UserLoginDTO user = new UserLoginDTO();
        List<Role> roles = accountRoleRepo.findRoleByAccount(account);
        user = UserLoginMapper.toUserLoginDTO(account, roles, auth);
        return user;
    }

}
