package com.nth.mike.service;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> findAll();
    ProductCategory findById(Long id);
    ProductCategory save(ProductCategory productCategory);
    Long deleteById(Long id);
}
