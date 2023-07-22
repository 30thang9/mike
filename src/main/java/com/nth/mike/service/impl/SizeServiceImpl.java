package com.nth.mike.service.impl;

import com.nth.mike.entity.Size;
import com.nth.mike.repository.SizeRepo;
import com.nth.mike.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    private SizeRepo sizeRepo;

    @Override
    public List<Size> findAll() {
        return sizeRepo.findAll();
    }

    @Override
    public Size findById(Long id) {
        return sizeRepo.findById(id).orElse(null);
    }

    @Override
    public Size save(Size size) {
        return sizeRepo.save(size);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            sizeRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
