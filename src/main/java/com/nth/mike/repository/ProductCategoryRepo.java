package com.nth.mike.repository;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepo extends JpaRepository<ProductCategory,Long> {
}
