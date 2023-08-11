package com.nth.mike.service;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findById(Long id);

    Order findByAccount(Account account);

    Order save(Order order);

    Long deleteById(Long id);
}
