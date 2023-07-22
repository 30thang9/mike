package com.nth.mike.service.impl;

import com.nth.mike.entity.Purchase;
import com.nth.mike.repository.PurchaseRepo;
import com.nth.mike.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseRepo purchaseRepo;

    @Override
    public List<Purchase> findAll() {
        return purchaseRepo.findAll();
    }

    @Override
    public Purchase findById(Long id) {
        return purchaseRepo.findById(id).orElse(null);
    }

    @Override
    public Purchase save(Purchase purchase) {
        return purchaseRepo.save(purchase);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            purchaseRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
