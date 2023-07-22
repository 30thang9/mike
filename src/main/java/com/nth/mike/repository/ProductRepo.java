package com.nth.mike.repository;

import com.nth.mike.entity.*;
import com.nth.mike.model.dto.ProductFilterDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.objectCategory = :objectCategory")
    List<Product> findProductByObjectCategory(ObjectCategory objectCategory);

    @Query("SELECT p FROM Product p WHERE p.productCategory = :productCategory")
    List<Product> findProductByProductCategory(ProductCategory productCategory);

}
