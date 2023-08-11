package com.nth.mike.service;

import com.nth.mike.entity.Order;
import com.nth.mike.entity.OrderDetail;
import com.nth.mike.entity.OrderDetailId;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetail> findAll();

    List<OrderDetail> findByOrder(Order order);

    OrderDetail findById(OrderDetailId id);

    OrderDetail save(OrderDetail orderDetail);

    OrderDetailId deleteById(OrderDetailId id);
}
