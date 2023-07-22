package com.nth.mike.repository;

import com.nth.mike.entity.Color;
import com.nth.mike.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepo extends JpaRepository<Color,Long> {
    @Query("SELECT DISTINCT c FROM Color c " +
            "JOIN ProductDetail pd ON pd.color = c " +
            "WHERE pd.product = :product")
    List<Color> findByProduct(Product product);
}
