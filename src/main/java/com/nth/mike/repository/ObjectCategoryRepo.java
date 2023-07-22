package com.nth.mike.repository;

import com.nth.mike.entity.ObjectCategory;
import com.nth.mike.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectCategoryRepo extends JpaRepository<ObjectCategory,Long> {
}
