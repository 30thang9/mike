package com.nth.mike.service;

import com.nth.mike.entity.Color;
import com.nth.mike.entity.Material;

import java.util.List;

public interface MaterialService {
    List<Material> findAll();
    Material findById(Long id);
    Material save(Material material);
    Long deleteById(Long id);
}
