package com.nth.mike.service.impl;

import com.nth.mike.entity.*;
import com.nth.mike.repository.PurchaseDetailRepo;
import com.nth.mike.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseDetailServiceImpl implements PurchaseDetailService {
    @Autowired
    private PurchaseDetailRepo pdr;

    @Override
    public List<PurchaseDetail> findAll() {
        return pdr.findAll();
    }

    @Override
    public PurchaseDetail findById(PurchaseDetailId id) {
        return pdr.findById(id).orElse(null);
    }

    @Override
    public PurchaseDetail save(PurchaseDetail purchase) {
        return pdr.save(purchase);
    }

    @Override
    public List<PurchaseDetail> findByProduct(Product product){
        return pdr.findByProduct(product);
    }

    @Override
    public List<PurchaseDetail> findByProductDetail(ProductDetail productDetail){
        return pdr.findByProductDetail(productDetail);
    }

    @Override
    public List<PurchaseDetail> findByPurchase(Purchase purchase){
        return pdr.findByPurchase(purchase);
    }

    @Override
    public PurchaseDetailId deleteById(PurchaseDetailId id) {
        try {
            pdr.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
