package com.nth.mike.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductImage;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {
    @Query("SELECT pi FROM ProductImage pi WHERE pi.product = :product")
    List<ProductImage> findByProduct(Product product);

    @Query("SELECT pi FROM ProductImage pi WHERE pi.urlImage = :urlImage")
    ProductImage findByUrlImage(String urlImage);

    @Modifying
    @Query("DELETE FROM ProductImage pi WHERE pi.urlImage = :urlImage")
    List<ProductImage> deleteByUrlImage(String urlImage);

}
