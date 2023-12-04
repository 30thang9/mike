package com.nth.mike.repository;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.username =:username")
    Account findByUsername(String username);
    @Query("SELECT a FROM Account a WHERE a.resetCode =:resetCode")
    Account findByResetCode(String resetCode);
//    @Query("SELECT a FROM Account a WHERE a.accountType =:accountType")
//    List<Account> findAllAccountByType(AccountType accountType);
}
