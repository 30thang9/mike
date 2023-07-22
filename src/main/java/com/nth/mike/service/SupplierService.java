package com.nth.mike.service;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.Supplier;
import com.nth.mike.repository.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface SupplierService {
    List<Supplier> findAll();
    List<Supplier> findByName(String name);
    Supplier findById(Long id);
    Supplier save(Supplier supplier);
    Long deleteById(Long id);
}
