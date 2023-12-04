package com.nth.mike.service;

import com.nth.mike.entity.Purchase;
import com.nth.mike.model.dto.purchase.PurchaseDTO;

import java.util.List;

public interface PurchaseService {
    List<PurchaseDTO> findAll();
    Purchase findById(Long id);
    Purchase save(Purchase purchase);
    Long deleteById(Long id);
}
