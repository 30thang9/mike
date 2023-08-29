package com.nth.mike.service;

import java.util.List;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductImage;

public interface ProductImageService {
    List<ProductImage> findByProduct(Product product);

    ProductImage save(ProductImage productImage);

    String deleteById(String id);

}
