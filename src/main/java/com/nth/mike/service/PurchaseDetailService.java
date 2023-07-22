package com.nth.mike.service;

import com.nth.mike.entity.*;
import com.nth.mike.repository.PurchaseDetailRepo;

import java.io.Serializable;
import java.util.List;

public interface PurchaseDetailService {
    List<PurchaseDetail> findAll();
    PurchaseDetail findById(PurchaseDetailId id);
    PurchaseDetail save(PurchaseDetail purchase);

    List<PurchaseDetail> findByProduct(Product product);

    List<PurchaseDetail> findByProductDetail(ProductDetail productDetail);

    List<PurchaseDetail> findByPurchase(Purchase purchase);

    PurchaseDetailId deleteById(PurchaseDetailId id);
}
