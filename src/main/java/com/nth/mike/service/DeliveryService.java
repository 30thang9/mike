package com.nth.mike.service;

import com.nth.mike.entity.Delivery;

import java.util.List;

public interface DeliveryService {
    List<Delivery> findAll();

    Delivery findById(Long deliveryId);
}
