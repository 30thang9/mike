package com.nth.mike.service.impl;

import java.util.List;

import com.nth.mike.entity.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductImage;
import com.nth.mike.repository.ProductImageRepo;
import com.nth.mike.service.ProductImageService;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    private ProductImageRepo productImageRepo;

//    @Override
//    public List<ProductImage> findByProductDetail(ProductDetail productDetail) {
//        return productImageRepo.findByProductDetail(productDetail);
//    }

    @Override
    public ProductImage save(ProductImage productImage) {
        return productImageRepo.save(productImage);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            productImageRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(Iterable<ProductImage> productImages) {
        productImageRepo.saveAll(productImages);
    }

}
