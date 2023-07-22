package com.nth.mike.repository;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepo extends JpaRepository<Size,Long> {
    @Query("SELECT DISTINCT s FROM Size s " +
            "JOIN ProductDetail pd ON pd.size = s " +
            "WHERE pd.product = :product")
    List<Size> findByProduct(Product product);
}
