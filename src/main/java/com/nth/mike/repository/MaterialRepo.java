package com.nth.mike.repository;

import com.nth.mike.entity.Material;
import com.nth.mike.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepo extends JpaRepository<Material,Long> {
    @Query("SELECT DISTINCT m FROM Material m " +
            "JOIN ProductDetail pd ON pd.material = m " +
            "WHERE pd.product = :product")
    List<Material> findByProduct(Product product);
}
