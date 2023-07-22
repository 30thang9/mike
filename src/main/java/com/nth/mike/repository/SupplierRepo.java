package com.nth.mike.repository;

import com.nth.mike.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Long> {
    @Query(value = "SELECT * FROM suppliers WHERE name = ?1", nativeQuery = true)
    List<Supplier> findByName(String name);
}
