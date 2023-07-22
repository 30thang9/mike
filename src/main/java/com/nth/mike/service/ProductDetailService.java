package com.nth.mike.service;

import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.ProductDetailId;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface ProductDetailService {
    List<ProductDetail> findAll();
    ProductDetail save(ProductDetail p);
    ProductDetail findById(ProductDetailId id);
    ProductDetailId deleteById(ProductDetailId id);
}
