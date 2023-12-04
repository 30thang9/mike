package com.nth.mike.service;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.Order;

public interface OrderService {
    Order findByCustomer(Account account);

    Order save(Order order);

    Order findById(Long id);
}
