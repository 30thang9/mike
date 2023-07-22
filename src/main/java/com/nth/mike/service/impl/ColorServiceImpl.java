package com.nth.mike.service.impl;

import com.nth.mike.entity.Color;
import com.nth.mike.repository.ColorRepo;
import com.nth.mike.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorRepo colorRepo;

    @Override
    public List<Color> findAll() {
        return colorRepo.findAll();
    }

    @Override
    public Color findById(Long id) {
        return colorRepo.findById(id).orElse(null);
    }

    @Override
    public Color save(Color color) {
        return colorRepo.save(color);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            colorRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
