package com.nth.mike.service.impl;

import com.nth.mike.entity.Delivery;
import com.nth.mike.repository.DeliveryRepo;
import com.nth.mike.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DeliverServiceImpl implements DeliveryService {
    @Autowired
    private DeliveryRepo deliverRepo;
    @Override
    public List<Delivery> findAll() {
        return deliverRepo.findAll();
    }

    @Override
    public Delivery findById(Long deliveryId) {
        return deliverRepo.findById(deliveryId).orElse(null);
    }
}
