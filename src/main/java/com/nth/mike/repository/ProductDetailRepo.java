package com.nth.mike.repository;

import com.nth.mike.entity.Color;
import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductDetail;
import com.nth.mike.entity.ProductDetailId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepo extends JpaRepository<ProductDetail, ProductDetailId> {
    @Query("SELECT pd FROM ProductDetail pd where pd.product = :product")
    public List<ProductDetail> findByProduct(Product product);

    @Query("SELECT pd FROM ProductDetail pd where pd.product = :product AND pd.color = :color")
    public List<ProductDetail> findByProductColor(Product product, Color color);
}
