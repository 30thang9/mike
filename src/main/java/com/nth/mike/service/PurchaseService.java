package com.nth.mike.service;

import com.nth.mike.entity.Purchase;

import java.util.List;

public interface PurchaseService {
    List<Purchase> findAll();
    Purchase findById(Long id);
    Purchase save(Purchase purchase);
    Long deleteById(Long id);
}
