package com.nth.mike.repository;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.customer = :customer")
    Order findByCustomer(Account customer);
}
