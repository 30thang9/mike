package com.nth.mike.repository;

import com.nth.mike.entity.Order;
import com.nth.mike.entity.OrderDetail;
import com.nth.mike.entity.OrderDetailId;
import com.nth.mike.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, OrderDetailId> {
    @Query("SELECT od from OrderDetail od WHERE od.order = :order")
    List<OrderDetail> findByOrder(Order order);
    @Query("SELECT od from OrderDetail od WHERE od.productDetail = :productDetail")
    List<OrderDetail> findByProductDetail(ProductDetail productDetail);
}
