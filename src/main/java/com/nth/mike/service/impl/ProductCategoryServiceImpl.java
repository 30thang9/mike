package com.nth.mike.service.impl;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductCategory;
import com.nth.mike.repository.ProductCategoryRepo;
import com.nth.mike.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRepo productCategoryRepo;
    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepo.findAll();
    }

    @Override
    public ProductCategory findById(Long id) {
        return productCategoryRepo.findById(id).orElse(null);
    }

    @Override
    public ProductCategory save(ProductCategory product) {
        return productCategoryRepo.save(product);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            productCategoryRepo.deleteById(id);
            return id;
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
