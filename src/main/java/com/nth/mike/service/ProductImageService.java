package com.nth.mike.service;

import java.util.List;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.ProductImage;

public interface ProductImageService {
//    List<ProductImage> findByProductDetail(ProductDetail productDetail);

    ProductImage save(ProductImage productImage);

    Long deleteById(Long id);

    void saveAll(Iterable<ProductImage> productImages);
}
