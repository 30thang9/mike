package com.nth.mike.repository;

import com.nth.mike.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

    Account findByResetCode(String resetCode);
}
