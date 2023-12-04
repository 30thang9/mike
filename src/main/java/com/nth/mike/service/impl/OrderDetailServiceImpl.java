package com.nth.mike.service.impl;

import com.nth.mike.entity.Order;
import com.nth.mike.entity.OrderDetail;
import com.nth.mike.entity.OrderDetailId;
import com.nth.mike.repository.OrderDetailRepo;
import com.nth.mike.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Override
    public List<OrderDetail> findByOrder(Order order) {
        return orderDetailRepo.findByOrder(order);
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepo.save(orderDetail);
    }

    @Override
    public OrderDetail findById(OrderDetailId orderDetailId) {
        return orderDetailRepo.findById(orderDetailId).orElse(null);
    }

    @Override
    public OrderDetailId deleteById(OrderDetailId orderDetailId) {
        try {
            orderDetailRepo.deleteById(orderDetailId);
            return orderDetailId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
