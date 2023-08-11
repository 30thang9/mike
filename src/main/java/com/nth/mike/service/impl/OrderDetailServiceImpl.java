package com.nth.mike.service.impl;

import com.nth.mike.entity.Order;
import com.nth.mike.entity.OrderDetail;
import com.nth.mike.entity.OrderDetailId;
import org.springframework.beans.factory.annotation.Autowired;

import com.nth.mike.repository.OrderDetailRepo;
import com.nth.mike.service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Override
    public List<OrderDetail> findAll(){
        return orderDetailRepo.findAll();
    }
    @Override
    public List<OrderDetail> findByOrder(Order order){
        return orderDetailRepo.findByOrder(order);
    }
    @Override
    public OrderDetail findById(OrderDetailId id){
        return orderDetailRepo.findById(id).orElse(null);
    }
    @Override
    public OrderDetail save(OrderDetail orderDetail){
        return orderDetailRepo.save(orderDetail);
    }
    @Override
    public OrderDetailId deleteById(OrderDetailId id){
        try {
            orderDetailRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
