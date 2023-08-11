package com.nth.mike.service.impl;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

import com.nth.mike.repository.OrderRepo;
import com.nth.mike.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Override
    public List<Order> findAll(){
        return orderRepo.findAll();
    }
    @Override
    public Order findById(Long id){
        return orderRepo.findById(id).orElse(null);
    }
    @Override
    public Order findByAccount(Account account) {
        return orderRepo.findByAccount(account);
    }
    @Override
    public Order save(Order order) {
        return orderRepo.save(order);
    }
    @Override
    public Long deleteById(Long id){
        try {
            orderRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
