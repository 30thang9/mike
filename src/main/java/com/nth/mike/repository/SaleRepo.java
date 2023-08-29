package com.nth.mike.repository;

import com.nth.mike.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepo extends JpaRepository<Sale, Long> {
}