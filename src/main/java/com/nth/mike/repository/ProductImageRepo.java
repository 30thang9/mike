package com.nth.mike.repository;

import java.util.List;

import com.nth.mike.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductImage;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {
//    @Query("SELECT pi FROM ProductImage pi WHERE pi.productDetail = :productDetail")
//    List<ProductImage> findByProductDetail(ProductDetail productDetail);

}
