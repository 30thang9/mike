package com.nth.mike.service;

import com.nth.mike.entity.Product;
import com.nth.mike.entity.SizeImage;

import java.util.List;

public interface SizeImageService {
    List<SizeImage> findAll();
    SizeImage findById(Long id);
    SizeImage save(SizeImage sizeImage);
    Long deleteById(Long id);
}
