package com.nth.mike.service.impl;

import java.util.List;

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

    @Override
    public List<ProductImage> findByProduct(Product product) {
        return productImageRepo.findByProduct(product);
    }

    @Override
    public ProductImage findByUrlImage(String url) {
        return productImageRepo.findByUrlImage(url);
    }

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
    public Long deleteByUrlImage(String urlImage) {
        try {
            Long id = productImageRepo.findByUrlImage(urlImage).getId();
            productImageRepo.deleteByUrlImage(urlImage);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
