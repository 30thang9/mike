package com.nth.mike.repository;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.SizeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeImageRepo extends JpaRepository<SizeImage,Long> {

}
