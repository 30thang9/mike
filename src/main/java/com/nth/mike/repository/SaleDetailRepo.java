package com.nth.mike.repository;

import com.nth.mike.entity.SaleDetail;
import com.nth.mike.entity.SaleDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleDetailRepo extends JpaRepository<SaleDetail, SaleDetailId> {
}
