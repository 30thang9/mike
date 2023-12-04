package com.nth.mike.service.impl;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.Order;
import com.nth.mike.repository.OrderRepo;
import com.nth.mike.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Override
    public Order findByCustomer(Account account) {
        return orderRepo.findByCustomer(account);
    }

    @Override
    public Order save(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public Order findById(Long id) {
        return orderRepo.findById(id).orElse(null);
    }
}
