package com.nth.mike.service.impl;

import com.nth.mike.entity.Purchase;
import com.nth.mike.model.dto.purchase.PurchaseDTO;
import com.nth.mike.model.mapper.purchase.PurchaseMapper;
import com.nth.mike.repository.PurchaseRepo;
import com.nth.mike.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseRepo purchaseRepo;

    @Override
    public List<PurchaseDTO> findAll() {
        List<Purchase> purchases = purchaseRepo.findAll();
        List<PurchaseDTO> purchasesDTO = new ArrayList<PurchaseDTO>();
        for (Purchase purchase:purchases){
            purchasesDTO.add(PurchaseMapper.topurchaseDTO(purchase));
        }
        return purchasesDTO;
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
