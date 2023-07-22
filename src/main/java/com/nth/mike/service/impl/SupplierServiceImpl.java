package com.nth.mike.service.impl;

import com.nth.mike.entity.Supplier;
import com.nth.mike.repository.SupplierRepo;
import com.nth.mike.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepo supplierRepo;

    @Override
    public List<Supplier> findAll() {
        return supplierRepo.findAll();
    }

    @Override
    public List<Supplier> findByName(String name) {
        return supplierRepo.findByName(name);
    }

    @Override
    public Supplier findById(Long id) {
        return supplierRepo.findById(id).orElse(null);
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepo.save(supplier);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            supplierRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
