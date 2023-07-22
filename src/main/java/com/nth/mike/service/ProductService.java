package com.nth.mike.service;

import com.nth.mike.entity.Product;
import com.nth.mike.model.dto.ProductFilterDTO;
import com.nth.mike.model.dto.ProductFullDetailDTO;
import com.nth.mike.model.pagination.ProductFilterPaging;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    List<Product> findByProductCate(Long productCateId);
    List<Product> findByObjectCate(Long objectCateId);
    Product findById(Long id);
    Product save(Product product);
    Long deleteById(Long id);
    ProductFilterPaging findByFilter(ProductFilterDTO filter);
    List<ProductFullDetailDTO> findAllProductFullDetail();

    ProductFullDetailDTO findProductFullDetailById(Long id);
}
