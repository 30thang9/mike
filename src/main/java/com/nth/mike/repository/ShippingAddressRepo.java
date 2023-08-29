package com.nth.mike.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.ShippingAddress;

@Repository
public interface ShippingAddressRepo extends JpaRepository<ShippingAddress, Long> {
    @Query("SELECT aa FROM ShippingAddress aa WHERE aa.account = :account")
    List<ShippingAddress> findByAccount(Account account);
}
