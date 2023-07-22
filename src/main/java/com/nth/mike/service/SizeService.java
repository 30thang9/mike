package com.nth.mike.service;

import com.nth.mike.entity.Employee;
import com.nth.mike.entity.Size;

import java.util.List;

public interface SizeService {
    List<Size> findAll();
    Size findById(Long id);
    Size save(Size size);
    Long deleteById(Long id);
}
