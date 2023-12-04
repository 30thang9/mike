package com.nth.mike.service;

import com.nth.mike.entity.Color;
import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.ProductStatus;
import com.nth.mike.model.dto.product.ProductFilterDTO;
import com.nth.mike.model.dto.product.ProductFullDetailDTO;
import com.nth.mike.model.request.product.ProductFilterRequest;
import com.nth.mike.model.request.product.ProductSearchRequest;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    List<Product> findByProductStatus(ProductStatus status);

    List<Product> findByProductCate(Long productCateId);

    List<Product> findByObjectCate(Long objectCateId);

    Product findById(Long id);

    Product save(Product product);

    Long deleteById(Long id);

    ProductFilterDTO findByFilter(ProductFilterRequest filter);

    ProductFilterDTO findBySearch(ProductSearchRequest filter);

    // List<ProductFullDetailDTO> findAllProductFullDetail();
    //
    ProductFullDetailDTO findProductFullDetailByProduct(Product product);

    ProductFullDetailDTO findProductFullDetailByProductColor(Product product, Color color);

    ProductFullDetailDTO findProductFullDetailByProductDetail(ProductDetail productDetail);
}
