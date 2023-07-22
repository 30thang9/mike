package com.nth.mike.service.impl;

import com.nth.mike.entity.ObjectCategory;
import com.nth.mike.repository.ObjectCategoryRepo;
import com.nth.mike.service.ObjectCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectCategoryServiceImpl implements ObjectCategoryService {
    @Autowired
    private ObjectCategoryRepo objectCategoryRepo;
    @Override
    public List<ObjectCategory> findAll() {
        return objectCategoryRepo.findAll();
    }
    @Override
    public ObjectCategory findById(Long id) {
        return objectCategoryRepo.findById(id).orElse(null);
    }

    @Override
    public ObjectCategory save(ObjectCategory product) {
        return objectCategoryRepo.save(product);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            objectCategoryRepo.deleteById(id);
            return id;
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
