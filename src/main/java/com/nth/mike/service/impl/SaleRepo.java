package com.nth.mike.service.impl;

import com.nth.mike.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepo extends JpaRepository<Sale,Long> {
}
