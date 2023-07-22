package com.nth.mike.service.impl;

import com.nth.mike.entity.Material;
import com.nth.mike.repository.MaterialRepo;
import com.nth.mike.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepo materialRepo;

    @Override
    public List<Material> findAll() {
        return materialRepo.findAll();
    }

    @Override
    public Material findById(Long id) {
        return materialRepo.findById(id).orElse(null);
    }

    @Override
    public Material save(Material material) {
        return materialRepo.save(material);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            materialRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
