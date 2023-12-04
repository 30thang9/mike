package com.nth.mike.service.impl;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.SizeImage;
import com.nth.mike.repository.SizeImageRepo;
import com.nth.mike.service.SizeImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SizeImageServiceImpl implements SizeImageService {
    @Autowired
    private SizeImageRepo sizeImageRepo;
    @Override
    public List<SizeImage> findAll() {
        return sizeImageRepo.findAll();
    }
    @Override
    public SizeImage findById(Long id) {
        return sizeImageRepo.findById(id).orElse(null);
    }

    @Override
    public SizeImage save(SizeImage sizeImage) {
        return sizeImageRepo.save(sizeImage);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            sizeImageRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
