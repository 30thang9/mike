package com.nth.mike.service;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.ProductDetailId;

import java.util.List;

public interface ProductDetailService {
    List<ProductDetail> findAll();

    List<ProductDetail> findByProduct(Product product);

    ProductDetail save(ProductDetail p);

    ProductDetail findById(ProductDetailId id);

    ProductDetailId deleteById(ProductDetailId id);

    void saveAll(List<ProductDetail> productDetailsSave);
}
