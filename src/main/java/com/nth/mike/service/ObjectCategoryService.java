package com.nth.mike.service;

import com.nth.mike.entity.ObjectCategory;

import java.util.List;

public interface ObjectCategoryService {
    List<ObjectCategory> findAll();
    ObjectCategory findById(Long id);

    ObjectCategory save(ObjectCategory objectCategory);
    Long deleteById(Long id);
}
