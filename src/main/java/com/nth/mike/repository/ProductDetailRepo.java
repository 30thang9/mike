package com.nth.mike.repository;

import com.nth.mike.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepo extends JpaRepository<ProductDetail, ProductDetailId> {
    @Query("SELECT pd FROM ProductDetail pd where pd.product = :product")
    List<ProductDetail> findByProduct(Product product);
    @Query("SELECT pd FROM ProductDetail pd where pd.product = :product AND pd.productDetailStatus = :status")
    List<ProductDetail> findByProductWhereStatus(Product product, ProductDetailStatus status);
    @Query("SELECT pd FROM ProductDetail pd where pd.product = :product AND pd.color = :color")
    List<ProductDetail> findByProductColor(Product product, Color color);
    @Query("SELECT pd FROM ProductDetail pd where pd.product = :product AND pd.color = :color AND pd.productDetailStatus = :status")
    List<ProductDetail> findByProductColorWhereStatus(Product product, Color color, ProductDetailStatus status);
}
