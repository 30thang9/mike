package com.nth.mike.repository;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.AccountRole;
import com.nth.mike.entity.AccountRoleId;
import com.nth.mike.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountRoleRepo extends JpaRepository<AccountRole, AccountRoleId> {
    @Query("SELECT ar.role FROM AccountRole ar WHERE ar.account = :account")
    List<Role> findRoleByAccount(Account account);

    @Query("SELECT ar.account FROM AccountRole ar WHERE ar.role = :role")
    List<Account> findAccountByRole(Role role);
}
