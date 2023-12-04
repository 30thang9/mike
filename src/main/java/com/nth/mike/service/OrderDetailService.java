package com.nth.mike.service;

import com.nth.mike.entity.Order;
import com.nth.mike.entity.OrderDetail;
import com.nth.mike.entity.OrderDetailId;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> findByOrder(Order order);

    OrderDetail save(OrderDetail orderDetail);

    OrderDetail findById(OrderDetailId orderDetailId);

    OrderDetailId deleteById(OrderDetailId orderDetailId);
}
