package com.nth.mike.service.impl;

import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.ProductDetailId;
import com.nth.mike.repository.ProductDetailRepo;
import com.nth.mike.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    ProductDetailRepo pdr;

    @Override
    public List<ProductDetail> findAll() {
        return pdr.findAll();
    }

    @Override
    public ProductDetail save(ProductDetail p) {
        return pdr.save(p);
    }

    @Override
    public ProductDetail findById(ProductDetailId id) {
        return pdr.findById(id).orElse(null);
    }

    @Override
    public ProductDetailId deleteById(ProductDetailId id) {
        try {
            pdr.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
